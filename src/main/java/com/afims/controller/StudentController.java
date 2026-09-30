package com.afims.controller;

import com.afims.dto.FeedbackRequest;
import com.afims.entity.Feedback;
import com.afims.entity.FeedbackCycle;
import com.afims.entity.Question;
import com.afims.entity.User;
import com.afims.repository.FeedbackCycleRepository;
import com.afims.repository.FeedbackRepository;
import com.afims.repository.QuestionRepository;
import com.afims.repository.UserRepository;
import com.afims.service.AuditService;
import com.afims.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Student dashboard and feedback workflow.
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    private final UserRepository users;
    private final FeedbackRepository feedbacks;
    private final FeedbackCycleRepository cycles;
    private final QuestionRepository questions;
    private final AuditService audit;
    private final NotificationService notifications;

    public StudentController(
            UserRepository users,
            FeedbackRepository feedbacks,
            FeedbackCycleRepository cycles,
            QuestionRepository questions,
            AuditService audit,
            NotificationService notifications) {

        this.users = users;
        this.feedbacks = feedbacks;
        this.cycles = cycles;
        this.questions = questions;
        this.audit = audit;
        this.notifications = notifications;
    }

    private User me(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new IllegalStateException("No authenticated student was found.");
        }

        return users.findByEmailIgnoreCase(authentication.getName().trim())
                .orElseThrow(() ->
                        new IllegalStateException("Logged-in student was not found."));
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        User student = me(authentication);

        model.addAttribute("user", student);
        model.addAttribute(
                "feedbacks",
                feedbacks.findByStudentIdOrderByCreatedAtDesc(student.getId())
        );
        model.addAttribute(
                "faculties",
                users.findByRole(User.Role.FACULTY)
        );
        model.addAttribute(
                "activeCycle",
                findActiveCycle()
        );

        return "student/dashboard";
    }

    /**
     * Opens the Give Feedback page.
     * Both /student/feedback and /student/feedback/new are supported.
     */
    @GetMapping({"/feedback", "/feedback/new"})
    public String feedbackForm(Model model, Authentication authentication) {
        me(authentication);

        prepareFeedbackForm(model, new FeedbackRequest());

        return "student/feedback";
    }

    @PostMapping("/feedback")
    public String submitFeedback(
            @Valid @ModelAttribute("form") FeedbackRequest form,
            BindingResult bindingResult,
            Authentication authentication,
            Model model) {

        if (bindingResult.hasErrors()) {
            prepareFeedbackForm(model, form);
            return "student/feedback";
        }

        User student = me(authentication);

        FeedbackCycle cycle = findActiveCycle();

        if (cycle == null) {
            prepareFeedbackForm(model, form);
            model.addAttribute(
                    "error",
                    "There is no active feedback cycle. Please try again when a cycle is open."
            );
            return "student/feedback";
        }

        User faculty = users.findById(form.getFacultyId())
                .filter(user -> user.getRole() == User.Role.FACULTY)
                .orElse(null);

        if (faculty == null) {
            prepareFeedbackForm(model, form);
            model.addAttribute("error", "The selected faculty member is not available.");
            return "student/feedback";
        }

        String course = form.getCourse().trim();

        if (feedbacks.existsByStudentIdAndFacultyIdAndCourseAndCycleId(
                student.getId(),
                faculty.getId(),
                course,
                cycle.getId())) {

            prepareFeedbackForm(model, form);
            model.addAttribute(
                    "error",
                    "You have already submitted feedback for this faculty, course and active cycle."
            );
            return "student/feedback";
        }

        Feedback feedback = new Feedback();
        feedback.setStudent(student);
        feedback.setFaculty(faculty);
        feedback.setCourse(course);
        feedback.setSubject(form.getSubject().trim());
        feedback.setCycle(cycle);
        feedback.setOverallRating(form.getRating());
        feedback.setComment(cleanOptional(form.getComment()));
        feedback.setSuggestion(cleanOptional(form.getSuggestion()));
        feedback.setAnonymous(form.isAnonymous());

        feedbacks.save(feedback);

        audit.log(
                student,
                "FEEDBACK_SUBMITTED",
                "Feedback",
                feedback.getId(),
                null
        );

        notifications.send(
                faculty,
                "New feedback received",
                "A student submitted feedback for " + feedback.getCourse() + ".",
                "FEEDBACK"
        );

        notifications.send(
                student,
                "Feedback submitted",
                "Your feedback was recorded successfully.",
                "FEEDBACK"
        );

        return "redirect:/student/dashboard?submitted";
    }

    private void prepareFeedbackForm(Model model, FeedbackRequest form) {
        model.addAttribute("form", form);

        List<User> facultyList;
        try {
            facultyList = users.findByRole(User.Role.FACULTY);
        } catch (RuntimeException ex) {
            facultyList = Collections.emptyList();
        }

        List<Question> questionList;
        try {
            questionList = questions.findByActiveTrueOrderByDisplayOrderAsc();
        } catch (RuntimeException ex) {
            questionList = Collections.emptyList();
        }

        model.addAttribute("faculties", facultyList);
        model.addAttribute("questions", questionList);
        model.addAttribute("activeCycle", findActiveCycle());
    }

    private FeedbackCycle findActiveCycle() {
        try {
            return cycles.findFirstByStatusOrderByStartDateDesc(
                    FeedbackCycle.Status.ACTIVE
            ).orElse(null);
        } catch (RuntimeException ex) {
            return null;
        }
    }

    private String cleanOptional(String value) {
        if (value == null) {
            return null;
        }

        String cleaned = value.trim();
        return cleaned.isEmpty() ? null : cleaned;
    }
}

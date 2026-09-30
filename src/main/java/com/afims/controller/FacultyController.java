package com.afims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.afims.dto.ImprovementPlanRequest;
import com.afims.entity.Feedback;
import com.afims.entity.ImprovementPlan;
import com.afims.entity.Question;
import com.afims.entity.User;
import com.afims.repository.AuditLogRepository;
import com.afims.repository.FeedbackRepository;
import com.afims.repository.ImprovementPlanRepository;
import com.afims.repository.QuestionRepository;
import com.afims.repository.UserRepository;
import com.afims.service.FeedbackAnalysisService;
import com.afims.service.NotificationService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/faculty")
public class FacultyController {

    private final UserRepository users;
    private final FeedbackRepository feedbacks;
    private final ImprovementPlanRepository plans;
    private final FeedbackAnalysisService ai;
    private final AuditLogRepository audit;
    private final NotificationService notifications;
    private final QuestionRepository questions;

    public FacultyController(
            UserRepository users,
            FeedbackRepository feedbacks,
            ImprovementPlanRepository plans,
            FeedbackAnalysisService ai,
            AuditLogRepository audit,
            NotificationService notifications,
            QuestionRepository questions) {

        this.users = users;
        this.feedbacks = feedbacks;
        this.plans = plans;
        this.ai = ai;
        this.audit = audit;
        this.notifications = notifications;
        this.questions = questions;
    }

    private User me(
            org.springframework.security.core.Authentication authentication) {

        return users
                .findByEmailIgnoreCase(authentication.getName())
                .orElseThrow(()
                        -> new IllegalStateException(
                        "Logged-in faculty user was not found."
                ));
    }

    // ==============================
    // FACULTY DASHBOARD
    // ==============================
    @GetMapping("/dashboard")
    public String dashboard(
            Model model,
            org.springframework.security.core.Authentication authentication) {

        User user = me(authentication);

        var facultyFeedbacks
                = feedbacks.findByFacultyIdOrderByCreatedAtDesc(
                        user.getId()
                );

        model.addAttribute("user", user);

        model.addAttribute(
                "feedbacks",
                facultyFeedbacks
        );

        model.addAttribute(
                "plans",
                plans.findByFacultyIdOrderByUpdatedAtDesc(
                        user.getId()
                )
        );

        model.addAttribute(
                "questions",
                questions.findByActiveTrueOrderByDisplayOrderAsc()
        );

        model.addAttribute(
                "avg",
                facultyFeedbacks
                        .stream()
                        .mapToInt(Feedback::getOverallRating)
                        .average()
                        .orElse(0)
        );

        return "faculty/dashboard";
    }

    // ==============================
    // CREATE IMPROVEMENT PLAN
    // ==============================
    @GetMapping("/plans/new")
    public String newPlan(Model model) {

        model.addAttribute(
                "form",
                new ImprovementPlanRequest()
        );

        return "faculty/plan-form";
    }

    // ==============================
    // SAVE IMPROVEMENT PLAN
    // ==============================
    @PostMapping("/plans")
    public String savePlan(
            @Valid
            @ModelAttribute("form") ImprovementPlanRequest form,
            org.springframework.security.core.Authentication authentication) {

        User user = me(authentication);

        ImprovementPlan plan = new ImprovementPlan();

        plan.setFaculty(user);

        plan.setTitle(form.getTitle());

        plan.setProblemStatement(
                form.getProblemStatement()
        );

        plan.setEvidence(
                form.getEvidence()
        );

        plan.setRootCause(
                form.getRootCause()
        );

        plan.setProposedAction(
                form.getProposedAction()
        );

        plan.setTargetDate(
                form.getTargetDate()
        );

        plan.setPriority(
                form.getPriority()
        );

        plan.setExpectedOutcome(
                form.getExpectedOutcome()
        );

        plan.setMeasurementMethod(
                form.getMeasurementMethod()
        );

        plan.setStatus(
                ImprovementPlan.Status.DRAFT
        );

        plans.save(plan);

        return "redirect:/faculty/dashboard";
    }

    // ==============================
    // SUBMIT IMPROVEMENT PLAN
    // ==============================
    @PostMapping("/plans/{id}/submit")
    public String submitPlan(
            @PathVariable Long id,
            org.springframework.security.core.Authentication authentication) {

        ImprovementPlan plan
                = plans.findById(id)
                        .orElseThrow(()
                                -> new IllegalArgumentException(
                                "Improvement plan not found."
                        ));

        if (!plan.getFaculty()
                .getEmail()
                .equalsIgnoreCase(authentication.getName())) {

            throw new org.springframework.security.access.AccessDeniedException(
                    "You are not allowed to submit this plan."
            );
        }

        plan.setStatus(
                ImprovementPlan.Status.SUBMITTED
        );

        plans.save(plan);

        User hod
                = users.findByRole(User.Role.HOD)
                        .stream()
                        .findFirst()
                        .orElse(null);

        notifications.send(
                hod,
                "Improvement plan submitted",
                "A faculty improvement plan is ready for review.",
                "PLAN"
        );

        return "redirect:/faculty/dashboard";
    }

    // ==============================
    // ADD QUESTION
    // ==============================
    @PostMapping("/questions")
    public String addQuestion(
            @RequestParam String questionText,
            @RequestParam String category) {

        Question question = new Question();

        question.setQuestionText(
                questionText
        );

        question.setCategory(
                category
        );

        question.setDisplayOrder(
                (int) questions.count() + 1
        );

        questions.save(question);

        return "redirect:/faculty/dashboard";
    }

    // ==============================
    // DISABLE QUESTION
    // ==============================
    @PostMapping("/questions/{id}/disable")
    public String disableQuestion(
            @PathVariable Long id) {

        Question question
                = questions.findById(id)
                        .orElseThrow(()
                                -> new IllegalArgumentException(
                                "Question not found."
                        ));

        question.setActive(false);

        questions.save(question);

        return "redirect:/faculty/dashboard";
    }

    // ==============================
    // AI INSIGHTS
    // ==============================
    @GetMapping("/insights")
    public String insights(
            Model model,
            org.springframework.security.core.Authentication authentication) {

        var feedbackList
                = feedbacks.findByFacultyIdOrderByCreatedAtDesc(
                        me(authentication).getId()
                );

        model.addAttribute(
                "feedbacks",
                feedbackList
        );

        model.addAttribute(
                "analyses",
                feedbackList
                        .stream()
                        .map(ai::analyze)
                        .toList()
        );

        return "faculty/insights";
    }
}

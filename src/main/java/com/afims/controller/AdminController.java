package com.afims.controller;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.afims.entity.FeedbackCycle;
import com.afims.entity.ImprovementPlan;
import com.afims.entity.Question;
import com.afims.entity.User;
import com.afims.repository.AuditLogRepository;
import com.afims.repository.DepartmentRepository;
import com.afims.repository.FeedbackCycleRepository;
import com.afims.repository.FeedbackRepository;
import com.afims.repository.ImprovementPlanRepository;
import com.afims.repository.QuestionRepository;
import com.afims.repository.UserRepository;
@Controller
@RequestMapping("/admin")public class AdminController {
    final UserRepository users;
    final FeedbackRepository feedbacks;
    final ImprovementPlanRepository plans;
    final AuditLogRepository audits;
    final FeedbackCycleRepository cycles;
    final QuestionRepository questions;
    final DepartmentRepository depts;
    public AdminController(            UserRepository u,            FeedbackRepository f,            ImprovementPlanRepository p,            AuditLogRepository a,            FeedbackCycleRepository c,            QuestionRepository q,            DepartmentRepository d) {
        users = u;
        feedbacks = f;
        plans = p;
        audits = a;
        cycles = c;
        questions = q;
        depts = d;
    }
    @GetMapping("/dashboard")    String dash(Model m) {
        m.addAttribute(                "users",                users.findAll(PageRequest.of(0, 10))        );
        m.addAttribute(                "totalUsers",                users.count()        );
        m.addAttribute(                "activeUsers",                users.countByStatus(                        User.RegistrationStatus.ACTIVE                )        );
        m.addAttribute(                "pending",                users.countByStatus(                        User.RegistrationStatus.PENDING                )        );
        m.addAttribute(                "feedbackCount",                feedbacks.total()        );
        m.addAttribute(                "avg",                feedbacks.averageRating() == null                ? 0                : feedbacks.averageRating()        );
        m.addAttribute(                "openPlans",                plans.countByStatus(                        ImprovementPlan.Status.SUBMITTED                )                + plans.countByStatus(                        ImprovementPlan.Status.UNDER_REVIEW                )        );
        m.addAttribute(                "cycles",                cycles.findAll()        );
        m.addAttribute(                "questions",                questions.findAll()        );
        return "admin/dashboard";
    }
    @PostMapping("/users/{id}/status")    String status(
    @PathVariable Long id,
    @RequestParam User.RegistrationStatus value) {
        User u = users.findById(id).orElseThrow();
        u.setStatus(value);
        users.save(u);
        return "redirect:/admin/dashboard";
    }
    @PostMapping("/cycles")    String cycle(
    @RequestParam String name,
    @RequestParam String academicYear,
    @RequestParam Integer semester,
    @RequestParam java.time.LocalDate startDate,
    @RequestParam java.time.LocalDate endDate) {
        FeedbackCycle c = new FeedbackCycle();
        c.setName(name);
        c.setAcademicYear(academicYear);
        c.setSemester(semester);
        c.setStartDate(startDate);
        c.setEndDate(endDate);
        c.setStatus(FeedbackCycle.Status.UPCOMING);
        cycles.save(c);
        return "redirect:/admin/dashboard";
    }
    @PostMapping("/questions")    String question(
    @RequestParam String questionText,
    @RequestParam String category) {
        Question q = new Question();
        q.setQuestionText(questionText);
        q.setCategory(category);
        q.setDisplayOrder((int) questions.count() + 1);
        questions.save(q);
        return "redirect:/admin/dashboard";
    }
    @GetMapping("/audit")    String audit(Model m) {
        m.addAttribute(                "logs",                audits.findAll(PageRequest.of(0, 50))        );
        return "admin/audit";
    }
}

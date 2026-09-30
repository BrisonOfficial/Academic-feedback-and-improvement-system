package com.afims.controller;
import com.afims.entity.*;
import com.afims.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/hod") public class HodController {
    final ImprovementPlanRepository plans;
    final FeedbackRepository feedbacks;
    final UserRepository users;
    final com.afims.service.NotificationService notifications;
    public HodController(ImprovementPlanRepository p,FeedbackRepository f,UserRepository u,com.afims.service.NotificationService n) {
        plans=p;
        feedbacks=f;
        users=u;
        notifications=n;
    }
    @GetMapping("/dashboard") String dash(Model m) {
        m.addAttribute("plans",plans.findAll());
        m.addAttribute("facultyCount",users.countByRole(User.Role.FACULTY));
        m.addAttribute("studentCount",users.countByRole(User.Role.STUDENT));
        m.addAttribute("avg",feedbacks.averageRating()==null?0:feedbacks.averageRating());
        return "hod/dashboard";
    }
    @PostMapping("/plans/{id}/review") String review(
    @PathVariable Long id,
    @RequestParam String decision,
    @RequestParam(required=false) String comments) {
        ImprovementPlan p=plans.findById(id).orElseThrow();
        p.setHodComments(comments);
        p.setStatus(ImprovementPlan.Status.valueOf(decision));
        plans.save(p);
        notifications.send(p.getFaculty(),"Improvement plan reviewed","Your HOD has updated the status to "+p.getStatus()+".","PLAN");
        return "redirect:/hod/dashboard";
    }
}

package com.afims.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/error")public class ErrorController {
    @GetMapping("/403")    String e403() {
        return "error/403";
    }
    @GetMapping("/404")    String e404() {
        return "error/404";
    }
    @GetMapping("/500")    String e500() {
        return "error/500";
    }
}

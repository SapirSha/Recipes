package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogInController {

    @RequestMapping("/")  
    public String showPage() {
        return "LogIn";
    }
}
package controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Exceptions.InvalidUserException;
import Exceptions.NoUserException;
import Others.Service;
import Others.User;


@Controller
public class AuthController {
    @Autowired
    Service service;

    @RequestMapping("/LogIn")
    public String showLogIn(@RequestParam(value = "LogInError", required = false) String error,
                            @RequestParam(value = "returnUrl", required = false) String returnUrl, Model model) {
        User user = new User();
        model.addAttribute("user", user);

        if (error != null)
            model.addAttribute("loginErrorMessage", "Invalid username or password.");
        else
            model.addAttribute("loginErrorMessage", "");

        model.addAttribute("returnUrl", returnUrl != null ? returnUrl : "/");

        return "MainPage";
    }

    @PostMapping("/processLogin")
    public String processLogin(@ModelAttribute("LogInUser") User user, 
                               @RequestParam(value = "returnUrl", required = false) String returnUrl,
                               HttpServletRequest request) {
        try {
            User savedUser = service.getUserByName(user.getUsername());
            if (savedUser.getPassword().equals(user.getPassword())) {
                // create session and put user object on session
                request.getSession().setAttribute("user", savedUser);
                return "redirect:/";
            }
        } catch (NoUserException e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("URL" + returnUrl);
        
        System.out.println("FAILED LOGIN");
        return "redirect:/LogIn?LogInError=true&returnUrl=" + (returnUrl != null ? returnUrl : "/");
    }

    @RequestMapping("/SignUp")
    public String showSignUp(@RequestParam(value = "SignUpError", required = false) String error,
                             @RequestParam(value = "returnUrl", required = false) String returnUrl, Model model) {
        User user = new User();
        model.addAttribute("user", user);

        if (error != null)
            model.addAttribute("signupErrorMessage", error);
        else
            model.addAttribute("signupErrorMessage", "");

        model.addAttribute("returnUrl", returnUrl != null ? returnUrl : "/");

        return "MainPage";
    }

    @PostMapping("/processSignup")
    public String processSignup(@ModelAttribute("SignUpUser") User user,
                                @RequestParam(value = "returnUrl", required = false) String returnUrl,
                                Model model) {
        String error;

        if (user.getUsername().length() == 0 || user.getPassword().length() == 0) {
            error = "Empty username or password are not allowed!";
        } else {
            try {
                service.saveUser(user);
                System.out.println("User signup successful: " + user);
                return "redirect:/SignUp?SignUpSuccess=true";  // Redirect to a success page
            } catch (InvalidUserException e) {
                error = "Username already exists";
            } catch (IOException e) {
                error = "";
                e.printStackTrace();
            }
        }
        
        return "redirect:/SignUp?SignUpError=" + error + "&returnUrl=" + (returnUrl != null ? returnUrl : "/");
    }
}


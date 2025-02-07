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
public class LogInController {
	@Autowired
	Service service;

    @RequestMapping("/")  
    public String showPage() {    	
        return "redirect:/LogIn";
    }
    
    @RequestMapping("/LogIn")
    public String showLogIn(@RequestParam(value = "error", required = false) String error, Model model) {
    	User user = new User();
    	
    	model.addAttribute("user", user);
    	
    	if (error != null)
    		model.addAttribute("loginErrorMessage", "Invalid username or password.");
    	else
    		model.addAttribute("loginErrorMessage", "");
    	    	
    	return "LogIn";
    }
    
	@PostMapping("/processLogin")
	public String processLogin(@ModelAttribute("user") User user, HttpServletRequest request) {
		try {
			User savedUser = service.getUserByName(user.getUsername());
						
			if (savedUser.getPassword().equals(user.getPassword())) {
				// create session and put user object on session
				request.getSession().setAttribute("user", savedUser);
				return "WASD";
			}
		}
		catch (NoUserException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("FAILED LOGIN");
		return "redirect:/LogIn?error=true";
	}
	
	@RequestMapping("/SignUp")
	public String showSignUp(@RequestParam(value = "error", required = false) String error, Model model) {
    	User user = new User();
    	
    	model.addAttribute("user", user);
    	
    	if (error != null)
    		model.addAttribute("signupErrorMessage", error);
    	else
    		model.addAttribute("signupErrorMessage", "");
    	    	
    	return "SignUp";
	}
	
	@PostMapping("/processSignup")
    public String processSignup(@ModelAttribute("user") User user) {
		String error;
		
		if (user.getUsername().length() == 0 || user.getPassword().length() == 0)
			error = "Empty username or password are not allowed!";
		else {
			try {
				service.saveUser(user);
				
		        System.out.println("User signup successful: " + user);
		        return "SignUpSuccess";  // Redirect to a success page
			}
			catch (InvalidUserException e) {
				error = "Username already exists";
			} catch (IOException e) {
				error = "";
				e.printStackTrace();
			}
		}
		
		return "redirect:/SignUp?error=" + error;
    }
    
    @RequestMapping("/WASD")  
    public String showWASDPage() {
    	
    	System.out.println("WASD");
    	
    	
        return "WASD";
    }
    
}
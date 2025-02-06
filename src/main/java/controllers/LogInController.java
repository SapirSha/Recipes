package controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
    	User user = new User();
    	
    	model.addAttribute("user", user);
    	
    	if (error != null)
    		model.addAttribute("loginErrorMessage", "Invalid username or password.");
    	else
    		model.addAttribute("loginErrorMessage", "");
    	    	
    	return "LogIn";
    }
    
	@RequestMapping("/processLogin")
	public String processLogin(@ModelAttribute("user") User user, HttpServletRequest request) {
		System.out.println(user);
		
		try {
			User savedUser = service.getUserByName(user.getUsername());
			
			System.out.println(savedUser);
			
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
    
    @RequestMapping("/WASD")  
    public String showWASDPage() {
    	
    	System.out.println("WASD");
    	
    	
        return "WASD";
    }
    
}
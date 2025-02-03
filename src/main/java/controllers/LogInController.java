package controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Others.User;


@Controller
public class LogInController {

    @RequestMapping("/")  
    public String showPage() {
    	
    	System.out.println("LOGIN");
    	
    	
        return "redirect:/LogIn";
    }
    
    @RequestMapping("/LogIn")
    public String login(Model  model) {
    	User user = new User();
    	
    	model.addAttribute("user", user);
    	
    	return "LogIn";
    }
    
	@RequestMapping("/processLogin")
	public String processLogin(@ModelAttribute("user") User user, HttpServletRequest request) {
		System.out.println(user);
 
		if (user.getPassword().equals("abc")) {
			// create session and put user object on session
			request.getSession().setAttribute("user", user);
			return "WASD";
		}
		else System.out.println("FAILED LOGIN");
		return "redirect:/?incorrect=true";

	}
    
    @RequestMapping("/WASD")  
    public String showWASDPage() {
    	
    	System.out.println("WASD");
    	
    	
        return "WASD";
    }
    
}
package controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import Others.User;

@Controller
public class MainController {

	@RequestMapping("/")
	public String showPage(Model model, HttpServletRequest request) {
		if (request.getSession(false) != null 
				&& request.getSession().getAttribute("user") != null) { // Check if the user has a session
			User user = (User)request.getSession().getAttribute("user");
			
			model.addAttribute("helloMessage", "Hello " + user.getUsername() + "! ");
		}
		else
			model.addAttribute("helloMessage", "");
		
		return "MainPage";
	}

    @RequestMapping("/MyRecipes")
	public String showMyRecipesPage(HttpServletRequest request) {
    	if (request.getSession(false) == null) // Check if the user has a session
			return "redirect:/";
    	
		System.out.println("My recipes");
		return "MyRecipes";
	}

}

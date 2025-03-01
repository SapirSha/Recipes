package controllers;

import java.io.IOException;
import java.util.List;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import Exceptions.InvalidRecipeException;
import Exceptions.NoRecipeException;
import Exceptions.NoUserException;
import Others.Recipe;
import Others.Service;
import Others.User;

@Controller
public class MainController {
	@Autowired
	Service service;

	@RequestMapping("/")
	public String showPage(Model model,
			@RequestParam(value = "returnUrl", required = false) String returnUrl, HttpServletRequest request) {
		
		if (request.getSession(false) != null 
				&& request.getSession().getAttribute("user") != null) { // Check if the user has a session
			User user = (User)request.getSession().getAttribute("user");
			
			model.addAttribute("helloMessage", "Hello " + user.getUsername() + "! ");
		}
		else {
			model.addAttribute("helloMessage", "");
			model.addAttribute("isNotAuthorized", true);
		}
		
		return "MainPage";
	}
	@RequestMapping("Home")
	public String showPage(Model model, HttpServletRequest request) {
		return "redirect:/";
	}

    @RequestMapping("/MyRecipes")
	public String showMyRecipesPage(Model model,
			@RequestParam(value = "returnUrl", required = false) String returnUrl, HttpServletRequest request) {
    	
    	if (request.getSession(false) == null || request.getSession().getAttribute("user") == null) // Check if the user has a session
			return "redirect:/";
    	
		System.out.println("My recipes");
		
		List<Recipe> recipeList = ((User)request.getSession().getAttribute("user")).getRecipes();
		
		for (Recipe r : recipeList) {
			System.out.println(r);
		}
		
		model.addAttribute("recipeList", recipeList);
		
		return "MyRecipes";
	}
    
    
    
    @RequestMapping("/remove")
    public String removeRecipe(@RequestParam("id") String id, String selectedIndex, 
    		HttpServletRequest request) {
    	if (request.getSession(false) == null || request.getSession().getAttribute("user") == null) // Check if the user has a session
			return "redirect:/";
    	
    	try {
    		User u = (User)request.getSession().getAttribute("user");
    		int idINT = Integer.parseInt(id);
    		Recipe r = service.getRecipe(idINT);
    		service.deleteRecipe(u, r);
    	}
    	catch (NoUserException e) {
    		e.printStackTrace();
		} catch (InvalidRecipeException e) {
			e.printStackTrace();
		} catch (NoRecipeException e) {
			e.printStackTrace();
			System.out.println("Did not find recipe!");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	System.out.println("HERE");
    	return "redirect:/MyRecipes";
    }
    
    @RequestMapping("/logout")
    public String logoutOfUser(HttpServletRequest request) {
    	if (request.getSession(false) == null 
    			|| request.getSession().getAttribute("user") == null) // Check if the user has a session
			return "redirect:/";
    	
    	request.getSession().invalidate();
    	
    	return "redirect:/";
    }
    
    
    
    @RequestMapping("/SearchRecipe")
	public String showSearchRecipePage(Model model, HttpServletRequest request) {
		List<Recipe> recipeList;
		try {
			recipeList = service.getAllPublicRecipes();
		} catch (IOException e) {
			return "redirect:/";
		}
		
		for (Recipe r : recipeList) {
			System.out.println(r);
		}
		
		model.addAttribute("recipeList", recipeList);
    	
		return "SearchRecipe";
	}
}

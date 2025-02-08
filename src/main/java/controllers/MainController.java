package controllers;

import java.io.IOException;
import java.util.List;

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
	public String showMyRecipesPage(Model model, HttpServletRequest request) {
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
    
    @PostMapping("/remove")
    public String removeRecipe(@RequestParam(value = "recipeRadio", required = false) String selectedIndex, 
    		HttpServletRequest request) {
    	if (request.getSession(false) == null || request.getSession().getAttribute("user") == null) // Check if the user has a session
			return "redirect:/";
    	
    	try {
	    	if (selectedIndex != null) {
	    		int recipeIndex = Integer.parseInt(selectedIndex);
	    		User u = (User)request.getSession().getAttribute("user");
	    		Recipe r = u.getRecipes().get(recipeIndex);
	    		
	    		service.deleteRecipe(u, r);
	    	}
    	}
    	catch (NoUserException e) {
    		e.printStackTrace();
		} catch (InvalidRecipeException e) {
			e.printStackTrace();
		} catch (NoRecipeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return "redirect:/MyRecipes";
    }
    
}

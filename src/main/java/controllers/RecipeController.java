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

import Exceptions.InvalidRecipeException;
import Exceptions.NoRecipeException;
import Exceptions.NoUserException;
import Others.Recipe;
import Others.Service;
import Others.User;

@Controller
@RequestMapping("/recipe")
public class RecipeController {
	@Autowired
	Service service;
	
	@RequestMapping("/view")
	public String showRecipe(@ModelAttribute(name = "recipe") Recipe recipe, Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) // Check if the user has a session
			return "redirect:/";
		
		if (recipe.getId() == 0) // if recipe not passed (an empty object)
			return "/WASD";
		
		model.addAttribute("recipe", recipe);
		
		model.addAttribute("actionType", "v"); // "v" for view
		
		return "recipePage";
	}
	
	@RequestMapping("/edit")
	public String editRecipe(@RequestParam(value = "error", required = false) String error, 
			@ModelAttribute("recipe") Recipe recipe, Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) // Check if the user has a session
			return "redirect:/";
		
		if (recipe.getId() == 0) // if recipe not passed (an empty object)
			return "/WASD";
		
		if (error != null)
			model.addAttribute("errorMessage", error);
		
		model.addAttribute("recipe", recipe);
		
		model.addAttribute("actionType", "e"); // "e" for edit
		
		return "recipePage";
	}
	
	@RequestMapping("/create")
	public String createRecipe(Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) // Check if the user has a session
			return "redirect:/";
		
		model.addAttribute("recipe", new Recipe());
		
		model.addAttribute("actionType", "c"); // "c" for create
		
		return "recipePage";
	}
	
	@PostMapping("/saveRecipe")
	public String editRecipeProcess(@ModelAttribute("recipe") Recipe recipe, HttpServletRequest request) {
		try {
			User user = (User)request.getAttribute("user");
			
			service.editRecipe(user, recipe);
		}
		catch (NoRecipeException e) {
		} catch (InvalidRecipeException e) {
			return "redirect:/recipe/edit?error=Invlid recipe";
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoUserException e) {
			e.printStackTrace();
		}
		
		return "WASD";
	}
	
	@PostMapping("/createRecipe")
	public String createRecipeProcess(@ModelAttribute("recipe") Recipe recipe, HttpServletRequest request) {
		try {
			User user = (User)request.getAttribute("user");
			
			service.saveRecipe(user, recipe);
		}
		catch (InvalidRecipeException e) {
			return "redirect:/recipe/edit?error=Invlid recipe";
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoUserException e) {
			e.printStackTrace();
		}
		
		return "WASD";
	}
}

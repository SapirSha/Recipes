package controllers;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.omg.CORBA.Request;
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
public class RecipeController {
	@Autowired
	Service service;
	
    @RequestMapping("/ShowRecipe")
    public String showRecipeDetails(@ModelAttribute("ShowRecipe") Recipe recipe, Model model) {
    	System.out.println("SHOWING RECIPE");
    	
        recipe.setName("Chocolate Cake");
        recipe.setCategory( "Dessert");
        recipe.setDescription("A delicious chocolate cake.");
        recipe.setIngredients("Flour, Sugar, Cocoa, Baking Powder, Eggs, Milk, Butter, Vanilla");
        recipe.setInstructions("1. Mix ingredients. 2. Bake at 350°F for 30 minutes. 3. Enjoy!");
        recipe.setDateAdded(new Date());
        recipe.setDateLatestChange(new Date());
        
        model.addAttribute("recipe", recipe);
        
        System.out.println("HELLO" + recipe);
        
        return "RecipeDetails";
    }
    
	
	@RequestMapping("/EditRecipe")
	public String editRecipe(@RequestParam(value = "error", required = false) String error, 
			@ModelAttribute("EditRecipe") Recipe recipe, Model model, HttpServletRequest request) {
		
		if (request.getSession(false) == null)
			return "redirect:/";
		
		if (recipe.getId() == 0)
			return "/WASD";
		
		if (error != null)
			model.addAttribute("errorMessage", error);
		
		model.addAttribute("recipe", recipe);
		
		model.addAttribute("actionType", "e");
		
		return "recipePage";
	}
	
	@RequestMapping("/AddRecipe")
	public String createRecipe(Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) // Check if the user has a session
			return "redirect:/";
		
		model.addAttribute("recipe", new Recipe());
		
		model.addAttribute("actionType", "c"); // "c" for create
		
		return "AddRecipe";
	}

    @PostMapping("/AddRecipe")
    public String addRecipe(@ModelAttribute("AddRecipe") Recipe recipe, HttpServletRequest request) {
        System.out.println("Adding new recipe: " + recipe);
        
        /// ADD THE RECIPE
        try {
        	recipe.setDateAdded(new Date());
        	recipe.setDateLatestChange(new Date());
        service.saveRecipe((User)request.getSession().getAttribute("user"), recipe);
        }
        catch (Exception e) {
        	System.out.println("EXCEPTION IN ADD RECIPE: " + e);
            return "redirect:/MyRecipes?RecipeCreated=false";
        }

        
        return "redirect:/MyRecipes?RecipeCreated=true";
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
	
	@PostMapping("/CreateRecipe")
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

package controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@RequestMapping(value = "/ShowRecipePage")
	public String showRecipeDetails(@RequestParam("id") int id,
	                                @RequestParam("name") String name,
	                                @RequestParam("category") String category,
	                                @RequestParam("description") String description,
	                                @RequestParam("ingredients") String ingredients,
	                                @RequestParam("instructions") String instructions,
	                                RedirectAttributes redirectAttributes) {
	    // Create and populate a Recipe object
	    Recipe recipe = new Recipe(name, category, description, ingredients, instructions, new Date(), new Date());
	    recipe.setId(id);
	    // Add as a flash attribute (retained only for the next request)
	    redirectAttributes.addFlashAttribute("ShowRecipe", recipe);
	    
	    // Redirect to the second method
	    return "redirect:/ShowRecipe";
	}


	
	@RequestMapping("/ShowRecipe")
	public String showRecipeDetails(@ModelAttribute("ShowRecipe") Recipe recipe, Model model) {

	    if (recipe.getId() == 0)
	    	return "redirect:/MyRecipes";

	    model.addAttribute("recipe", recipe);
	    	    
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
            return "redirect:/MyRecipes?RecipeCreated=false"; //////////////////////////////////
        }

        
        return "redirect:/MyRecipes?RecipeCreated=true";
    }
	
	@PostMapping("/SaveRecipe")
	public String editRecipeProcess(@ModelAttribute("recipe") Recipe recipe, HttpServletRequest request, Model model) {
		if (request.getSession(false) == null || request.getSession().getAttribute("user") == null) // Check if the user has a session
			return "redirect:/";
		
		System.out.println("SAVE RECIPE: " + recipe);
		
		try {
			User user = (User)request.getSession().getAttribute("user");

//			System.out.println("USER-------------" + service.getUserByName(user.getUsername()));
			service.editRecipe(user, recipe);
		}
		catch (NoRecipeException e) {
		} catch (InvalidRecipeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoUserException e) {
			e.printStackTrace();
		}
    			
		List<Recipe> recipeList = ((User)request.getSession().getAttribute("user")).getRecipes();
		
		for (Recipe r : recipeList) {
			System.out.println(r);
		}
		
		model.addAttribute("recipeList", recipeList);
		
		return "MyRecipes";
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

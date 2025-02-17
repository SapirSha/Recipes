package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Exceptions.InvalidRecipeException;
import Exceptions.InvalidUserException;
import Exceptions.NoRecipeException;
import Exceptions.NoUserException;
import Others.Recipe;
import Others.Service;
import Others.User;

import RestClasses.GeneralRestResponse;

@RestController
@RequestMapping("/api")
public class APIController {
	@Autowired
	private Service service;
	
	private User checkAndGetUser(String username, String password) throws NoUserException {
		try {
			User user = service.getUserByName(username);
			
			if (user.getPassword().equals(password)) {
				return user;
			}		
		}
		catch (NoUserException e) { // To change exception message
		}
		
		throw new NoUserException("Invalid username or password");
	}
	
	@GetMapping("/signup/{username}/{password}")
	public GeneralRestResponse apiSignup(@PathVariable String username, 
			@PathVariable String password) throws InvalidUserException, IOException {
		try {
			User user = new User(username, password);
			
			service.saveUser(user);
			
			return new GeneralRestResponse(true, "success");
		}
		catch (InvalidUserException e) {
			return new GeneralRestResponse(false, "Invalid user");
		}
	}
	
	@GetMapping("/user/{username}/{password}")
	public User apiGetUser(@PathVariable String username, 
			@PathVariable String password) throws NoUserException {
		return checkAndGetUser(username, password);
	}
	
	@GetMapping("/allRecipes/{username}/{password}")
	public List<Recipe> apiGetAllRecipes(@PathVariable String username, 
			@PathVariable String password) throws NoUserException {
		return checkAndGetUser(username, password).getRecipes();
	}
	
	@GetMapping("/findRecipe/{username}/{password}/{recipeName}")
	public Recipe apiGetRecipe(@PathVariable String username, 
			@PathVariable String password, @PathVariable String recipeName) throws NoUserException, NoRecipeException {
		return service.getRecipe(checkAndGetUser(username, password), recipeName);
	}
	
	@GetMapping("/editRecipe/name/{username}/{password}/{recipeName}/{newName}")
	public GeneralRestResponse apiRecipeEditName(@PathVariable String username, 
			@PathVariable String password, @PathVariable String recipeName, 
			@PathVariable String newName) throws NoUserException, NoRecipeException, CloneNotSupportedException, InvalidRecipeException, IOException {
		User user = checkAndGetUser(username, password);
		
		Recipe recipe = service.getRecipeClone(user, recipeName);
		
		recipe.setName(newName);
		
		service.editRecipe(user, recipe);
		
		return new GeneralRestResponse(true, "success");
	}
	
	@GetMapping("/editRecipe/category/{username}/{password}/{recipeName}/{newCategory}")
	public GeneralRestResponse apiRecipeEditcategory(@PathVariable String username, 
			@PathVariable String password, @PathVariable String recipeName, 
			@PathVariable String newCategory) throws NoUserException, NoRecipeException, CloneNotSupportedException, InvalidRecipeException, IOException {
		User user = checkAndGetUser(username, password);
		
		Recipe recipe = service.getRecipeClone(user, recipeName);
		
		recipe.setCategory(newCategory);
		
		service.editRecipe(user, recipe);
		
		return new GeneralRestResponse(true, "success");
	}
	
	@GetMapping("/editRecipe/description/{username}/{password}/{recipeName}/{newDescription}")
	public GeneralRestResponse apiRecipeEditDescription(@PathVariable String username, 
			@PathVariable String password, @PathVariable String recipeName, 
			@PathVariable String newDescription) throws NoUserException, NoRecipeException, CloneNotSupportedException, InvalidRecipeException, IOException {
		User user = checkAndGetUser(username, password);
		
		Recipe recipe = service.getRecipeClone(user, recipeName);
		
		recipe.setDescription(newDescription);
		
		service.editRecipe(user, recipe);
		
		return new GeneralRestResponse(true, "success");
	}
	
	
}

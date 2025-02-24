package DataAccess;
import java.io.IOException;
import java.util.List;

import Others.Recipe;
import Others.User;
import Exceptions.InvalidRecipeException;
import Exceptions.InvalidUserException;
import Exceptions.NoRecipeException;
import Exceptions.NoUserException;

public interface ISerializer {
	// Checks if a recipe exists in the DB by its name
	public boolean recipeExistsByName(User user, String name) throws IOException;
	
	// Checks if a recipe exists in the DB by its id
	public boolean recipeExistsById(int id) throws IOException;
	
	// Save a recipe to the DB. Do nothing if name already exists.
	// Return true if added successfully and false if not added
	public void saveRecipe(User user, Recipe recipe) throws IOException, NoUserException, InvalidRecipeException;
	
	// Delete a recipe from the DB. Return true if deleted successfully 
	// and false if not (if name doesn't exist, do nothing and return false)
	public void deleteRecipe(User user, Recipe recipe) throws NoRecipeException, IOException, NoUserException;
	
	// Receives a recipe and changes the recipe in the DB with the same id
	// to be equal to the received recipe. Return true if changed successfully 
	// and false if not (if id doesn't exist in the DB, do nothing and return false)
	public void editRecipe(User user, Recipe changedRecipe) throws NoRecipeException, IOException, NoUserException, InvalidRecipeException;
	
	// Retrieve a recipe from the DB. Return null if not found
	public Recipe getRecipeByName(User user, String recipeName) throws NoRecipeException, NoUserException, IOException;
	
	// Retrieve a recipe from the DB. Return null if not found
	public Recipe getRecipeById(int id) throws NoRecipeException, IOException;
	
	// Return in a list all of the ids of the recipes currently stored 
	// in the DB (Return an empty list if the DB doesn't contain any recipe)
	public List<Integer> getRecipeIds();
	
	// Return in a list all of the names of the recipes currently stored 
	// in the DB (Return an empty list if the DB doesn't contain any recipe)
	public List<String> getRecipeNames();
	
	// Retrieve the names of all of the recipes in the DB of a specific category
	public List<Integer> getRecipesIdByCategory(String category);
	
	// Retrieve all of the recipes in the DB
	public List<Recipe> getAllRecipes();
	
	// Retrieve all of the recipes in the DB of a specific category
	public List<Recipe> getRecipesByCategory(User user, String category) throws NoUserException, IOException;
	
	public List<Recipe> getAllUserRecipes(User user) throws NoUserException, IOException;
	
	public void saveUser(User user) throws InvalidUserException, IOException;
	
	public void deleteUser(User user) throws NoUserException, IOException;
	
	public User getUserByName(String username) throws NoUserException, IOException;
	
	public User getUserById(int id) throws NoUserException, IOException;
}

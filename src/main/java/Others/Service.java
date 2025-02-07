package Others;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import DataAccess.ISerializer;
import Exceptions.InvalidRecipeException;
import Exceptions.InvalidUserException;
import Exceptions.NoRecipeException;
import Exceptions.NoUserException;

@Component
@PropertySource("classpath:params.properties")
public class Service {
	@Autowired
	private ISerializer serializer;

	@Value("${maxNameLen}")
	private int maxNameLength;

	@Value("${maxDescLen}")
	private int maxDescriptionLength;

	@Value("${maxIngredientsLen}")
	private int maxIngredients;

	@Value("${maxInstructionsLen}")
	private int maxInstructions;

	public void saveRecipe(User user, Recipe recipe) throws InvalidRecipeException, IOException, NoUserException {
		validate(recipe); // throws InvalidRecipe Exception

		if (serializer.recipeExistsByName(recipe.getName()))
			throw new InvalidRecipeException("Recipe already exists!\n");

		serializer.saveRecipe(user, recipe);
	}

	public Recipe getRecipe(User user, String name) throws NoRecipeException, NoUserException {
		return serializer.getRecipeByName(user, name);
	}

	public Recipe getRecipeClone(User user, String name) throws CloneNotSupportedException, NoRecipeException, NoUserException {
		Recipe r = getRecipe(user, name);

		if (r == null) {
			return null;
		}

		return r.clone();
	}

	public Recipe getRecipe(int id) throws NoRecipeException {
		return serializer.getRecipeById(id);
	}

	public List<Recipe> getAllRecipes() {
		return serializer.getAllRecipes();
	}

	public List<Recipe> getAllRecipesFromCategory(User user, String category) throws NoUserException {
		return serializer.getRecipesByCategory(user, category);
	}

	public void deleteRecipe(User user, Recipe recipe) throws InvalidRecipeException, NoRecipeException, IOException, NoUserException {
		serializer.deleteRecipe(user, recipe);
	}

	public void editRecipe(User user, Recipe newInfo) throws InvalidRecipeException, IOException, NoRecipeException, NoUserException {
		int id = newInfo.getId();

		validate(newInfo); // throws InvalidRecipeException
		
		Recipe oldInfo = serializer.getRecipeById(id);
		if (oldInfo == null)
			throw new NoRecipeException("not an existing recipe!");
		
		if (!newInfo.getName().equalsIgnoreCase(oldInfo.getName())) {
			if (serializer.recipeExistsByName(newInfo.getName())) {
				throw new InvalidRecipeException("New name already exists for a differenct recipe!");
			}
		}

		serializer.editRecipe(user, newInfo);
	}
	
	public User getUserByName(String username) throws NoUserException {
		return serializer.getUserByName(username);
	}
	
	public void saveUser(User user) throws InvalidUserException, IOException {
		serializer.saveUser(user);
	}

	// VALIDATIONS //

	public boolean validate(Recipe recipe) throws InvalidRecipeException {
		String errorMsg = "";

		if (!isValidName(recipe.getName())) {
			errorMsg += "Invalid Name!\n";
		}
		if (!isValidCategory(recipe.getCategory())) {
			errorMsg += "Invalid Category!\n";
		}
		if (!isValidDescription(recipe.getDescription())) {
			errorMsg += "Invalid Description!\n";
		}
		if (!isValidIngredients(recipe.getIngredients())) {
			errorMsg += "Invalid Ingredients!\n";
		}
		if (!isValidInstructions(recipe.getInstructions())) {
			errorMsg += "Invalid Instructions!\n";
		}

		if (!errorMsg.equals("")) {
			throw new InvalidRecipeException(errorMsg);
		}

		return true;
	}

	public boolean isValidName(String name) {
		return name != null && !name.trim().isEmpty() && name.length() <= maxNameLength;
	}

	public boolean isValidCategory(String category) {
		return category != null && !category.trim().replaceAll("[ \n\t]", "").isEmpty();
	}

	public boolean isValidDescription(String desc) {
		return desc != null && !desc.trim().replaceAll("[ \n\t]", "").isEmpty() && desc.length() <= maxDescriptionLength;
	}

	public boolean isValidIngredients(List<String> ingredients) {
		return ingredients != null && !ingredients.isEmpty() && ingredients.size() <= maxIngredients;
	}

	public boolean isValidInstructions(List<String> instructions) {
		return instructions != null && !instructions.isEmpty() && instructions.size() <= maxInstructions;
	}
}

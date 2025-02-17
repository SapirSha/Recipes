//package DataAccess;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.mvc.method.annotation.SessionAttributeMethodArgumentResolver;
//
//import javax.annotation.PostConstruct;
//
//import Others.Recipe;
//import Others.User;
//import Exceptions.InvalidRecipeException;
//import Exceptions.InvalidUserException;
//import Exceptions.NoRecipeException;
//import Exceptions.NoUserException;
//
//@Component
//public class FileSerializer implements ISerializer {
//	
//	private List<User> users;
//	private static int lastRecipeId;
//	
//	@PostConstruct
//	public void getRecipes() {
//		try {
//			FileInputStream fileIn = new FileInputStream("recipes.ser");
//			ObjectInputStream in = new ObjectInputStream(fileIn);
//			users = (List<User>) in.readObject();
//			fileIn.close();
//			in.close();
//		} catch (IOException | ClassNotFoundException e) {
//			users = new ArrayList<>();
//		}
//		
//		lastRecipeId = findMaxRecipeId();
//		
////		// for testing (without an actual DB)
////		try {
////			saveUser(new User("admin", "admin"));
////			saveUser(new User("user1", "1234"));
////			saveUser(new User("user2", "4321"));
////			
////			System.out.println("printing all users:");
////			
////			for (User u : users) {
////				System.out.println(u);
////			}
////		}
////		catch (InvalidUserException e) {
////			System.out.println("Tried to create an invalid user!");
////		} catch (IOException e) {
////			System.out.println("IO exception");
////		}
//		
////		try {
////			Recipe r = new Recipe();
////			
////			r.setName("sushi");
////			r.setCategory("asian");
////			r.setDescription("a roll of rice with vegetables and fish");
////			r.setIngredients("-fish\n-rice\n-avocado");
////			r.setInstructions("1. cook rice\n2. roll");
////			
////			saveRecipe(users.get(1), r);
////			
////			r = new Recipe();
////			
////			r.setName("pasta bolognese");
////			r.setCategory("italian");
////			r.setDescription("A classic dish of pasta with meat and sauce");
////			r.setIngredients("-pasta\n-red meat\n-tomato sauce\n-onion");
////			r.setInstructions("1. cook the pasta\n2. cut the onion\n3. cook the onion with the meat"
////					+ "\n4. add tomato sauce\n5. add pasta");
////			
////			saveRecipe(users.get(1), r);
////		}
////		catch (Exception e) {
////			e.printStackTrace();
////		}
//	}
//	
//	private int findMaxRecipeId() {
//		int maxId = 0;
//		
//		for (User user : users) {
//			for (Recipe recipe : user.getRecipes()) {
//				if (recipe.getId() > maxId)
//					maxId = recipe.getId();
//			}
//		}
//		
//		return maxId;
//	}
//	
//	public void SerializeData(List<User> users) throws IOException {
//		FileOutputStream fileOut = new FileOutputStream("recipes.ser");
//		ObjectOutputStream out = new ObjectOutputStream(fileOut);
//		out.writeObject(users);
////			System.out.println("Serialized data is saved in recipes.ser");
//		
//		fileOut.close();
//		out.close();
//	}
//
//	
//	@Override
//	public boolean recipeExistsByName(String name) {
//
//		for (User user : users) {
//			for (Recipe recipe : user.getRecipes()) {
//				if (recipe.getName().equalsIgnoreCase(name)) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//
//	@Override
//	public boolean recipeExistsById(int id) {
//		for (User user : users) {
//			for (Recipe recipe : user.getRecipes()) {
//				if (recipe.getId() == id) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//
//	@Override
//	public void saveRecipe(User user, Recipe recipe) throws IOException, NoUserException, InvalidRecipeException {
//		if (!users.contains(user))
//			throw new NoUserException("User '" + user.getUsername() + "' does not exist.");
//		
//		for (Recipe r : user.getRecipes()) {
//			if (r.getName().equals(recipe.getName()))
//				throw new InvalidRecipeException("Recipe with the name '" + recipe.getName() + "' already exists.");
//		}
//		
//		recipe.setId(++lastRecipeId);
//		
//		user.addRecipe(recipe);
//
//		SerializeData(users);
//	}
//
//	@Override
//	public void deleteRecipe(User user, Recipe recipe) throws NoRecipeException, IOException, NoUserException {
//		if (!users.contains(user))
//			throw new NoUserException("User '" + user.getUsername() + "' does not exist.");
//		
//		if (!user.getRecipes().contains(recipe))
//			throw new NoRecipeException("Recipe '" + recipe.getName() + "' does not exist.");
//		
//		user.getRecipes().remove(recipe);
//		
//		SerializeData(users);
//	}
//
//	@Override
//	public void editRecipe(User user, Recipe changedRecipe) throws NoRecipeException, IOException, NoUserException {
//		if (!users.contains(user))
//			throw new NoUserException("User '" + user.getUsername() + "' does not exist.");
//		
//		if (user.getRecipes().contains(changedRecipe)) {
//			user.getRecipes().set(user.getRecipes().indexOf(changedRecipe), changedRecipe);
//			SerializeData(users);
//			return;
//		}
//		
//		throw new NoRecipeException("Recipe '" + changedRecipe.getName() + "' does not exist.");
//	}
//
//	@Override
//	public Recipe getRecipeByName(User user, String name) throws NoRecipeException, NoUserException {
//		if (!users.contains(user))
//			throw new NoUserException("User '" + user.getUsername() + "' does not exist.");
//		
//		for (Recipe recipe : user.getRecipes()) {
//			if (recipe.getName().equalsIgnoreCase(name)) {
//				return recipe;
//			}
//		}
//		
//		throw new NoRecipeException("Recipe '" + name + "' does not exist.");
//	}
//
//	@Override
//	public Recipe getRecipeById(int id) throws NoRecipeException {
//		System.out.println(id);
//		
//		System.out.println(users);
//		
//		for (User user : users) {
//			for (Recipe recipe : user.getRecipes()) {
//				System.out.println(recipe);
//				if (recipe.getId() == id) {
//					return recipe;
//				}
//			}
//		}
//		
//		throw new NoRecipeException("Recipe with the id " + id + " does not exist.");
//	}
//
//	@Override
//	public List<Integer> getRecipeIds() {
//		List<Integer> recipeIds = new ArrayList<Integer>();
//		
//		for (User user : users) {
//			for (Recipe recipe : user.getRecipes()) {
//				recipeIds.add(recipe.getId());
//			}
//		}
//		
//		return recipeIds;
//	}
//
//	@Override
//	public List<String> getRecipeNames() {
//		List<String> recipeNames = new ArrayList<String>();
//		
//		for (User user : users) {
//			for (Recipe recipe : user.getRecipes()) {
//				recipeNames.add(recipe.getName());
//			}
//		}
//		
//		return recipeNames;
//	}
//
//	@Override
//	public List<Integer> getRecipesIdByCategory(String category) {
//		List<Integer> recipeIds = new ArrayList<Integer>();
//		
//		for (User user : users) {
//			for (Recipe recipe : user.getRecipes()) {
//				if (recipe.getCategory().equalsIgnoreCase(category))
//					recipeIds.add(recipe.getId());
//			}
//		}
//		
//		return recipeIds;
//	}
//
//	@Override
//	public List<Recipe> getAllRecipes() {
//		ArrayList<Recipe> tempRecipes = new ArrayList<>();
//		
//		for (User user : users) {
//			for (Recipe recipe : user.getRecipes()) {
//				tempRecipes.add(recipe);
//			}
//		}
//		
//		return tempRecipes;
//	}
//
//	@Override
//	public List<Recipe> getRecipesByCategory(User user, String category) throws NoUserException {
//		if (!users.contains(user))
//			throw new NoUserException("User '" + user.getUsername() + "' does not exist.");
//		
//		ArrayList<Recipe> tempRecipes = new ArrayList<>();
//		
//		for (Recipe recipe : user.getRecipes()) {
//			if (recipe.getCategory().equalsIgnoreCase(category))
//				tempRecipes.add(recipe);
//		}
//		
//		return tempRecipes;
//	}
//
//	@Override
//	public List<Recipe> getAllUserRecipes(User user) {
//		return user.getRecipes();
//	}
//
//	@Override
//	public void saveUser(User user) throws InvalidUserException, IOException {
//		if (user.getUsername() == null || user.getPassword() == null)
//			throw new InvalidUserException("User must have username and password!");
//		
//		for (User u : users) {
//			if (u.getUsername().equals(user.getUsername())) {
//				throw new InvalidUserException("User with the name '" + user.getUsername() + "' already exists.");
//			}
//		}
//		
//		if (users.size() > 0) {
//			user.setId(users.get(users.size() - 1).getId() + 1);
//		}
//		else {
//			user.setId(1);
//		}
//		
//		users.add(user);
//		
//		SerializeData(users);
//	}
//
//	@Override
//	public void deleteUser(User user) throws NoUserException, IOException {
//		if (!users.contains(user))
//			throw new NoUserException("User '" + user.getUsername() + "' does not exist.");
//		
//		users.remove(user);
//		
//		SerializeData(users);
//	}
//
//	@Override
//	public User getUserByName(String username) throws NoUserException {
//		for (User user : users) {
//			if (user.getUsername().equals(username))
//				return user;
//		}
//		
//		throw new NoUserException("User with the name '" + username + "' does not exist.");
//	}
//
//	@Override
//	public User getUserById(int id) throws NoUserException {
//		for (User user : users) {
//			if (user.getId() == id)
//				return user;
//		}
//		
//		throw new NoUserException("User with the id " + id + " does not exist.");
//	}
//
//}
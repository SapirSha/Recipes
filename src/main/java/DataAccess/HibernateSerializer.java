package DataAccess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import Exceptions.InvalidRecipeException;
import Exceptions.InvalidUserException;
import Exceptions.NoRecipeException;
import Exceptions.NoUserException;
import Others.Recipe;
import Others.User;


@Component
public class HibernateSerializer implements ISerializer {
	
	//TODO add everything here
	
	private SessionFactory factory;
	
	@PostConstruct
	public void getRecipes() {
		
		factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(User.class)
				.addAnnotatedClass(Recipe.class)
				.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
//		try	{
//			session.beginTransaction();
//			Query<User> query = session.createQuery("from User", User.class);
//			users = query.getResultList();
//			
//			for (User user : users) {
//				int userId = user.getId();
//				Query<Recipe> recipeQuery = session.createQuery(
//						"from Recipe r where r.user.id = :userId", Recipe.class);
//				recipeQuery.setParameter("userId", userId);
//	            
//	            List<Recipe> recipes = recipeQuery.getResultList();
//	            for (Recipe recipe : recipes) {
//	            	user.addRecipe(recipe);
//	            }
//	        }
//			
//		} finally {
//			session.close();
//			factory.close();
//		}
	}
	
	public void SerializeData(List<User> users) throws IOException {
		SessionFactory factory = new Configuration()
				 .configure("hibernate.cfg.xml")
				 .buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			 session.beginTransaction();
			 for (User user : users) {
				 session.save(user);
				 for (Recipe recipe: user.getRecipes())
					 session.save(recipe);
			 }
			 session.getTransaction().commit();
		} catch (Exception e) {
            throw new IOException("Error saving data");
		}
	}	

	@Override
	public boolean recipeExistsByName(String name) {

		for (User user : users) {
			for (Recipe recipe : user.getRecipes()) {
				if (recipe.getName().equalsIgnoreCase(name)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean recipeExistsById(int id) {
		for (User user : users) {
			for (Recipe recipe : user.getRecipes()) {
				if (recipe.getId() == id) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void saveRecipe(User user, Recipe recipe) throws IOException, NoUserException, InvalidRecipeException {
		if (!users.contains(user))
			throw new NoUserException("User '" + user.getUsername() + "' does not exist.");
		
		for (Recipe r : user.getRecipes()) {
			if (r.getName().equals(recipe.getName()))
				throw new InvalidRecipeException("Recipe with the name '" + recipe.getName() + "' already exists.");
		}
		
		user.addRecipe(recipe);

		SerializeData(users);
	}

	@Override
	public void deleteRecipe(User user, Recipe recipe) throws NoRecipeException, IOException, NoUserException {
		if (!users.contains(user))
			throw new NoUserException("User '" + user.getUsername() + "' does not exist.");
		
		if (!user.getRecipes().contains(recipe))
			throw new NoRecipeException("Recipe '" + recipe.getName() + "' does not exist.");
		
		user.getRecipes().remove(recipe);
		
		SerializeData(users);
	}

	@Override
	public void editRecipe(User user, Recipe changedRecipe) throws NoRecipeException, IOException, NoUserException {
		if (!users.contains(user))
			throw new NoUserException("User '" + user.getUsername() + "' does not exist.");
		
		if (user.getRecipes().contains(changedRecipe)) {
			user.getRecipes().set(user.getRecipes().indexOf(changedRecipe), changedRecipe);
			SerializeData(users);
			return;
		}
		
		throw new NoRecipeException("Recipe '" + changedRecipe.getName() + "' does not exist.");
	}

	@Override
	public Recipe getRecipeByName(User user, String name) throws NoRecipeException, NoUserException {
		if (!users.contains(user))
			throw new NoUserException("User '" + user.getUsername() + "' does not exist.");
		
		for (Recipe recipe : user.getRecipes()) {
			if (recipe.getName().equalsIgnoreCase(name)) {
				return recipe;
			}
		}
		
		throw new NoRecipeException("Recipe '" + name + "' does not exist.");
	}

	@Override
	public Recipe getRecipeById(int id) throws NoRecipeException {
		//System.out.println(id);		
		//System.out.println(users);
		
		for (User user : users) {
			for (Recipe recipe : user.getRecipes()) {
				//System.out.println(recipe);
				if (recipe.getId() == id) {
					return recipe;
				}
			}
		}
		
		throw new NoRecipeException("Recipe with the id " + id + " does not exist.");
	}

	@Override
	public List<Integer> getRecipeIds() {
		List<Integer> recipeIds = new ArrayList<Integer>();
		
		for (User user : users) {
			for (Recipe recipe : user.getRecipes()) {
				recipeIds.add(recipe.getId());
			}
		}
		
		return recipeIds;
	}

	@Override
	public List<String> getRecipeNames() {
		List<String> recipeNames = new ArrayList<String>();
		
		for (User user : users) {
			for (Recipe recipe : user.getRecipes()) {
				recipeNames.add(recipe.getName());
			}
		}
		
		return recipeNames;
	}

	@Override
	public List<Integer> getRecipesIdByCategory(String category) {
		List<Integer> recipeIds = new ArrayList<Integer>();
		
		for (User user : users) {
			for (Recipe recipe : user.getRecipes()) {
				if (recipe.getCategory().equalsIgnoreCase(category))
					recipeIds.add(recipe.getId());
			}
		}
		
		return recipeIds;
	}

	@Override
	public List<Recipe> getAllRecipes() {
		ArrayList<Recipe> tempRecipes = new ArrayList<>();
		
		for (User user : users) {
			for (Recipe recipe : user.getRecipes()) {
				tempRecipes.add(recipe);
			}
		}
		
		return tempRecipes;
	}

	@Override
	public List<Recipe> getRecipesByCategory(User user, String category) throws NoUserException {
		if (!users.contains(user))
			throw new NoUserException("User '" + user.getUsername() + "' does not exist.");
		
		ArrayList<Recipe> tempRecipes = new ArrayList<>();
		
		for (Recipe recipe : user.getRecipes()) {
			if (recipe.getCategory().equalsIgnoreCase(category))
				tempRecipes.add(recipe);
		}
		
		return tempRecipes;
	}

	@Override
	public List<Recipe> getAllUserRecipes(User user) {
		return user.getRecipes();
	}

	@Override
	public void saveUser(User user) throws InvalidUserException, IOException {
		if (user.getUsername() == null || user.getPassword() == null)
			throw new InvalidUserException("User must have username and password!");
		
		for (User u : users) {
			if (u.getUsername().equals(user.getUsername())) {
				throw new InvalidUserException("User with the name '" + user.getUsername() + "' already exists.");
			}
		}		
		users.add(user);		
		SerializeData(users);
	}

	@Override
	public void deleteUser(User user) throws NoUserException, IOException {
		if (!users.contains(user))
			throw new NoUserException("User '" + user.getUsername() + "' does not exist.");
		
		users.remove(user);
		
		SerializeData(users);
	}

	@Override
	public User getUserByName(String username) throws NoUserException {
		for (User user : users) {
			if (user.getUsername().equals(username))
				return user;
		}
		
		throw new NoUserException("User with the name '" + username + "' does not exist.");
	}

	@Override
	public User getUserById(int id) throws NoUserException {
		for (User user : users) {
			if (user.getId() == id)
				return user;
		}
		
		throw new NoUserException("User with the id " + id + " does not exist.");
	}
}

package DataAccess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.relational.Database;
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
	
	// STATUS: everything seems to be working
	// To add potentially: dateLastestChange (seems to work properly with dateAdded)
	
	private SessionFactory factory;
	
	@PostConstruct
	public void initializeSessionFactory() {
		
		factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(User.class)
				.addAnnotatedClass(Recipe.class)
				.buildSessionFactory();
	}
	
	@PreDestroy
	public void closeSessionFactory() {
		factory.close();
	}
	
	// throws exception if no such user exists, or if something went wrong when accessing the database
	public void userExistsQuery(User user) throws NoUserException, IOException {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {		
			User userFromDB = session.get(User.class, user.getId());
			if (userFromDB == null)
				throw new NoUserException("User '" + user.getUsername() + "' does not exist.");
			//Query<User> userExistsQuery = session.createQuery("FROM User WHERE user_id = :userId", User.class);
			//userExistsQuery.setParameter("userId", user.getId());
			//if (userExistsQuery.uniqueResult() == null)
			//	throw new NoUserException("User '" + user.getUsername() + "' does not exist.");
		} catch (NoUserException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("Something went wrong when accessing the database");
		} finally {
			session.close();
		}
	}

	// throws exception if user doesn't have a recipe with name recipe.getName(),
	// or if something went wrong when accessing the database
	public void recipeNameExistsQuery(User user, Recipe recipe) throws NoUserException, IOException, NoRecipeException {
		userExistsQuery(user);
		
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {		
			Query<Recipe> recipeExistsQuery = session.createQuery(
					"FROM Recipe WHERE recipe_name = :recipeName AND user_user_id = :userId", Recipe.class);
			recipeExistsQuery.setParameter("recipeName", recipe.getName());
			recipeExistsQuery.setParameter("userId", user.getId());
			
			if (recipeExistsQuery.uniqueResult() == null)
				throw new NoRecipeException("Recipe with the name '" + recipe.getName() + "' doesn't exist.");
		} catch (NoRecipeException e) {
			throw e;			
		} catch (Exception e) {
			throw new IOException("Something went wrong when accessing the database");
		} finally {
			session.close();
		}
	}
	
	@Override
	public boolean recipeExistsByName(User user, String name) throws IOException {
		try {
			
			userExistsQuery(user);
		} catch(NoUserException e) {
			return false;
		}
		
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {							
			Query<Recipe> recipeExistsQuery = session.createQuery(
					"FROM Recipe WHERE recipe_name = :recipeName AND user_user_id = :userId", Recipe.class);
			recipeExistsQuery.setParameter("recipeName", name);
			recipeExistsQuery.setParameter("userId", user.getId());
			
			Recipe recipe = recipeExistsQuery.uniqueResult();
			if (recipe == null)
				return false;
			
			return true;
		} catch (Exception e) {
			throw new IOException("Something went wrong when accessing the database");
		} finally {
			session.close();
		}
	}

	@Override
	public boolean recipeExistsById(int id) throws IOException {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {		
			Query<Recipe> recipeExistsQuery = session.createQuery(
					"FROM Recipe WHERE recipe_id = :recipeId", Recipe.class);
			recipeExistsQuery.setParameter("recipeId", id);
			
			Recipe recipe = recipeExistsQuery.uniqueResult();
			
			if (recipe == null)
				return false;
			
			return true;
			
		} catch (Exception e) {
			throw new IOException("Something went wrong when accessing the database");
		} finally {
			session.close();
		}
	}

	@Override
	public void saveRecipe(User user, Recipe recipe) throws IOException, NoUserException, InvalidRecipeException {
		userExistsQuery(user);
		
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {		

			recipe.setUser(user);
			Query<Recipe> recipeExistsQuery = session.createQuery(
					"FROM Recipe WHERE recipe_name = :recipeName AND user_user_id = :userId", Recipe.class);
			recipeExistsQuery.setParameter("recipeName", recipe.getName());
			recipeExistsQuery.setParameter("userId", user.getId());
			
			
			if (recipeExistsQuery.uniqueResult() != null)
				throw new InvalidRecipeException("Recipe with the name '" + recipe.getName() + "' already exists.");
			
			user.add(recipe);
			session.save(recipe);
			session.getTransaction().commit();
			
		} catch (InvalidRecipeException e) {
			throw e;
		} catch (Exception e) {
			user.removeRecipe(recipe);
			session.getTransaction().rollback();
			throw new IOException("Something went wrong, did not save");
		} finally {
			session.close();
		}
	}

	@Override
	public void deleteRecipe(User user, Recipe recipe) throws NoRecipeException, IOException, NoUserException {
		userExistsQuery(user);
		recipeNameExistsQuery(user, recipe);
		
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {
			user.removeRecipe(recipe);
			session.remove(recipe);
			session.getTransaction().commit();
		} catch (Exception e) {
			user.add(recipe);
			session.getTransaction().rollback();
			throw new IOException("Something went wrong, did not delete");
		} finally {
			session.close();
		}
	}

	@Override
	public void editRecipe(User user, Recipe changedRecipe) throws NoRecipeException, IOException, NoUserException {
		userExistsQuery(user);
		
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		
		Recipe existingRecipe = session.get(Recipe.class, changedRecipe.getId());
		 if (existingRecipe == null) {
	            throw new NoRecipeException("Attempting to edit a non-existent recipe");
	        }
		 
		try {
			existingRecipe.setName(changedRecipe.getName());
			existingRecipe.setCategory(changedRecipe.getCategory());
			existingRecipe.setDescription(changedRecipe.getDescription());
			existingRecipe.setIngredients(changedRecipe.getIngredients());
			existingRecipe.setInstructions(changedRecipe.getInstructions());
			existingRecipe.setDateLatestChange(changedRecipe.getDateLatestChange());
			existingRecipe.setIsPrivate((changedRecipe.getIsPrivate()));
			session.update(existingRecipe);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new IOException("Something went wrong, edit was not saved");
		} finally {
			session.close();
		}
	}

	@Override
	public Recipe getRecipeByName(User user, String name) throws NoRecipeException, NoUserException, IOException {
		userExistsQuery(user);
		
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {
			Query<Recipe> recipeExistsQuery = session.createQuery(
					"FROM Recipe WHERE recipe_name = :recipeName AND user_user_id = :userId", Recipe.class);
			recipeExistsQuery.setParameter("recipeName", name);
			recipeExistsQuery.setParameter("userId", user.getId());
			
			Recipe recipe = recipeExistsQuery.uniqueResult();
			if (recipe == null)
				throw new NoRecipeException ("Recipe with the name '" + name + "' doesn't exist.");
			
			return recipe;
			
		} catch (NoRecipeException e) {
			throw e;
		} catch (Exception e) {
			throw new IOException("Something went wrong when accessing the database");
		} finally {
			session.close();
		}		
	}

	@Override
	public Recipe getRecipeById(int id) throws NoRecipeException, IOException {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {		
			Query<Recipe> recipeExistsQuery = session.createQuery(
					"FROM Recipe WHERE recipe_id = :recipeId", Recipe.class);
			recipeExistsQuery.setParameter("recipeId", id);
			
			Recipe recipe = recipeExistsQuery.uniqueResult();
			
			if (recipe == null)
				throw new NoRecipeException("Recipe with the id " + id + " does not exist.");
			
			return recipe;
			
		} catch (Exception e) {
			throw new IOException("Something went wrong when accessing the database");
		} finally {
			session.close();
		}
	}
	
	@Override
	public List<Recipe> getRecipesByCategory(User user, String category) throws NoUserException, IOException {
		userExistsQuery(user);
		
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		
		try {
			Query<Recipe> getRecipesByCategoryQuery = session.createQuery(
				    "FROM Recipe WHERE recipe_category = :category AND user_user_id = :userId", Recipe.class);
			getRecipesByCategoryQuery.setParameter("category", category);
			getRecipesByCategoryQuery.setParameter("userId", user.getId());

			return getRecipesByCategoryQuery.getResultList();
		} catch (Exception e) {
			throw new IOException("Something went wrong when accessing the database");
		}
	}

	@Override
	public List<Recipe> getAllUserRecipes(User user) throws NoUserException, IOException {
		userExistsQuery(user);
		
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {
			Query<Recipe> getUserRecipesQuery = session.createQuery(
				    "FROM Recipe WHERE user_user_id = :userId", Recipe.class);
			getUserRecipesQuery.setParameter("userId", user.getId());

			return getUserRecipesQuery.getResultList();
		} catch (Exception e) {
			throw new IOException("Something went wrong when accessing the database");
		} finally {
			session.close();
		}
	}

	@Override
	public void saveUser(User user) throws InvalidUserException, IOException {
		if (user.getUsername() == null || user.getPassword() == null)
			throw new InvalidUserException("User must have username and password!");
		
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {		
			Query<User> userExistsQuery = session.createQuery("FROM User WHERE user_id = :userId", User.class);
			userExistsQuery.setParameter("userId", user.getId());
			if (userExistsQuery.uniqueResult() != null)
				throw new InvalidUserException("User with the name'" + user.getUsername() + "' already exists.");
			
			session.save(user);
			session.getTransaction().commit(); 
			
		} catch (InvalidUserException e) {
			throw e;
		} catch (Exception e) {
			throw new IOException("Something went wrong when accessing the database");
		} finally {
			session.close();
		}		
	}

	// Don't think this is needed
	@Override
	public void deleteUser(User user) throws NoUserException, IOException {
		userExistsQuery(user);
		
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		
		try {
			session.remove(user);
			session.getTransaction().commit();
		} catch (Exception e) {
			throw new IOException("Something went wrong, did not delete");
		} finally {
			session.close();
		}
	}

	@Override
	public User getUserByName(String username) throws NoUserException, IOException {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {		
			Query<User> userExistsQuery = session.createQuery("FROM User WHERE username = :username", User.class);
			userExistsQuery.setParameter("username", username);
			
			User user = userExistsQuery.uniqueResult();
			//User user = session.get(User.class, username);
			System.out.println(user);
			System.out.println(user.getRecipes());
			if (user == null)
				throw new NoUserException("User with the name '" + username + "' does not exist.");
			
			return user;
			
		} catch (NoUserException e) {
			throw e;
		} catch (Exception e) {
			throw new IOException("Something went wrong when accessing the database");
		} finally {
			session.close();
		}
	}

	@Override
	public User getUserById(int id) throws NoUserException, IOException {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {		
			//Query<User> userExistsQuery = session.createQuery("FROM User WHERE user_id = :userId", User.class);
			//userExistsQuery.setParameter("userId", id);
			
			
			//User user = userExistsQuery.uniqueResult();
			User user = session.get(User.class, id);
			System.out.println(user);
			System.out.println(user.getRecipes());
			if (user == null)
				throw new NoUserException("User with the id '" + id + "' does not exist.");
			
			return user;
			
		} catch (NoUserException e) {
			throw e;
		} catch (Exception e) {
			throw new IOException("Something went wrong when accessing the database");
		} finally {
			session.close();
		}
	}	
	
	@Override
	public List<Recipe> getAllPublicRecipes() throws IOException {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {
			Query<Recipe> getAllPublicRecipesQuery = session.createQuery(
				    "FROM Recipe WHERE recipe_private = 0", Recipe.class);

			return getAllPublicRecipesQuery.getResultList();
		} catch (Exception e) {
			throw new IOException("Something went wrong when accessing the database");
		} finally {
			session.close();
		}
	}
	
	@Override
	public User getRecipeOwner(Recipe recipe) throws IOException {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {
			System.out.println("here also" + recipe.getId());
			Query<User> getRecipeOwnerQuery = session.createQuery(
				    "FROM User u WHERE u.id = (SELECT r.user.id FROM Recipe r WHERE r.id = :recipeId)",
				    User.class
			);
			getRecipeOwnerQuery.setParameter("recipeId", recipe.getId());

			System.out.println("here");

			return getRecipeOwnerQuery.getSingleResult();
		} catch (Exception e) {
			throw new IOException("Something went wrong when accessing the database");
		} finally {
			session.close();
		}
	}
	// THESE FUNCTIONS ARE NEVER USED
	
	@Override
	public List<String> getRecipeNames() {
		return null;
	}
	
	@Override
	public List<Integer> getRecipesIdByCategory(String category) {
		return null;
	}
	
	@Override
	public List<Recipe> getAllRecipes() { 
		return null;
	}
	
	@Override
	public List<Integer> getRecipeIds() {
		return null;
	}
}

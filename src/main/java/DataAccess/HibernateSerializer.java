package DataAccess;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import Exceptions.InvalidRecipeException;
import Exceptions.InvalidUserException;
import Exceptions.NoRecipeException;
import Exceptions.NoUserException;
import Others.Recipe;
import Others.User;

public class HibernateSerializer implements ISerializer {
	
	private List<User> users;
	
	@PostConstruct
	public void getRecipes() {
		
		SessionFactory factory = new Configuration()
								 .configure("hibernate.cfg.xml")
								 .buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		try	{
			session.beginTransaction();
			Query<User> query = session.createQuery("from User", User.class);
			users = query.getResultList();
			
			for (User user : users) {
				int userId = user.getId();
				Query<Recipe> recipeQuery = session.createQuery("from Recipe r where r.user.id = :userId", Recipe.class);
				recipeQuery.setParameter("userId", userId);
	            
	            List<Recipe> recipes = recipeQuery.getResultList();
	            for (Recipe recipe : recipes) {
	            	user.addRecipe(recipe);
	            }
	        }
			
		} finally {
			session.close();
			factory.close();
		}
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean recipeExistsById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void saveRecipe(User user, Recipe recipe) throws IOException, NoUserException, InvalidRecipeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRecipe(User user, Recipe recipe) throws NoRecipeException, IOException, NoUserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editRecipe(User user, Recipe changedRecipe) throws NoRecipeException, IOException, NoUserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Recipe getRecipeByName(User user, String recipeName) throws NoRecipeException, NoUserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Recipe getRecipeById(int id) throws NoRecipeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getRecipeIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getRecipeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getRecipesIdByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Recipe> getAllRecipes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Recipe> getRecipesByCategory(User user, String category) throws NoUserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Recipe> getAllUserRecipes(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveUser(User user) throws InvalidUserException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(User user) throws NoUserException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getUserByName(String username) throws NoUserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserById(int id) throws NoUserException {
		// TODO Auto-generated method stub
		return null;
	}

}

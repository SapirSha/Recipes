package Others;
import java.util.List;
import Others.Recipe;


public class User {
	private List<Recipe> recipes;
	private String username;
	private String password;
	
	public User() {}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getPassword(){
		return password;
	}
	
	public List<Recipe> getRecipes() {
		return this.recipes;
	}
	
	public void addRecipe(Recipe recipe) {
		this.recipes.add(recipe);
	}
	
	public void removeRecipe(Recipe recipe) {
		recipes.remove(recipe);
	}
	
	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}
	
	@Override
	public String toString() {
		String msg =String.format("Username %s, Password: ********\n", this.username);
		for (Recipe recipe : recipes) 
			msg += recipe + "\n";
		
		return msg;
	}
	
	
	
	
	
	
	
	
}

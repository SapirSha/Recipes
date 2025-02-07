package Others;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import Others.Recipe;


public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private List<Recipe> recipes;
	private String username;
	private String password;
	
	
	public User() {
		recipes = new ArrayList<>();
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.recipes = new ArrayList<>();
	}
	
	public int getId() {
		return id;
	}
	
	
	
	public void setId(int id) {
		this.id = id;
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
		String msg =String.format("Username %s, Password: %s\n", this.username, this.password);
		
		for (Recipe recipe : recipes) 
			msg += recipe + "\n";
		
		return msg;
	}

	@Override
	public boolean equals(Object arg0) {
		if (!(arg0 instanceof User))
			return false;
		
		User other = (User)arg0;
		
		return this.username.equals(other.username) && this.password.equals(other.password);
	}
	
}

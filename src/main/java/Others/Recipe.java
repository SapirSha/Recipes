package Others;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;


@Entity
@Table(name = "recipe")
public class Recipe implements Comparable<Recipe>, Cloneable, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recipe_id")
	private int id;
	
	@Column(name = "recipe_name")
	private String name;
	
	@Column(name = "recipe_category")
	private String category;
	
	@Column(name = "recipe_description")
	private String description;
	
	@Column(name = "recipe_ingredients")
	private String ingredients;
	
	@Column(name = "recipe_instructions")
	private String instructions;
	
	@Column(name = "date_added")
	private Date dateAdded;
	
	@Column(name = "date_latest_change")
	private Date dateLatestChange;
	
	
	@Column(name = "user_user_id")
	@JoinColumn(name = "user_user_id")
	private int userId;
	
	public Recipe() {}
	
	public Recipe(String name, String category, String description, String ingredients, String instructions,
			Date dateAdded, Date dateLatestChange) {
		super();
		this.name = name;
		this.category = category;
		this.description = description;
		this.ingredients = ingredients;
		this.instructions = instructions;
		this.dateAdded = dateAdded;
		this.dateLatestChange = dateLatestChange;
	}
	
	public Recipe(String name, String category, String description, String ingredients, String instructions,
			Date dateAdded, Date dateLatestChange, int userId) {
		super();
		this.name = name;
		this.category = category;
		this.description = description;
		this.ingredients = ingredients;
		this.instructions = instructions;
		this.dateAdded = dateAdded;
		this.dateLatestChange = dateLatestChange;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getIngredients() {
		return ingredients;
	}
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	
	public String getInstructions() {
		return instructions;
	}
	
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	public Date getDateAdded() {
		return dateAdded;
	}
	
	public void setDateAdded(Date date) {
		dateAdded = date;
	}

	public Date getDateLatestChange() {
		return dateLatestChange;
	}

	public void setDateLatestChange(Date dateLatestChange) {
		this.dateLatestChange = dateLatestChange;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		
		if (!(obj instanceof Recipe))
			return false;
		
		Recipe other = (Recipe)obj;
		
		return id == other.id;
	}
	
	@Override
	public int compareTo(Recipe other) {
		// Compare the recipes by name
		return name.compareTo(other.name);
	}

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", name=" + name + ", category=" + category + ", description=" + description
				+ ", ingredients=" + ingredients + ", instructions=" + instructions + ", dateAdded=" + dateAdded
				+ ", dateLatestChange=" + dateLatestChange + "]";
	}
	
	@Override
	public Recipe clone() throws CloneNotSupportedException {
		 Recipe clonedRecipe = (Recipe) super.clone();
		 clonedRecipe.dateAdded = (Date) dateAdded.clone();
		 clonedRecipe.dateLatestChange = (Date) dateLatestChange.clone();
		 
		return clonedRecipe;
	}
	
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

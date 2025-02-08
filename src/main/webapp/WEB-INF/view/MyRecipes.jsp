<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Recipes</title>
    <link rel="stylesheet" type="text/css" href="resources/css/main.css">
    <script type="text/javascript" src="resources/js/main.js"></script>
    <script>
    
 // Ensure the DOM is loaded before attaching listeners
    document.addEventListener("DOMContentLoaded", function() {
        // Attach event listener to all "Show" buttons
        const showButtons = document.querySelectorAll(".show-recipe-btn");
        showButtons.forEach(button => {
            button.addEventListener("click", function() {
                // Create a recipe data object from data attributes
                const recipeData = {
                    id: this.dataset.id,
                    name: this.dataset.name,
                    category: this.dataset.category,
                    description: this.dataset.description,
                    ingredients: this.dataset.ingredients,
                    instructions: this.dataset.instructions
                };
                // Populate modal fields with the recipe data
                document.getElementById("recipe-id").value = recipeData.id;
                document.getElementById("recipe-name").value = recipeData.name;
                document.getElementById("recipe-category").value = recipeData.category;
                document.getElementById("recipe-description").value = recipeData.description;
                document.getElementById("recipe-ingredients").value = recipeData.ingredients;
                document.getElementById("recipe-instructions").value = recipeData.instructions;
                
                // Open the modal (assuming your openModal function is defined accordingly)
                openModal("addRecipeModal");
            });
        });
    });

 
    </script>
</head>
<body>
    <%@ include file="common/header.jsp" %>
    <%@ include file="common/LoginSignupModal.jsp" %>
    
    <%@ include file="common/AddRecipeModal.jsp" %>

    <div class="content">
        <h1>My Recipes Page</h1>
        
        <br>
        <br>
        
        <form method="post">
	        <div class="recipe-button-container">
	            <button type="button" onclick="openModal('addRecipeModal')">Create</button>
	            <button type="submit" formaction="remove">Remove</button>
	        </div>
			
            <table class="recipe-table">
                <thead>
                <tr>
                    <th>Select</th>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Description</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="recipe" items="${recipeList}" varStatus="status">
                    <tr>
                        <td>
                            <div>
                                <input type="radio" name="recipeRadio" value="${status.index}">
                            </div>
                        </td>
                        <td>${recipe.name}</td>
                        <td>${recipe.category}</td>
                        <td>${recipe.description}</td>
                        <td>
						    <button type="button"
						            class="show-recipe-btn"
						            data-id="${recipe.id}"
						            data-name="${recipe.name}"
						            data-category="${recipe.category}"
						            data-description="${recipe.description}"
						            data-ingredients="${recipe.ingredients}"
						            data-instructions="${recipe.instructions}">
						        Show
						    </button>
						</td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </form>
    </div>
</body>
</html>
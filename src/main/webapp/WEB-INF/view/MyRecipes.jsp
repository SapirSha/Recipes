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
    
    document.addEventListener("DOMContentLoaded", function() {
        const showButtons = document.querySelectorAll(".show-recipe-btn");
        const contextPath = "${pageContext.request.contextPath}"; // Ensure this is properly retrieved

        showButtons.forEach(button => {
            button.addEventListener("click", function() {
                const recipeData = {
                    id: this.dataset.id || "",
                    name: this.dataset.name || "",
                    category: this.dataset.category || "",
                    description: this.dataset.description || "",
                    ingredients: this.dataset.ingredients || "",
                    instructions: this.dataset.instructions || ""
                };

                console.log("Recipe Data: ", recipeData);

                const params = new URLSearchParams(recipeData).toString();

                console.log("Query String: " + params);

                const url = contextPath + "/ShowRecipePage?" + params;

                console.log("Navigating to: " + url); // Debugging log

                window.location.href = url;
            });
        });
    });

    </script>
    <style>

.show-recipe-btn {
  background-color: #007BFF;
  color: #fff;
  border: none;
  border-radius: 4px;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: inline-block;
}


.show-recipe-btn:hover {
  background-color: #0056b3;
  transform: translateY(-2px);
}


.show-recipe-btn:active {
  transform: translateY(0);
}


table .show-recipe-btn {
  margin: 5px 0;
}
    
    </style>
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
						        class="show-recipe-btn show-button"
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
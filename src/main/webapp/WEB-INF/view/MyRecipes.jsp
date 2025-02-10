<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Recipes</title>
    <link rel="stylesheet" type="text/css" href="resources/css/main.css">
    <script type="text/javascript" src="resources/js/main.js"></script>
    

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

.delete-recipe-btn {
  background-color: #A52A2A;
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


.delete-recipe-btn:hover {
  background-color: #8B0000;
  transform: translateY(-2px);
}


.delete-recipe-btn:active {
  transform: translateY(0);
}


table .delete-recipe-btn {
  margin: 5px 0;
}

.createBtn {
  background-color: #74992e;;
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


.createBtn:hover {
  background-color: #008000;
  transform: translateY(-2px);
}


.createBtn:active {
  transform: translateY(0);
}

.actions{
display: flex;
justify-content: right;
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
			
            <table class="recipe-table">
                <thead>
                <tr>
                    <th style="width: 20%">Name</th>
                    <th style="width: 20%">Category</th>
                    <th>Description</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="recipe" items="${recipeList}" varStatus="status">
                    <tr>
                        <td>${recipe.name}</td>
                        <td>${recipe.category}</td>
                        <td>${recipe.description}</td>
                        <td class="actions">
						<button type="button" style="margin: 0 25px"
						        class="show-recipe-btn show-button"
						        data-id="${recipe.id}"
						        data-name="${recipe.name}"
						        data-category="${recipe.category}"
						        data-description="${recipe.description}"
						        data-ingredients="${recipe.ingredients}"
						        data-instructions="${recipe.instructions}">
						    Show
						</button>
						<button type="button" style="margin: 0 25px"
						        class="delete-recipe-btn show-button"
						        data-id="${recipe.id}">
						    Delete
						</button>
						</td>

                    </tr>
                </c:forEach>
                <tr>
                </tr>
                </tbody>
            </table>
            
            <button type="button" class="createBtn" onclick="openModal('addRecipeModal')" style="width:100%; margin=50px 0; position:flex">+</button>
        </form>
    </div>
    
        <script>
    document.addEventListener("DOMContentLoaded", function() {
        const showButtons = document.querySelectorAll(".show-recipe-btn");
        const deleteButtons = document.querySelectorAll(".delete-recipe-btn");
        const contextPath = "${pageContext.request.contextPath}";

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
        
        deleteButtons.forEach(button => {
            button.addEventListener("click", function() {
            	const recipeID = this.dataset.id;

                const url = contextPath + "/remove?id=" + recipeID;

                console.log("Navigating to: " + url);

                window.location.href = url;
            });
        
        
        });
    });
    
    function getUrlParameter(name) {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(name);
    }
    
    const createError = getUrlParameter('RecipeCreated');

    const modal = document.querySelector('.recipe-container');
    const modalContent = document.querySelector('.modal-content');
    console.log(createError)
    if (createError == "false"){
	    modal.style.display = 'flex';
	    
	    setTimeout(() => {
	        modal.style.opacity = '1';
	        modal.style.pointerEvents = 'auto';
	        modalContent.style.opacity = '1';
	        modalContent.style.transform = 'translateY(0)';
	    }, 10);
    }
    </script>
    
</body>
</html>
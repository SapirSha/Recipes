<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Recipes</title>
    
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

        .search-bar {
            margin-bottom: 20px;
        }

        .search-input {
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
            width: calc(100% - 120px);
        }

        .search-button {
            padding: 10px 20px;
            font-size: 16px;
            background-color:		#228B22;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        .search-button:hover {
            background-color: 	#228B22;
        }

        .search-button:active {
            transform: translateY(0);
        }
    </style>
</head>
<body>
    <%@ include file="common/header.jsp" %>
    <div class="content">
        <h1>Search Recipe</h1>
        
        <form method="get" action="SearchRecipe">
            <div class="search-bar">
                <input name="searchQuery" class="search-input" placeholder="Enter recipe name..." required />
                <button type="submit" class="search-button">Search</button>
            </div>
        </form>
        
        <!-- Existing Recipe List Table -->
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
                        </td>
                    </tr>
                </c:forEach>
                <tr></tr>
            </tbody>
        </table>
    </div>
    
    <c:choose>
        <c:when test="${empty sessionScope.user}">
            <%@ include file="common/LoginSignupModal.jsp" %>
        </c:when>
        <c:otherwise>
        </c:otherwise>
    </c:choose>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            const showButtons = document.querySelectorAll(".show-recipe-btn");
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
        });
    </script>
</body>
</html>

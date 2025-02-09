<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add New Recipe</title>
        <link rel="stylesheet" type="text/css" href="resources/css/AddRecipeModal.css">
    <script type="text/javascript" src="resources/js/AddRecipeModal.js">
    </script>
</head>
<body>
<div class="add-recipe-modal" style="display: contents">
    <div id="addRecipeModal" class="recipe-container">
        <div class="modal-content">
            <span class="close" onclick="closeModal('addRecipeModal')">&times;</span>
            <h1>Add New Recipe</h1>
            <form:form method="POST" modelAttribute="recipe" action="/Recipes/AddRecipe">
                <div class="editable">
                    <strong>Name:</strong>
                    <input id="new-recipe-name" name="name" class="input-field" required="required"/>
                </div>
                <div class="editable">
                    <strong>Category:</strong>
                    <input id="new-recipe-category" name="category" class="input-field" required="required"/>
                </div>
                <div class="editable">
                    <strong>Description:</strong>
                    <textarea id="new-recipe-description" name="description" class="input-field" oninput="autoExpandTextarea(event)" required="required"></textarea>
                </div>
                <div class="editable">
                    <strong>Ingredients:</strong>
                    <textarea id="new-recipe-ingredients" name="ingredients" class="input-field" oninput="autoExpandTextarea(event)" required="required"></textarea>
                </div>
                <div class="editable">
                    <strong>Instructions:</strong>
                    <textarea id="new-recipe-instructions" name="instructions" class="input-field" oninput="autoExpandTextarea(event)" required="required"></textarea>
                </div>
                <div class="edit-button">
                    <button type="submit" class="save-btn">Add Recipe</button>
                </div>
                 
            <c:if test="${param.RecipeCreated eq 'false'}">
                <div class="error-message">
                    A recipe already has the same name.
                </div>
            </c:if>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>

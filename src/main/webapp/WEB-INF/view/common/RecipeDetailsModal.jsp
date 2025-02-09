<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Recipe Details</title>
    <link rel="stylesheet" type="text/css" href="resources/css/RecipeDetailsModal.css">
    <script type="text/javascript" src="resources/js/RecipeDetailsModal.js"></script>
    
</head>
<body>
<div class="recipe-editor" style="display:contents">
    <div class="recipe-container">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <h1>Recipe Details</h1>
            <form:form method="POST" modelAttribute="ShowRecipe" action="SaveRecipe">
                <form:hidden path="id" id="recipe-id" />
                <div class="editable">
                    <strong>Name:</strong> <span>${recipe.name}</span>
                    <form:input path="name" id="recipe-name" class="input-field" />
                </div>
                <div class="editable">
                    <strong>Category:</strong> <span>${recipe.category}</span>
                    <form:input path="category" id="recipe-category" class="input-field" />
                </div>
                <div class="editable">
                    <strong>Description:</strong> <span>${recipe.description}</span>
                    <form:textarea path="description" id="recipe-description" class="input-field" oninput="autoExpandTextarea(event)"></form:textarea>
                </div>
                <div class="editable">
                    <strong>Ingredients:</strong> <span>${recipe.ingredients}</span>
                    <form:textarea path="ingredients" id="recipe-ingredients" class="input-field" oninput="autoExpandTextarea(event)"></form:textarea>
                </div>
                <div class="editable">
                    <strong>Instructions:</strong> <span>${recipe.instructions}</span>
                    <form:textarea path="instructions" id="recipe-instructions" class="input-field" oninput="autoExpandTextarea(event)"></form:textarea>
                </div>
                <div class="edit-button">
                    <button type="button" id="editButton" class="edit-btn" onclick="toggleEditMode()">Edit</button>
                    <button type="submit" id="saveButton" class="save-btn">Save</button>
                </div>
                
				<div id="error-message input-field" style="display: none;" class="error-message">
				    Recipe name is already registered!
				</div>

            </form:form>
        </div>
    </div>
</div>
</body>
</html>

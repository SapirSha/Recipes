<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Recipe Details</title>
    <style>
    @charset "ISO-8859-1";

.recipe-editor {
  font-family: 'Arial', sans-serif;
  background-color: #f2f2f2;
  margin: 0;
  padding: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
}

.recipe-container {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  width: 100%;
  animation: fadeIn 0.5s;
}

h1 {
  text-align: center;
  color: #333;
}

.editable {
  margin: 20px 0;
}
.editable strong {
  display: block;
  margin-bottom: 8px;
  color: #555;
}

.input-field {
  width: 100%;
  padding: 10px;
  box-sizing: border-box;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 16px;
  transition: border-color 0.3s;
}
.input-field:focus {
  border-color: #007BFF;
  outline: none;
}
textarea {
  resize: none;
  overflow: hidden;
}

.edit-button {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

button {
  padding: 12px 20px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
  font-size: 16px;
}
button:hover {
  opacity: 0.9;
}

.edit-btn {
  background-color: #007BFF;
  color: white;
}
.save-btn {
  background-color: #28A745;
  color: white;
}
.cancel-btn {
  background-color: #6c757d;
  color: white;
}
.return-btn {
  background-color: #DC3545;
  color: white;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}
    
    </style>
    <script>
    document.addEventListener("DOMContentLoaded", function () {
        autoExpandAllTextareas();
    });

    function enterEditMode() {
        document.querySelectorAll(".display-value").forEach(span => span.style.display = "none");
        document.querySelectorAll(".input-field").forEach(input => {
            input.style.display = "block";
            if (input.tagName.toLowerCase() === "textarea") {
                autoExpandTextarea({ target: input });
            }
        });
        document.getElementById("editButton").style.display = "none";
        document.getElementById("saveButton").style.display = "inline-block";
        document.getElementById("cancelButton").style.display = "inline-block";
    }

    function cancelEditMode() {
        document.getElementById("recipeForm").reset();
        document.querySelectorAll(".input-field").forEach(input => input.style.display = "none");
        document.querySelectorAll(".display-value").forEach(span => span.style.display = "inline");
        document.getElementById("editButton").style.display = "inline-block";
        document.getElementById("saveButton").style.display = "none";
        document.getElementById("cancelButton").style.display = "none";
    }

    function autoExpandTextarea(event) {
        let element = event.target;
        element.style.height = "auto";
        element.style.height = element.scrollHeight + "px";
    }

    function autoExpandAllTextareas() {
        document.querySelectorAll("textarea").forEach(textarea => {
            textarea.style.height = "auto";
            textarea.style.height = textarea.scrollHeight + "px";
        });
    }

    function goBack() {
        window.history.back();
    }

    </script>
</head>
<body>
    <div class="recipe-editor">
        <div class="recipe-container">
            <h1>Recipe Details</h1>
            <form:form id="recipeForm" method="POST" modelAttribute="ShowRecipe" action="SaveRecipe">
                <form:hidden path="id" id="recipe-id" />
                
                <!-- Name Field -->
                <div class="editable">
                    <strong>Name:</strong>
                    <span class="display-value" id="display-name">${recipe.name}</span>
                    <form:input path="name" id="recipe-name" class="input-field" style="display:none;" />
                </div>
                
                <!-- Category Field -->
                <div class="editable">
                    <strong>Category:</strong>
                    <span class="display-value" id="display-category">${recipe.category}</span>
                    <form:input path="category" id="recipe-category" class="input-field" style="display:none;" />
                </div>
                
                <!-- Description Field -->
                <div class="editable">
                    <strong>Description:</strong>
                    <span class="display-value" id="display-description">${recipe.description}</span>
                    <form:textarea path="description" id="recipe-description" class="input-field" style="display:none;" oninput="autoExpandTextarea(event)"></form:textarea>
                </div>
                
                <!-- Ingredients Field -->
                <div class="editable">
                    <strong>Ingredients:</strong>
                    <span class="display-value" id="display-ingredients">${recipe.ingredients}</span>
                    <form:textarea path="ingredients" id="recipe-ingredients" class="input-field" style="display:none;" oninput="autoExpandTextarea(event)"></form:textarea>
                </div>
                
                <!-- Instructions Field -->
                <div class="editable">
                    <strong>Instructions:</strong>
                    <span class="display-value" id="display-instructions">${recipe.instructions}</span>
                    <form:textarea path="instructions" id="recipe-instructions" class="input-field" style="display:none;" oninput="autoExpandTextarea(event)"></form:textarea>
                </div>
                
                <!-- Buttons -->
                <div class="edit-button">
                    <button type="button" id="editButton" class="edit-btn" onclick="enterEditMode()">Edit</button>
                    <button type="submit" id="saveButton" class="save-btn" style="display:none;">Save</button>
                    <button type="button" id="cancelButton" class="cancel-btn" style="display:none;" onclick="cancelEditMode()">Cancel</button>
                    <button type="button" id="returnButton" class="return-btn" onclick="goBack()">Return</button>
                </div>
            </form:form>
        </div>
    </div>
</body>
</html>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Recipe Details</title>
  <style>
    body {
      background: url("${recipeImageUrl}") no-repeat center center fixed;
      background-size: cover;
      margin: 0;
      padding: 0;
    }

    @charset "ISO-8859-1";

    .recipe-editor {
      font-family: 'Arial', sans-serif;
      margin: 0;
      padding: 20px;
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
      position: relative;
      z-index: 1; 
    }

    .recipe-container {
      background-color: rgba(255, 255, 255, 0.95);
      padding: 30px;
      border-radius: 16px;
      box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
      max-width: 800px;
      width: 100%;
      animation: fadeIn 0.5s;
      transition: transform 0.3s ease, opacity 0.3s ease;
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
      border-radius: 10px;
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
      border-radius: 20px;
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

    .error-message {
      display: none;  
      background-color: #ff4d4d; 
      color: white;  
      padding: 10px 15px;
      border-radius: 5px;
      font-size: 16px;
      font-weight: bold;
      text-align: center;
      width: fit-content;
      margin: 10px auto;
      box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
      animation: fadeIn 0.5s ease-in-out;
    }

    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(-10px); }
      to { opacity: 1; transform: translateY(0); }
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
      const contextPath = "${pageContext.request.contextPath}";
      let userElement = document.getElementById("EditableData");
      let usersAreTheSame = userElement.dataset.usersAreTheSame === 'true';
      if (usersAreTheSame) {
          window.location.href = contextPath + "/MyRecipes";
      } else {
          window.location.href = contextPath + "/SearchRecipe";
      }
    }
  </script>
</head>
<body>
  <div id="EditableData" 
       data-users-are-the-same="${Editable == true}" 
       style="opacity: 0; display: none;">
  </div>

  <div class="recipe-editor">
    <div class="recipe-container">
      <h1>Recipe Details</h1>
      <form:form id="recipeForm" method="POST" modelAttribute="ShowRecipe" action="SaveRecipe">
        <form:hidden path="id" id="recipe-id" />

        <div class="editable">
          <strong>Name:</strong>
          <span class="display-value" id="display-name">${recipe.name}</span>
          <form:input path="name" id="recipe-name" class="input-field" style="display:none;" />
        </div>

        <div class="editable">
          <strong>Category:</strong>
          <span class="display-value" id="display-category">${recipe.category}</span>
          <form:input path="category" id="recipe-category" class="input-field" style="display:none;" />
        </div>

        <div class="editable">
          <strong>Description:</strong>
          <span class="display-value" id="display-description">${recipe.description}</span>
          <form:textarea path="description" id="recipe-description" class="input-field" style="display:none;" 
              oninput="autoExpandTextarea(event)"></form:textarea>
        </div>

        <div class="editable">
          <strong>Ingredients:</strong>
          <span class="display-value" id="display-ingredients">${recipe.ingredients}</span>
          <form:textarea path="ingredients" id="recipe-ingredients" class="input-field" style="display:none;" 
              oninput="autoExpandTextarea(event)"></form:textarea>
        </div>

        <div class="editable">
          <strong>Instructions:</strong>
          <span class="display-value" id="display-instructions">${recipe.instructions}</span>
          <form:textarea path="instructions" id="recipe-instructions" class="input-field" style="display:none;" 
              oninput="autoExpandTextarea(event)"></form:textarea>
        </div>

        <c:choose>
          <c:when test="${Editable == true}">
            <div class="editable">
              <strong>Is Recipe Private?</strong>
              <span class="display-value" id="display-privacy">
                ${recipe.isPrivate == 1 ? 'Yes' : 'No'}
              </span>
              <div class="input-field" style="display:none; border:none;">
                <form:radiobutton path="isPrivate" id="recipe_private_yes" value="1" />
                <label for="recipe_private_yes" style="margin-right:5%">Yes</label>
                <form:radiobutton path="isPrivate" id="recipe_private_no" value="0" />
                <label for="recipe_private_no">No</label>
              </div>
            </div>

            <div id="error-message" style="display: none;" class="error-message">
              Recipe name is already registered!
            </div>
            <div class="edit-button">
              <button type="button" id="editButton" class="edit-btn" onclick="enterEditMode()">Edit</button>
              <button type="submit" id="saveButton" class="save-btn" style="display:none;">Save</button>
              <button type="button" id="cancelButton" class="cancel-btn" style="display:none;" onclick="cancelEditMode()">Cancel</button>
              <button type="button" id="returnButton" class="return-btn" onclick="goBack()">Return</button>
            </div>
          </c:when>
          <c:otherwise>
            <div class="edit-button">
              <p>Notice! - You are not the owner of this recipe.</p>
              <button type="button" id="returnButton" class="return-btn" onclick="goBack()">Return</button>
            </div>
          </c:otherwise>
        </c:choose>
      </form:form>

      <script>
        function getUrlParameter(name) {
          const urlParams = new URLSearchParams(window.location.search);
          return urlParams.get(name);
        }
        const saveError = getUrlParameter('SaveError');
        if (saveError === 'true') {
          const errorMessageDiv = document.getElementById('error-message');
          if (errorMessageDiv) {
            errorMessageDiv.style.display = 'block';
          }
        }
      </script>
    </div>
  </div>
</body>
</html>

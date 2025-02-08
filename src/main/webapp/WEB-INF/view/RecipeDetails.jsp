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
            font-family: 'Arial', sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 20px;
        }
        h1 {
            color: #333;
        }

        /* MODAL CONTAINER */
        .recipe-container {
            opacity: 0;
            pointer-events: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow-y: auto;
            background-color: rgba(0, 0, 0, 0.4);
            display: flex;
            justify-content: center;
            align-items: flex-start;
            padding-top: 20px;
            transition: opacity 0.5s ease-in-out;
        }

        /* MODAL CONTENT */
        .modal-content {
            background-color: #fefefe;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            max-width: 800px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            position: relative;
            transition: transform 0.5s ease-in-out, opacity 0.5s ease-in-out;
            max-height: 90vh;
            overflow-y: auto;
            transform: translateY(-50px);
            opacity: 0;
            
                /* Hide scrollbar */
    scrollbar-width: none; /* Firefox */
    -ms-overflow-style: none; /* IE/Edge */
        }
        
        .modal-content::-webkit-scrollbar {
    display: none; /* Chrome, Safari */
}

        /* CLOSE BUTTON */
        .close {
            color: #aaa;
            position: absolute;
            top: 10px;
            right: 15px;
            font-size: 28px;
            font-weight: bold;
        }
        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

        /* FORM INPUTS */
        .editable {
            margin: 20px 0;
        }
        .editable strong {
            display: block;
            margin-bottom: 8px;
        }
        .editable input, .editable textarea {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 16px;
            transition: border-color 0.3s;
        }
        .editable input:focus, .editable textarea:focus {
            border-color: #007BFF;
            outline: none;
        }
        .editable textarea {
            resize: none;
            overflow: hidden;
        }

        /* BUTTONS */
        .edit-button {
            display: flex;
            justify-content: flex-end;
            margin-top: 20px;
        }
        .edit-button button {
            padding: 12px 20px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.3s ease, color 0.3s ease;
            font-size: 16px;
        }
        .edit-button button:hover {
            opacity: 0.9;
        }
        .edit-button .edit-btn {
            background-color: #007BFF;
            color: white;
        }
        .edit-button .save-btn {
            background-color: #28A745;
            color: white;
            display: none;
        }
    </style>

    <script>
    
    window.onload = function() {
        const elements = document.querySelectorAll('.editable');
        elements.forEach(element => {
            const input = element.querySelector('input, textarea');
            const span = element.querySelector('span');

                input.style.opacity = '0';
                input.style.display = 'none';
                span.style.display = 'inline-block';
                span.style.opacity = '1';
            
        });
    }
    
        function openModal() {
            const modal = document.querySelector('.recipe-container');
            const modalContent = document.querySelector('.modal-content');
            
            // Make the modal container visible by updating opacity
            modal.style.display = 'flex';
            setTimeout(() => {
                modal.style.opacity = '1';  // Ensure opacity transitions correctly
                modal.style.pointerEvents = 'auto';  // Enable pointer events when visible
                modalContent.style.opacity = '1';  // Fade in the modal content
                modalContent.style.transform = 'translateY(0)';  // Reset modal position to fully visible
            }, 10);
        }

        function closeModal() {
            const modal = document.querySelector('.recipe-container');
            const modalContent = document.querySelector('.modal-content');

            modal.style.opacity = '0';
            modal.style.pointerEvents = 'none';
            modalContent.style.opacity = '0';
            modalContent.style.transform = 'translateY(-50px)';

            setTimeout(() => {
                modal.style.display = 'none';
            }, 500);
        }

        function toggleEditMode() {
            const elements = document.querySelectorAll('.editable');
            const isEditing = elements[0].querySelector('input, textarea').style.display == 'block';

            elements.forEach(element => {
                const input = element.querySelector('input, textarea');
                const span = element.querySelector('span');

                if (!isEditing) {
                    span.style.display = 'none';
                    input.style.display = 'block';
                    input.style.opacity = '1';
                } else {
                    input.style.display = 'none';
                    span.style.display = 'inline-block';
                    span.style.opacity = '1';
                }
            });

            const saveButton = document.getElementById('saveButton');
            const editButton = document.getElementById('editButton');
            if (!isEditing) {
                saveButton.style.display = 'inline-block';
                editButton.textContent = 'Cancel';
            } else {
                saveButton.style.display = 'none';
                editButton.textContent = 'Edit';
            }
        }

        window.onclick = function(event) {
            const modal = document.querySelector('.recipe-container');
            if (event.target === modal) {
                closeModal();
            }
        }

        // Auto-expand textarea
        function autoExpandTextarea(event) {
            event.target.style.height = 'auto';
            event.target.style.height = event.target.scrollHeight + 'px';
        }
    </script>
</head>
<body>
    <button onclick="openModal()">Open Recipe Details</button>

    <div class="recipe-container">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <h1>Recipe Details</h1>
            <form:form method="POST" modelAttribute="recipe" action="${contextPath}/saveRecipe">
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
            </form:form>
        </div>
    </div>
</body>
</html>

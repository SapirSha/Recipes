<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add New Recipe</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 20px;
            height: 100%;
            overflow: hidden; /* Prevent body scroll */
        }
        h1 {
            color: #333;
        }

.recipe-container {
    display: flex;
    opacity: 0; /* Start fully hidden */
    pointer-events: none; /* Prevent interaction when hidden */
    transition: opacity 0.5s ease-in-out; /* Smooth fade-in/fade-out */
    
    position: fixed;
    z-index: 1;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.4);
    justify-content: center;
    align-items: flex-start;
    padding: 50px 0 150px;
}


.modal-content {
    background-color: #fefefe;
    padding: 30px;
    border: 1px solid #888;
    width: 90%;
    max-width: 600px;
    max-height: 90%; /* Ensure the modal doesn't go off-screen */
    overflow-y: auto; /* Enable vertical scrolling */
    border-radius: 12px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    position: relative;
    
    /* Hide scrollbar */
    scrollbar-width: none; /* Firefox */
    -ms-overflow-style: none; /* IE/Edge */
    
    transform: translateY(-50px);
    transition: transform 0.5s ease-in-out, opacity 0.5s ease-in-out;
    opacity: 0;
}

.modal-content::-webkit-scrollbar {
    display: none; /* Chrome, Safari */
}

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
        .edit-button .save-btn {
            background-color: #28A745;
            color: white;
        }

        /* Animations */
        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }
        @keyframes slideIn {
            from { transform: translateY(-50px); opacity: 0; }
            to { transform: translateY(0); opacity: 1; }
        }
        @keyframes fadeOut {
            from { opacity: 1; }
            to { opacity: 0; }
        }
        @keyframes slideOut {
            from { transform: translateY(0); opacity: 1; }
            to { transform: translateY(-50px); opacity: 0; }
        }

        .animate-fadeIn {
            animation: fadeIn 0.5s;
        }
        .animate-slideIn {
            animation: slideIn 0.5s;
        }
        .animate-fadeOut {
            animation: fadeOut 0.5s;
        }
        .animate-slideOut {
            animation: slideOut 0.5s;
        }
    </style>
    <script>
    function openModal(modalId) {
        const modal = document.getElementById(modalId);
        const modalContent = modal.querySelector('.modal-content');

        modal.style.display = 'flex';
        
        setTimeout(() => {
            modal.style.opacity = '1'; 
            modal.style.pointerEvents = 'auto'; // Allow interactions
            modalContent.style.opacity = '1';
            modalContent.style.transform = 'translateY(0)';
        }, 10); // Short delay to allow display change
    }


    function closeModal(modalId) {
        const modal = document.getElementById(modalId);
        const modalContent = modal.querySelector('.modal-content');

        modal.style.opacity = '0'; // Fade out
        modal.style.pointerEvents = 'none'; // Disable interaction
        modalContent.style.opacity = '0';
        modalContent.style.transform = 'translateY(-50px)';

        
        setTimeout(() => {
            modal.style.display = 'none';
        }, 500); // Matches transition time
    }



        window.onclick = function(event) {
            if (event.target.classList.contains('recipe-container')) {
                closeModal(event.target.id);
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
    <button onclick="openModal('addRecipeModal')">Add New Recipe</button>

    <!-- Add Recipe Modal -->
    <div id="addRecipeModal" class="recipe-container">
        <div class="modal-content">
            <span class="close" onclick="closeModal('addRecipeModal')">&times;</span>
            <h1>Add New Recipe</h1>
            <form:form method="POST" modelAttribute="recipe" action="${contextPath}/addRecipe">
                <div class="editable">
                    <strong>Name:</strong>
                    <form:input path="name" id="new-recipe-name" class="input-field" />
                </div>
                <div class="editable">
                    <strong>Category:</strong>
                    <form:input path="category" id="new-recipe-category" class="input-field" />
                </div>
                <div class="editable">
                    <strong>Description:</strong>
                    <form:textarea path="description" id="new-recipe-description" class="input-field" oninput="autoExpandTextarea(event)"></form:textarea>
                </div>
                <div class="editable">
                    <strong>Ingredients:</strong>
                    <form:textarea path="ingredients" id="new-recipe-ingredients" class="input-field" oninput="autoExpandTextarea(event)"></form:textarea>
                </div>
                <div class="editable">
                    <strong>Instructions:</strong>
                    <form:textarea path="instructions" id="new-recipe-instructions" class="input-field" oninput="autoExpandTextarea(event)"></form:textarea>
                </div>
                <div class="edit-button">
                    <button type="submit" class="save-btn">Add Recipe</button>
                </div>
            </form:form>
        </div>
    </div>
</body>
</html>


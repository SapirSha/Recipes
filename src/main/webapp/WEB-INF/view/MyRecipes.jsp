<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Recipes</title>
    <link rel="stylesheet" type="text/css" href="resources/css/main.css">
    <script type="text/javascript" src="resources/js/main.js"></script>
</head>
<body>
    <%@ include file="common/header.jsp" %>
    <div class="content">
        <h1>My Recipes Page</h1>
        
        <br>
        <br>
        
        <form method="post">
	        <div class="recipe-button-container">
	            <button type="submit" formaction="create">Create</button>
	            <button type="submit" formaction="show">Show</button>
	            <button type="submit" formaction="edit">Edit</button>
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
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </form>
    </div>
    <%@ include file="common/LoginSignupModal.jsp" %>
</body>
</html>
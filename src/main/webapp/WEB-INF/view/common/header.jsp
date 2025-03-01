<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <link rel="stylesheet" type="text/css" href="resources/css/main.css">
<div class="menu">
<a href="Home">Home</a>
	<c:choose>
        <c:when test="${empty sessionScope.user}">
            <a href="#" id="btnAuthorize">Authorize</a>
        </c:when>
        <c:otherwise>
            <a href="logout">Logout</a>
            <a href="MyRecipes">My Recipes</a>
        </c:otherwise>
    </c:choose>
    <a href="SearchRecipe">Search Recipe</a>
</div>


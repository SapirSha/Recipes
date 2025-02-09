<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="menu">
	<c:choose>
        <c:when test="${empty sessionScope.user}">
            <a href="#" id="btnAuthorize">Authorize</a>
        </c:when>
        <c:otherwise>
            <a href="logout">Logout</a>
            <a href="MyRecipes">My Recipes</a>
        </c:otherwise>
    </c:choose>
    <!-- <a href="#">Search Recipe</a>
    <a href="#">Services</a>
    <a href="#">Contact</a> -->
</div>


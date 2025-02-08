<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
</head>
<body>
    <button onclick="openModal('addRecipeModal')">Add New Recipe</button>
    <jsp:include page="common/AddRecipeModal.jsp" />

</body>
</html>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Recipe Details</title>
</head>
<body>
    <button onclick="openModal()">Open Recipe Details</button>
    <jsp:include page="common/RecipeDetailsModal.jsp" />

</body>
</html>

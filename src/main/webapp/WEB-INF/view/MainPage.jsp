<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <h1>Main Page</h1>
        <P>${helloMessage} </P>
        <p>Welcome to the main page of Recipes!</p>
    </div>
    <%@ include file="common/LoginSignupModal.jsp" %>
</body>
</html>

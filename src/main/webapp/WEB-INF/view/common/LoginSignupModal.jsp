<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Recipes</title>
    <link rel="stylesheet" type="text/css" href="resources/css/main.css">
    <script type="text/javascript" src="resources/js/main.js"></script>
</head>
<body>
<div id="modal" class="modal">
	<%
	String contextPath = request.getContextPath();
	request.setAttribute("contextPath", contextPath);
	%>

    <div class="modal-content">
        <span class="close">&times;</span>
        
        <div class="tab">
            <button id="loginTabBtn" class="active">Login</button>
            <button id="signUpTabBtn">Sign-Up</button>
        </div>
        
        <div id="loginContent" class="tab-content active">
            <h2 style="margin-bottom: 10px;">Login</h2>
            <form:form action="${contextPath}/processLogin" method="POST" modelAttribute="LogInUser">
			    <label for="loginUsername">Username:</label>
			    <input type="text" id="loginUsername" name="username" required>
			    <label for="loginPassword">Password:</label>
			    <input type="password" id="loginPassword" name="password" required>
			    <input type="hidden" name="returnUrl" value="${returnUrl}">
			    <p class="error-message">${loginErrorMessage}</p>
			    <button type="submit">Login</button>
			</form:form>

        </div>
        
        <div id="signUpContent" class="tab-content">
            <div id="signUpForm">
                <h2 style="margin-bottom: 10px;">Sign-Up</h2>
			<form:form action="processSignup" method="post" modelAttribute="SignUpUser">
			    <label for="signUpUsername">Username:</label>
			    <input type="text" id="signUpUsername" name="username" required>
			    <label for="signUpPassword">Password:</label>
			    <input type="password" id="signUpPassword" name="password" required>
			    <input type="hidden" name="returnUrl" value="${returnUrl}">
			    <p class="error-message">${signupErrorMessage}</p>
			    <button type="submit">Sign Up</button>
			</form:form>

            </div>
            <div class="success-container" id="signUpSuccess">
                <div class="checkmark"></div>
                <h1>Success!</h1>
                <p>Your account has been successfully created. Please try to login!</p>
            </div>
        </div>
    </div>
</div>
</body>

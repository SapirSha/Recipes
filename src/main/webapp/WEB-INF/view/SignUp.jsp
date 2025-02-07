<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign Up</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .signup-container {
            background-color: #fff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 350px;
            text-align: center;
        }

        h2 {
            color: #333;
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 20px;
            text-align: left;
        }

        label {
            display: block;
            margin-bottom: 5px;
            color: #555;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        button {
            background-color: #007bff;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 1em;
            width: 100%;
            margin-top: 10px;
        }

        button:hover {
            background-color: #0056b3;
        }

        .error-message {
            color: red;
            margin-top: 5px;
            text-align: center;
            display: block;
            min-height: 30px;
        }

        .server-error-container {
            min-height: 30px;
            margin-bottom: 10px;
        }

        .server-error-message {
            color: #dc3545;
            font-size: 0.9em;
            text-align: center;
            padding: 5px;
            border: 1px solid #f8d7da;
            background-color: #f8d7da;
            border-radius: 4px;
        }

        .server-error-container:empty {
            display: none;
        }

        .hidden {
            display: none;
        }

       .login-link {  /* Added this class */
            margin-top: 20px;
            font-size: 0.9em;
            color: #555;
        }

        .login-link a {  /* Added this class */
            color: #007bff;
            text-decoration: none;
        }

        .login-link a:hover {  /* Added this class */
            text-decoration: underline;
        }

    </style>
</head>
<body>

    <div class="signup-container">
        <h1>Sign Up</h1>

        <div class="server-error-container">
            <% if (request.getAttribute("signupErrorMessage") != "") { %>
                <p class="server-error-message">
                    <%= request.getAttribute("signupErrorMessage") %>
                </p>
            <% } %>
        </div>

        <form:form action="processSignup" method="post" modelAttribute="user">
             <div class="form-group">
                <label for="username">Username:</label>
                <form:input path="username" id="username" />
            </div>

            <div class="form-group">
                <label for="password">Password:</label>
                <form:password path="password" id="password" />
            </div>

            <button type="submit">Sign Up</button>

        </form:form>

        <div class="login-link">
             Already have an account? <a href="LogIn">Log In</a>
        </div>
    </div>

</body>
</html>
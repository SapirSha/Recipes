<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login Form</title>
    <style>
        .error-message {
            color: red;
            display: block;       /* Important: Reserve space */
            min-height: 20px;    /* Adjust height as needed */
        }
    </style>
</head>
<body>
    <form:form action="processLogin" method="POST" modelAttribute="user">
        User Name: <form:input type="text" path="username" />
        <br><br>
        Password: <form:input type="password" path="password" />
        <br><br>

        <p class="error-message">${loginErrorMessage}</p>

        <input type="submit" value="Submit" />
    </form:form>
</body>
</html>
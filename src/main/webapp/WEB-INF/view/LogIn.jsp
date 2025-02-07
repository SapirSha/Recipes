<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login Form</title>
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

        .login-container {
            background-color: #fff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 350px;
            text-align: center;
        }

        h1 {
            color: #333;
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 20px;
            text-align: left; /* Align labels to the left */
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
            box-sizing: border-box; /* Important for width */
        }

        .error-message {
            color: red;
            display: block;
            min-height: 30px;
            margin-top: 10px;
            text-align: center; /* Center the error message */
        }

        button,
        input[type="submit"] {
            background-color: #007bff;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 1em;
            width: 100%; /* Make the button fill the width */
            margin-top: 10px; /* Add some spacing above the button */
        }

        button:hover,
        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        .signup-link {
            margin-top: 20px;
            font-size: 0.9em;
            color: #555;
        }

        .signup-link a {
            color: #007bff;
            text-decoration: none;
        }

        .signup-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <div class="login-container">
        <h1>Login</h1>

        <form:form action="processLogin" method="POST" modelAttribute="user">
            <div class="form-group">
                <label for="username">User Name:</label>
                <form:input type="text" path="username" id="username"/>
            </div>

            <div class="form-group">
                <label for="password">Password:</label>
                <form:input type="password" path="password" id="password"/>
            </div>

            <p class="error-message">${loginErrorMessage}</p>

            <input type="submit" value="Log In" />
        </form:form>

        <div class="signup-link">
            Don't have an account? <a href="SignUp">Sign Up</a>
        </div>
    </div>

</body>
</html>
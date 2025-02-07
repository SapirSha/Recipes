<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign Up Successful</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f8ff;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .success-container {
            background-color: #ffffff;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 400px;
        }

        h1 {
            color: #28a745; 
            margin-bottom: 20px;
            font-size: 2.5em;
        }

        p {
            color: #555;
            font-size: 1.2em;
            margin-bottom: 30px;
        }

        .login-button {
            background-color: #007bff;
            color: #ffffff;
            padding: 15px 30px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1.1em;
            text-decoration: none;
            display: inline-block;
        }

        .login-button:hover {
            background-color: #0056b3;
        }

        .checkmark {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            background: #28a745;
            margin: 0 auto 30px;
            position: relative;
            overflow: hidden;
        }

        .checkmark:after {
            content: '';
            position: absolute;
            top: 50%;
            left: 50%;
            width: 20px;
            height: 40px;
            border: 5px solid white;
            border-top: none;
            border-left: none;
            transform: translate(-50%, -70%) rotate(45deg);
        }
    </style>
</head>
<body>

    <div class="success-container">
        <div class="checkmark"></div>
        <h1>Success!</h1>
        <p>Your account has been successfully created. Welcome!</p>
        <a href="LogIn" class="login-button">Go to Login</a>
    </div>

</body>
</html>
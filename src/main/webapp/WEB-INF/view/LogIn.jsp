<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>Login Form</title>
</head>
<body>
	<form:form action="processLogin" method="POST" modelAttribute="user">
	User Name: <form:input type = "text" path="username" />
	<br>
	<br>
	Password: <form:input type="password" path="password" />
	<br>
	<br>
	
	<p style="color:red"> ${message} </p>
	<br>

	<input type="submit" value="Submit" />
	</form:form>
</body>
</html>

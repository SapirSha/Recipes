<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- The Modal -->
<div id="modal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>

        <div class="tab">
            <button id="loginTabBtn" class="active">Login</button>
            <button id="signUpTabBtn">Sign-Up</button>
        </div>
        
        <div id="loginContent" class="tab-content active">
            <h2>Login</h2>
            <form:form action="processLogin" method="POST" modelAttribute="LogInUser">
                <label for="loginUsername">Username:</label>
                <input type="text" id="loginUsername" name="username" required>
                <label for="loginPassword">Password:</label>
                <input type="password" id="loginPassword" name="password" required>
                <p class="error-message">${loginErrorMessage}</p>
                <button type="submit">Login</button>
            </form:form>
        </div>
        
        <div id="signUpContent" class="tab-content">
            <div id="signUpForm">
                <h2>Sign-Up</h2>
                <form:form action="processSignup" method="post" modelAttribute="SignUpUser">
                    <label for="signUpUsername">Username:</label>
                    <input type="text" id="signUpUsername" name="username" required>
                    <label for="signUpPassword">Password:</label>
                    <input type="password" id="signUpPassword" name="password" required>
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

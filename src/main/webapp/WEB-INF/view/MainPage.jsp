<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Recipes</title>
    <style>
    
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }
        
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
        }
        
        .menu {
            width: 220px;
            background-color: #333;
            color: #fff;
            height: 100vh;
            position: fixed;
            overflow: auto;
            padding-top: 20px;
            text-align: center;
        }

        .menu a {
            display: block;
            color: #fff;
            padding: 15px 0;
            text-decoration: none;
            transition: background-color 0.3s ease, color 0.3s ease;
        }
        
        .menu a:hover {
            background-color: #575757;
        }

        .menu .image-button {
            margin-bottom: 20px;
        }
        
        .menu .image-button img {
            width: 80px;
            height: 80px;
            cursor: pointer;
            transition: transform 0.3s ease, filter 0.3s ease;
        }
        
        .menu .image-button img:hover {
            transform: scale(1.1);
            filter: brightness(1.2);
        }

        .content {
            margin-left: 220px;
            padding: 20px;
            flex: 1;
        }

        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.6);
        }
        
        .modal.show {
            animation: fadeInModal 0.4s ease-out forwards;
        }
        .modal.hide {
            animation: fadeOutModal 0.4s ease-out forwards;
        }
        .modal-content {
            background-color: #fefefe;
            margin: 5% auto;
            padding: 0;
            border-radius: 5px;
            width: 400px;
            overflow: hidden;
            position: relative;
            animation: slideDown 0.5s ease-out forwards;
        }
        .modal-content.hide {
            animation: slideUp 0.4s ease-in forwards;
        }

        .close {
            color: #aaa;
            position: absolute;
            right: 15px;
            top: 10px;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
            transition: color 0.3s ease;
            z-index: 1;
        }
        .close:hover,
        .close:focus {
            color: black;
        }

        .tab {
            display: flex;
        }
        .tab button {
            flex: 1;
            padding: 14px 0;
            border: none;
            background-color: #f1f1f1;
            cursor: pointer;
            font-size: 17px;
            transition: background-color 0.3s, color 0.3s;
        }
        .tab button:hover {
            background-color: #ddd;
        }
        .tab button.active {
            background-color: #fff;
            font-weight: bold;
        }

        .tab-content {
            display: none;
            padding: 20px;
            animation: fadeInContent 0.3s ease-in forwards;
        }
        .tab-content.active {
            display: block;
        }

        .modal-content input[type=text],
        .modal-content input[type=password],
        .modal-content input[type=email] {
            width: 100%;
            padding: 12px 15px;
            margin: 8px 0;
            border: 1px solid #ccc;
            border-radius: 3px;
        }

        .modal-content button[type=submit] {
            background-color: #333;
            color: white;
            padding: 12px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            width: 100%;
            margin-top: 10px;
            font-size: 16px;
            transition: background-color 0.3s ease, box-shadow 0.3s ease;
        }
        
        .modal-content button[type=submit]:hover {
            background-color: #575757;
            box-shadow: 0 0 10px rgba(0,0,0,0.3);
        }
        
        .error-message {
            color: red;
            display: block;
            min-height: 30px;
            margin-top: 10px;
            text-align: center; /* Center the error message */
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
        
        .success-container {
            display:none;
            text-align: center;
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

        @keyframes fadeInModal {
            from { opacity: 0; }
            to { opacity: 1; }
        }
        
        @keyframes fadeOutModal {
            from { opacity: 1; }
            to { opacity: 0; }
        }
        
        @keyframes slideDown {
            from { transform: translateY(-50px); opacity: 0; }
            to { transform: translateY(0); opacity: 1; }
        }
        
        @keyframes slideUp {
            from { transform: translateY(0); opacity: 1; }
            to { transform: translateY(-50px); opacity: 0; }
        }
        
        @keyframes fadeInContent {
            from { opacity: 0; }
            to { opacity: 1; }
        }
        
    </style>
    <script>
    window.onload = function() {
        const urlParams = new URLSearchParams(window.location.search);
        const logInError = urlParams.get('LogInError');
        const signUpError = urlParams.get('SignUpError');
        const signUpSuccess= urlParams.get('SignUpSuccess');
        
        if(signUpError || logInError || signUpSuccess){
            var modal = document.getElementById("modal");
            var modalContent = document.querySelector(".modal-content");
            var closeBtn = document.getElementsByClassName("close")[0];

            var loginTabBtn = document.getElementById("loginTabBtn");
            var signUpTabBtn = document.getElementById("signUpTabBtn");
            
            var loginContent = document.getElementById("loginContent");
            var signUpContent = document.getElementById("signUpContent");
            
            var signUpForm = document.getElementById("signUpForm");
            var signUpSuccessMsg = document.getElementById("signUpSuccess");

            var btnAuthorize = document.getElementById("btnAuthorize");
        }
        
        if(signUpError || signUpSuccess){
        	//console.log(request.getAttribute("signupErrorMessage"));
        	console.log(signUpError);
        	console.log(signUpSuccess);
        	
            if(signUpSuccess){
            	signUpForm.style.display = 'none';
            	signUpSuccessMsg.style.display = 'block';
            }
            
        	// Make the SignUp modal appear
            event.preventDefault();
            modal.style.display = "block";
            modal.classList.add("show");
            modal.classList.remove("hide");
            modalContent.classList.remove("hide");
            loginContent.classList.remove('active');
            signUpContent.classList.add('active');
            loginTabBtn.classList.remove('active');
            signUpTabBtn.classList.add('active');
            

        }

        
        if (logInError){
            // Make The Login Modal appear
            
            event.preventDefault();
            modal.style.display = "block";
            modal.classList.add("show");
            modal.classList.remove("hide");
            modalContent.classList.remove("hide");
            
            loginContent.classList.add('active');
            signUpContent.classList.remove('active');
            loginTabBtn.classList.add('active');
            signUpTabBtn.classList.remove('active');
        }
        
    };
        document.addEventListener("DOMContentLoaded", function() {
            var modal = document.getElementById("modal");
            var modalContent = document.querySelector(".modal-content");
            var closeBtn = document.getElementsByClassName("close")[0];

            var loginTabBtn = document.getElementById("loginTabBtn");
            var signUpTabBtn = document.getElementById("signUpTabBtn");
            
            var loginContent = document.getElementById("loginContent");
            var signUpContent = document.getElementById("signUpContent");
            
            var btnAuthorize = document.getElementById("btnAuthorize");

            // OPEN MODAL
            btnAuthorize.onclick = function(event) {
                event.preventDefault();
                modal.style.display = "block";
                modal.classList.add("show");
                modal.classList.remove("hide");
                modalContent.classList.remove("hide");
                openTab('login');
            };
            
            // Close MODAL
            function closeModal() {
                modalContent.classList.add("hide");
                modal.classList.add("hide");
                setTimeout(function() {
                    modal.style.display = "none";
                    modal.classList.remove("show", "hide");
                    modalContent.classList.remove("hide");
                }, 400); 
            }
            // Close modal when 'x' is clicked
            closeBtn.onclick = function() {
                closeModal();
            }
            
            // Close modal when clicking outside of modal content
            window.onclick = function(event) {
                if (event.target == modal) {
                    closeModal();
                }
            }
            // Function to switch between login and signup
            function openTab(tabName) {
                if(tabName === 'login') {
                    loginContent.classList.add('active');
                    signUpContent.classList.remove('active');
                    loginTabBtn.classList.add('active');
                    signUpTabBtn.classList.remove('active');
                } else {
                    loginContent.classList.remove('active');
                    signUpContent.classList.add('active');
                    loginTabBtn.classList.remove('active');
                    signUpTabBtn.classList.add('active');
                }
            }
            // Add event listeners to tab buttons
            loginTabBtn.onclick = function() { openTab('login'); }
            signUpTabBtn.onclick = function() { openTab('signup'); }
        });
    </script>
</head>
<body>
    <div class="menu">
        <!-- Image Button -->
        <a href="#"  id="btnAuthorize" >Authorize</a>
        <a href="#">My Recipes</a>
        <a href="#">Search Recipe</a>
        <a href="#">Services</a>
        <a href="#">Contact</a>
    </div>
    <div class="content">
        <h1>Main Page</h1>
        <p>AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA</p>
    </div>

    <!-- The Modal -->
    <div id="modal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <!-- Tab Buttons -->
            <div class="tab">
                <button id="loginTabBtn" class="active">Login</button>
                <button id="signUpTabBtn">Sign-Up</button>
            </div>
            
            <!-- Login Content -->
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
            
            <!-- Sign-Up Content -->
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
</body>
</html>

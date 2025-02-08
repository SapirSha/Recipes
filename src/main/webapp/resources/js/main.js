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
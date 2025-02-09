        window.addEventListener('load', function() {
        	const urlParams = new URLSearchParams(window.location.search);

        	const recipeCreated = urlParams.get('RecipeCreated');

        	if (recipeCreated == 'false') {
        		console.log("HERE");
                const modal = document.getElementById('addRecipeModal');
                const modalContent = modal.querySelector('.modal-content');

                modal.style.display = 'flex';
                
                setTimeout(() => {
                    modal.style.opacity = '1'; 
                    modal.style.pointerEvents = 'auto';
                    modalContent.style.opacity = '1';
                    modalContent.style.transform = 'translateY(0)';
                }, 10);
        	}
        });
        
        function openModal(modalId) {
            const modal = document.getElementById(modalId);
            const modalContent = modal.querySelector('.modal-content');

            modal.style.display = 'flex';
            
            setTimeout(() => {
                modal.style.opacity = '1'; 
                modal.style.pointerEvents = 'auto'; // Allow interactions
                modalContent.style.opacity = '1';
                modalContent.style.transform = 'translateY(0)';
            }, 10);
        }
        
//<button onclick="openModal('addRecipeModal')">Add New Recipe</button>
        function closeModal(modalId) {
            const modal = document.getElementById(modalId);
            const modalContent = modal.querySelector('.modal-content');

            modal.style.opacity = '0';
            modal.style.pointerEvents = 'none'; 
            modalContent.style.opacity = '0';
            modalContent.style.transform = 'translateY(-50px)';

            setTimeout(() => {
                modal.style.display = 'none';
            }, 500); 
        }

        window.onclick = function(event) {
            if (event.target.classList.contains('recipe-container')) {
                closeModal(event.target.id);
            }
        }

        // Auto-expand textarea
        function autoExpandTextarea(event) {
            event.target.style.height = 'auto';
            event.target.style.height = event.target.scrollHeight + 'px';
        }
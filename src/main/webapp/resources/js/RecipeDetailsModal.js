        window.addEventListener('load', function() {
            const elements = document.querySelectorAll('.editable');
            elements.forEach(element => {
                const input = element.querySelector('input, textarea');
                const span = element.querySelector('span');
                
                input.style.opacity = '0';
                input.style.display = 'none';
                span.style.display = 'inline-block';
                span.style.opacity = '1';
            });
        });
        
        function openModal() {
            const modal = document.querySelector('.recipe-container');
            const modalContent = document.querySelector('.modal-content');
            
            modal.style.display = 'flex';
            
            setTimeout(() => {
                modal.style.opacity = '1';
                modal.style.pointerEvents = 'auto';
                modalContent.style.opacity = '1';
                modalContent.style.transform = 'translateY(0)';
            }, 10);
        }

        function closeModal() {
            const modal = document.querySelector('.recipe-container');
            const modalContent = document.querySelector('.modal-content');

            modal.style.opacity = '0';
            modal.style.pointerEvents = 'none';
            modalContent.style.opacity = '0';
            modalContent.style.transform = 'translateY(-50px)';

            setTimeout(() => {
                modal.style.display = 'none';
            }, 500);
        }

        function toggleEditMode() {
            const elements = document.querySelectorAll('.editable');
            const isEditing = elements[0].querySelector('input, textarea').style.display === 'block';

            elements.forEach(element => {
                const input = element.querySelector('input, textarea');
                const span = element.querySelector('span');

                if (!isEditing) {
                    span.style.display = 'none';
                    input.style.display = 'block';
                    input.style.opacity = '1';
                } else {
                    input.style.display = 'none';
                    span.style.display = 'inline-block';
                    span.style.opacity = '1';
                }
            });

            const saveButton = document.getElementById('saveButton');
            const editButton = document.getElementById('editButton');
            if (!isEditing) {
                saveButton.style.display = 'inline-block';
                editButton.textContent = 'Cancel';
            } else {
                saveButton.style.display = 'none';
                editButton.textContent = 'Edit';
            }
        }

        window.onclick = function(event) {
            const modal = document.querySelector('.recipe-container');
            if (event.target === modal) {
                closeModal();
            }
        }

        function autoExpandTextarea(event) {
            event.target.style.height = 'auto';
            event.target.style.height = event.target.scrollHeight + 'px';
        }
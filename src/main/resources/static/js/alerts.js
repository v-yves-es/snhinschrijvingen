/**
 * Alert functionality
 * Handles dismissible alerts
 */

(function() {
    'use strict';

    document.addEventListener('DOMContentLoaded', function() {
        // Handle alert close buttons
        const closeButtons = document.querySelectorAll('.alert-dismissible .close');
        
        closeButtons.forEach(button => {
            button.addEventListener('click', function() {
                const alert = this.closest('.alert');
                if (alert) {
                    // Fade out animation
                    alert.classList.remove('show');
                    
                    // Remove from DOM after animation
                    setTimeout(() => {
                        alert.remove();
                    }, 150);
                }
            });
        });
    });
})();

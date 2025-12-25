/**
 * Form Validation Script
 * Provides client-side validation with visual feedback
 */

(function() {
    'use strict';

    // Validation rules
    const validators = {
        required: (value) => {
            return value && value.trim() !== '';
        },
        email: (value) => {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return emailRegex.test(value);
        },
        phone: (value) => {
            // Belgian phone format: +32XXXXXXXXX or 0XXXXXXXXX
            const phoneRegex = /^(\+32|0)[1-9]\d{7,8}$/;
            return phoneRegex.test(value.replace(/\s/g, ''));
        },
        rijksregisternummer: (value) => {
            // Format: YYMMDD-XXX-XX
            const rrRegex = /^\d{6}-\d{3}-\d{2}$/;
            return rrRegex.test(value);
        },
        minLength: (value, length) => {
            return value.length >= length;
        },
        maxLength: (value, length) => {
            return value.length <= length;
        },
        postalCode: (value) => {
            // Belgian postal code: 4 digits
            return /^\d{4}$/.test(value);
        }
    };

    // Validation messages in Dutch
    const messages = {
        required: 'Dit veld is verplicht',
        email: 'Voer een geldig e-mailadres in',
        phone: 'Voer een geldig telefoonnummer in',
        rijksregisternummer: 'Voer een geldig rijksregisternummer in (YYMMDD-XXX-XX)',
        postalCode: 'Voer een geldige postcode in (4 cijfers)',
        minLength: 'Te kort, minimaal {length} karakters',
        maxLength: 'Te lang, maximaal {length} karakters'
    };

    /**
     * Validate a single field
     */
    function validateField(field) {
        const value = field.value;
        const fieldName = field.name || field.id;
        let isValid = true;
        let errorMessage = '';

        // Check if field is required
        if (field.hasAttribute('required') || field.dataset.required) {
            if (!validators.required(value)) {
                isValid = false;
                errorMessage = messages.required;
            }
        }

        // If field has value or is required, check other validations
        if (value && isValid) {
            // Email validation
            if (field.type === 'email' || field.dataset.validate === 'email') {
                if (!validators.email(value)) {
                    isValid = false;
                    errorMessage = messages.email;
                }
            }

            // Phone validation
            if (field.type === 'tel' || field.dataset.validate === 'phone') {
                if (!validators.phone(value)) {
                    isValid = false;
                    errorMessage = messages.phone;
                }
            }

            // Rijksregisternummer validation
            if (field.dataset.validate === 'rijksregisternummer') {
                if (!validators.rijksregisternummer(value)) {
                    isValid = false;
                    errorMessage = messages.rijksregisternummer;
                }
            }

            // Postal code validation
            if (field.dataset.validate === 'postalCode') {
                if (!validators.postalCode(value)) {
                    isValid = false;
                    errorMessage = messages.postalCode;
                }
            }

            // Min length validation
            if (field.dataset.minLength) {
                const minLength = parseInt(field.dataset.minLength);
                if (!validators.minLength(value, minLength)) {
                    isValid = false;
                    errorMessage = messages.minLength.replace('{length}', minLength);
                }
            }

            // Max length validation
            if (field.dataset.maxLength) {
                const maxLength = parseInt(field.dataset.maxLength);
                if (!validators.maxLength(value, maxLength)) {
                    isValid = false;
                    errorMessage = messages.maxLength.replace('{length}', maxLength);
                }
            }
        }

        // Apply visual feedback
        updateFieldValidation(field, isValid, errorMessage);
        
        return isValid;
    }

    /**
     * Update field visual validation state
     */
    function updateFieldValidation(field, isValid, errorMessage) {
        // Remove existing validation classes
        field.classList.remove('is-valid', 'is-invalid');

        // Don't show validation on empty non-required fields that haven't been touched
        if (!field.value && !field.hasAttribute('required') && !field.dataset.touched) {
            return;
        }

        // Add appropriate validation class
        if (isValid) {
            field.classList.add('is-valid');
        } else {
            field.classList.add('is-invalid');
        }

        // Update or create error message
        let feedbackElement = field.parentElement.querySelector('.invalid-feedback');
        
        if (!isValid) {
            if (!feedbackElement) {
                feedbackElement = document.createElement('div');
                feedbackElement.className = 'invalid-feedback';
                field.parentElement.appendChild(feedbackElement);
            }
            feedbackElement.textContent = errorMessage;
        }
    }

    /**
     * Initialize validation for a form
     */
    function initializeForm(form) {
        const fields = form.querySelectorAll('input:not([type="hidden"]):not([type="submit"]), select, textarea');

        fields.forEach(field => {
            // Mark field as touched on blur
            field.addEventListener('blur', function() {
                field.dataset.touched = 'true';
                validateField(field);
            });

            // Real-time validation on input (after field has been touched)
            field.addEventListener('input', function() {
                if (field.dataset.touched === 'true') {
                    validateField(field);
                }
            });

            // Special handling for required radio buttons and checkboxes
            if ((field.type === 'radio' || field.type === 'checkbox') && field.hasAttribute('required')) {
                field.addEventListener('change', function() {
                    validateField(field);
                });
            }
        });

        // Form submission validation
        form.addEventListener('submit', function(event) {
            let formIsValid = true;

            fields.forEach(field => {
                field.dataset.touched = 'true';
                if (!validateField(field)) {
                    formIsValid = false;
                }
            });

            if (!formIsValid) {
                event.preventDefault();
                
                // Scroll to first invalid field
                const firstInvalid = form.querySelector('.is-invalid');
                if (firstInvalid) {
                    firstInvalid.scrollIntoView({ behavior: 'smooth', block: 'center' });
                    firstInvalid.focus();
                }
            }
        });
    }

    /**
     * Initialize validation on page load
     */
    document.addEventListener('DOMContentLoaded', function() {
        const forms = document.querySelectorAll('form[data-validate="true"], form.needs-validation');
        forms.forEach(form => initializeForm(form));
        
        // Also initialize forms without specific class/attribute (auto-detect)
        const allForms = document.querySelectorAll('form');
        allForms.forEach(form => {
            if (!form.dataset.validate && !form.classList.contains('needs-validation')) {
                // Check if form has any required fields
                const hasRequiredFields = form.querySelector('[required]');
                if (hasRequiredFields) {
                    initializeForm(form);
                }
            }
        });
    });

})();

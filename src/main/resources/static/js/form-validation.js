/**
 * Form Validation Script
 * Provides real-time client-side validation with visual feedback
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
    function validateField(field, showValidState = true) {
        const value = field.value;
        const fieldName = field.name || field.id;
        let isValid = true;
        let errorMessage = '';

        // Special handling for radio buttons
        if (field.type === 'radio') {
            if (field.hasAttribute('required')) {
                const radioGroup = document.querySelectorAll(`input[name="${field.name}"]`);
                const isChecked = Array.from(radioGroup).some(radio => radio.checked);
                if (!isChecked) {
                    isValid = false;
                    errorMessage = 'Maak een keuze';
                }
            }
            // Apply visual feedback for the entire radio group
            updateRadioGroupValidation(field, isValid, errorMessage, showValidState);
            return isValid;
        }

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
        updateFieldValidation(field, isValid, errorMessage, showValidState);
        
        return isValid;
    }

    /**
     * Check if entire form is valid
     */
    function isFormValid(form) {
        const fields = form.querySelectorAll('input:not([type="hidden"]):not([type="submit"]):not([type="button"]), select, textarea');
        let allValid = true;
        
        // Track which radio groups we've already checked
        const checkedRadioGroups = new Set();

        fields.forEach(field => {
            // Skip disabled fields
            if (field.disabled) {
                return;
            }
            
            // Skip fields that are in hidden containers
            if (field.offsetParent === null && field.type !== 'hidden') {
                return;
            }

            // For radio buttons, check if any in the group is checked
            if (field.type === 'radio') {
                // Only check each radio group once
                if (checkedRadioGroups.has(field.name)) {
                    return;
                }
                checkedRadioGroups.add(field.name);
                
                if (field.hasAttribute('required')) {
                    const radioGroup = form.querySelectorAll(`input[name="${field.name}"]`);
                    const isChecked = Array.from(radioGroup).some(radio => radio.checked);
                    if (!isChecked) {
                        allValid = false;
                    }
                }
                return; // Skip further checks for radio (handled above)
            }

            // Check if field is required and empty
            if (field.hasAttribute('required') || field.dataset.required) {
                if (!field.value || field.value.trim() === '') {
                    allValid = false;
                    return;
                }
            }

            // Check if field has is-invalid class (only if it has been touched or has a value)
            if (field.classList.contains('is-invalid') && (field.dataset.touched === 'true' || field.value)) {
                allValid = false;
            }
        });

        return allValid;
    }

    /**
     * Update submit button state based on form validity
     */
    function updateSubmitButton(form) {
        const submitButton = form.querySelector('button[type="submit"]');
        if (!submitButton) return;

        if (isFormValid(form)) {
            submitButton.disabled = false;
            submitButton.classList.remove('btn--disabled');
            submitButton.style.opacity = '1';
            submitButton.style.cursor = 'pointer';
        } else {
            submitButton.disabled = true;
            submitButton.classList.add('btn--disabled');
            submitButton.style.opacity = '0.6';
            submitButton.style.cursor = 'not-allowed';
        }
    }

    /**
     * Update radio group validation state
     */
    function updateRadioGroupValidation(field, isValid, errorMessage, showValidState = true) {
        const radioGroup = document.querySelectorAll(`input[name="${field.name}"]`);
        
        // Remove validation classes from all radio buttons in group
        radioGroup.forEach(radio => {
            radio.classList.remove('is-valid', 'is-invalid');
        });

        // Find or create feedback element (after the last radio button container)
        const firstRadio = radioGroup[0];
        let container = firstRadio.closest('.position-relative, .form-group, .mb-3');
        if (!container) {
            container = firstRadio.parentElement.parentElement;
        }
        
        // For form-radio-group, the container should be the parent of form-radio-group
        const radioGroupContainer = firstRadio.closest('.form-radio-group');
        if (radioGroupContainer) {
            container = radioGroupContainer.parentElement;
        }
        
        // Special handling for dynamically added "program" radio buttons
        let feedbackElement = container.querySelector('.invalid-feedback');
        if (field.name === 'program') {
            feedbackElement = document.getElementById('programsListFeedback');
            if (!feedbackElement) {
                feedbackElement = container.querySelector('.invalid-feedback');
            }
        }
        
        if (!isValid) {
            // Add invalid class to all radios in group
            radioGroup.forEach(radio => {
                radio.classList.add('is-invalid');
            });
            
            if (!feedbackElement) {
                feedbackElement = document.createElement('div');
                feedbackElement.className = 'invalid-feedback';
                feedbackElement.style.display = 'block';
                feedbackElement.style.marginTop = '0.5rem';
                container.appendChild(feedbackElement);
            }
            feedbackElement.textContent = errorMessage;
            feedbackElement.style.display = 'block';
        } else {
            if (showValidState) {
                radioGroup.forEach(radio => {
                    radio.classList.add('is-valid');
                });
            }
            if (feedbackElement) {
                feedbackElement.style.display = 'none';
            }
        }
    }

    /**
     * Update field visual validation state
     */
    function updateFieldValidation(field, isValid, errorMessage, showValidState = true) {
        // Remove existing validation classes
        field.classList.remove('is-valid', 'is-invalid');
        
        // For Select2, also update the container
        let select2Container = null;
        if (field.classList.contains('select2-hidden-accessible')) {
            select2Container = field.nextElementSibling;
            if (select2Container && select2Container.classList.contains('select2')) {
                select2Container.classList.remove('is-valid', 'is-invalid');
            }
        }

        // Update or create error message
        let feedbackElement = field.parentElement.querySelector('.invalid-feedback');
        
        // Show error state immediately, valid state only after interaction
        if (!isValid) {
            field.classList.add('is-invalid');
            if (select2Container) {
                select2Container.classList.add('is-invalid');
            }
            
            if (!feedbackElement) {
                feedbackElement = document.createElement('div');
                feedbackElement.className = 'invalid-feedback';
                feedbackElement.style.display = 'block';
                field.parentElement.appendChild(feedbackElement);
            }
            feedbackElement.textContent = errorMessage;
            feedbackElement.style.display = 'block';
        } else {
            // Valid state - show green if value exists and showValidState is true
            if (showValidState && field.value) {
                field.classList.add('is-valid');
                if (select2Container) {
                    select2Container.classList.add('is-valid');
                }
            }
            
            // Always hide error message when valid
            if (feedbackElement) {
                feedbackElement.style.display = 'none';
            }
        }
    }

    /**
     * Initialize validation for a form
     */
    function initializeForm(form) {
        const fields = form.querySelectorAll('input:not([type="hidden"]):not([type="submit"]):not([type="button"]), select, textarea');

        fields.forEach(field => {
            // Real-time validation on input (only after first interaction)
            field.addEventListener('input', function() {
                if (field.dataset.touched === 'true') {
                    validateField(field, true);
                }
            });

            // Validation on blur - mark as touched
            field.addEventListener('blur', function() {
                field.dataset.touched = 'true';
                validateField(field, true);
            });

            // For Select2 dropdowns, listen to change event
            if (field.classList.contains('select2-hidden-accessible')) {
                if (typeof jQuery !== 'undefined') {
                    jQuery(field).on('select2:select select2:unselect change', function() {
                        field.dataset.touched = 'true';
                        validateField(field, true);
                    });
                }
            }

            // Special handling for required radio buttons and checkboxes
            if (field.type === 'radio' || field.type === 'checkbox') {
                field.addEventListener('change', function() {
                    // Mark all radios in the group as touched
                    if (field.type === 'radio') {
                        const radioGroup = form.querySelectorAll(`input[name="${field.name}"]`);
                        radioGroup.forEach(radio => {
                            radio.dataset.touched = 'true';
                        });
                    } else {
                        field.dataset.touched = 'true';
                    }
                    validateField(field, true);
                });
            }
        });

        // Form submission validation
        form.addEventListener('submit', function(event) {
            let formIsValid = true;
            const validatedRadioGroups = new Set();

            fields.forEach(field => {
                field.dataset.touched = 'true';
                
                // For radio buttons, only validate once per group
                if (field.type === 'radio') {
                    if (validatedRadioGroups.has(field.name)) {
                        return; // Skip - already validated this group
                    }
                    validatedRadioGroups.add(field.name);
                }
                
                if (!validateField(field, true)) {
                    formIsValid = false;
                }
            });
            
            // Also check for dynamically added radio groups (like study program)
            const dynamicRadioGroups = form.querySelectorAll('input[type="radio"][name="program"]');
            if (dynamicRadioGroups.length > 0 && !validatedRadioGroups.has('program')) {
                dynamicRadioGroups.forEach(radio => radio.dataset.touched = 'true');
                if (!validateField(dynamicRadioGroups[0], true)) {
                    formIsValid = false;
                }
            }

            if (!formIsValid) {
                event.preventDefault();
                event.stopPropagation();
                
                // Scroll to first invalid field
                const firstInvalid = form.querySelector('.is-invalid');
                if (firstInvalid) {
                    firstInvalid.scrollIntoView({ behavior: 'smooth', block: 'center' });
                    
                    // Focus on the field
                    if (firstInvalid.classList.contains('select2-hidden-accessible')) {
                        // For Select2, open the dropdown
                        if (typeof jQuery !== 'undefined') {
                            jQuery(firstInvalid).select2('open');
                        }
                    } else {
                        firstInvalid.focus();
                    }
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

    // Expose updateSubmitButton globally for use in other scripts (deprecated - button is always enabled now)
    window.updateFormValidation = function(form) {
        // No longer needed, but kept for compatibility
    };
    
    // Expose Select2 validation function globally
    window.validateSelect2Field = function(field) {
        if (field && typeof validateField === 'function') {
            validateField(field, true);
        }
    };

})();

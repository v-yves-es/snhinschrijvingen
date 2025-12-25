/**
 * Flatpickr initialization - Dutch localization
 */
document.addEventListener('DOMContentLoaded', function() {
    const datepickerInputs = document.querySelectorAll('[data-toggle="datepicker"]');
    
    datepickerInputs.forEach(input => {
        flatpickr(input, {
            locale: "nl",
            dateFormat: "d/m/Y",
            allowInput: true,
            altInput: true,
            altFormat: "d/m/Y"
        });
    });
});

/**
 * Select2 Initialization
 * Automatically converts all select elements with .select2 class to Select2 dropdowns
 */
document.addEventListener('DOMContentLoaded', function() {
    // Initialize Select2 on all select elements with .select2 class
    if (typeof jQuery !== 'undefined' && jQuery.fn.select2) {
        jQuery(function($) {
            // Default Select2 configuration
            $('.select2').each(function() {
                var $this = $(this);
                var config = {
                    theme: 'classic',
                    width: '100%',
                    placeholder: $this.data('placeholder') || 'Maak een keuze...',
                    allowClear: true,
                    language: {
                        noResults: function() {
                            return "Geen resultaten gevonden";
                        },
                        searching: function() {
                            return "Zoeken...";
                        },
                        inputTooShort: function(args) {
                            var remainingChars = args.minimum - args.input.length;
                            return "Voer nog " + remainingChars + " karakter(s) in";
                        },
                        loadingMore: function() {
                            return "Meer resultaten laden...";
                        },
                        maximumSelected: function(args) {
                            return "Je kunt maximaal " + args.maximum + " item(s) selecteren";
                        }
                    }
                };
                
                // Check if data-no-scroll attribute is present
                if ($this.data('no-scroll') === true || $this.data('no-scroll') === 'true') {
                    config.dropdownCssClass = 'select2-dropdown-no-scroll';
                }
                
                $this.select2(config);
                
                // Add validation trigger after Select2 initialization
                $this.on('select2:select select2:unselect select2:clear change', function() {
                    var field = this;
                    field.dataset.touched = 'true';
                    
                    // Trigger validation if validation function exists
                    if (typeof window.validateSelect2Field === 'function') {
                        window.validateSelect2Field(field);
                    }
                });
            });
            
            // For nationality select with search
            $('#nationaliteit').select2({
                theme: 'classic',
                width: '100%',
                placeholder: 'Kies een nationaliteit...',
                allowClear: false,
                minimumResultsForSearch: -1, // Disable search for small lists
                language: {
                    noResults: function() {
                        return "Geen nationaliteit gevonden";
                    },
                    searching: function() {
                        return "Zoeken...";
                    }
                }
            });
            
            // For year select (small list, no scroll needed)
            $('#studyYear').select2({
                theme: 'classic',
                width: '100%',
                placeholder: 'Kies een jaar...',
                minimumResultsForSearch: -1, // Disable search for small lists
                allowClear: false,
                dropdownCssClass: 'select2-dropdown-no-scroll'
            });
            
            // For previous school year select (small list, no search needed)
            $('#vorigeSchoolJaar').select2({
                theme: 'classic',
                width: '100%',
                placeholder: 'Selecteer een jaar...',
                minimumResultsForSearch: -1, // Disable search for small lists
                allowClear: false,
                dropdownCssClass: 'select2-dropdown-no-scroll'
            });
            
            // Custom logic for previous school "anders" functionality
            const vorigeSchoolSelect = $('#vorigeSchool');
            const andersSchoolContainer = $('#andersSchoolContainer');
            const andersSchoolInput = $('#vorigeSchoolAnders');
            
            if (vorigeSchoolSelect.length && andersSchoolContainer.length) {
                // Handle selection change
                vorigeSchoolSelect.on('select2:select change', function(e) {
                    const value = $(this).val();
                    
                    if (value === '0') {
                        andersSchoolContainer.slideDown(300);
                        andersSchoolInput.prop('required', true);
                        setTimeout(function() {
                            andersSchoolInput.focus();
                        }, 350);
                    } else {
                        andersSchoolContainer.slideUp(300);
                        andersSchoolInput.prop('required', false);
                        andersSchoolInput.val('');
                    }
                });
                
                // Check initial state on page load
                if (vorigeSchoolSelect.val() === '0') {
                    andersSchoolContainer.show();
                    andersSchoolInput.prop('required', true);
                }
            }
        });
    }
});

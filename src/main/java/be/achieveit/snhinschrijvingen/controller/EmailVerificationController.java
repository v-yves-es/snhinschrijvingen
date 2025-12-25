package be.achieveit.snhinschrijvingen.controller;

import be.achieveit.snhinschrijvingen.dto.EmailForm;
import be.achieveit.snhinschrijvingen.model.Registration;
import be.achieveit.snhinschrijvingen.service.EmailVerificationService;
import be.achieveit.snhinschrijvingen.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inschrijving")
public class EmailVerificationController {

    private final RegistrationService registrationService;
    private final EmailVerificationService emailVerificationService;

    public EmailVerificationController(
            final RegistrationService registrationService,
            final EmailVerificationService emailVerificationService) {
        this.registrationService = registrationService;
        this.emailVerificationService = emailVerificationService;
    }

    @GetMapping("/start")
    public String showEmailVerification(Model model) {
        model.addAttribute("emailForm", new EmailForm());
        return "email-verification";
    }

    @PostMapping("/start")
    public String processEmailVerification(@ModelAttribute EmailForm emailForm, RedirectAttributes redirectAttributes) {
        // Create new registration
        Registration registration = registrationService.createRegistration(emailForm.getEmailadres());
        
        // Send verification email (currently logs to console)
        emailVerificationService.sendVerificationEmail(registration);
        
        // Add email to redirect attributes
        redirectAttributes.addFlashAttribute("email", emailForm.getEmailadres());
        
        return "redirect:/inschrijving/email-sent";
    }

    @GetMapping("/email-sent")
    public String showEmailSent(Model model) {
        // Email should be available from flash attributes
        if (!model.containsAttribute("email")) {
            return "redirect:/inschrijving/start";
        }
        return "email-sent";
    }
}

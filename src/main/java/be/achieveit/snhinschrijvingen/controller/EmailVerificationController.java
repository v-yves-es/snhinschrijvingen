package be.achieveit.snhinschrijvingen.controller;

import be.achieveit.snhinschrijvingen.dto.EmailForm;
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

    @GetMapping("/start")
    public String showEmailVerification(Model model) {
        model.addAttribute("emailForm", new EmailForm());
        return "email-verification";
    }

    @PostMapping("/start")
    public String processEmailVerification(@ModelAttribute EmailForm emailForm, RedirectAttributes redirectAttributes) {
        // Store email in session for later use
        // TODO: Send verification email
        
        // Add email to redirect attributes so it can be displayed on next page
        redirectAttributes.addFlashAttribute("email", emailForm.getEmailadres());
        
        return "redirect:/inschrijving/email-sent";
    }

    @GetMapping("/email-sent")
    public String showEmailSent(Model model) {
        // Email should be available from flash attributes
        if (!model.containsAttribute("email")) {
            // If accessed directly without email, redirect back to start
            return "redirect:/inschrijving/start";
        }
        return "email-sent";
    }
}

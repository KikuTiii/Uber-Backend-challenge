package estudos.uberbackendchallenge.controllers;

import estudos.uberbackendchallenge.application.EmailSenderService;
import estudos.uberbackendchallenge.core.EmailRequest;
import estudos.uberbackendchallenge.core.exceptions.EmailServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailSenderController {

    private final EmailSenderService emailSenderService;

    @Autowired
    public EmailSenderController(EmailSenderService emailService) {
        this.emailSenderService = emailService;
    }

    @PostMapping()
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest resquest){
        try{
            emailSenderService.sendEmail(resquest.to(), resquest.subject(), resquest.body());
            return ResponseEntity.ok("Email sent successfully");
        } catch (EmailServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email: " + e.getMessage());
        }
    }
}

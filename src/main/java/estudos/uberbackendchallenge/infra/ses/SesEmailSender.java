package estudos.uberbackendchallenge.infra.ses;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import estudos.uberbackendchallenge.adapters.EmailSenderGateway;
import estudos.uberbackendchallenge.core.exceptions.EmailServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SesEmailSender implements EmailSenderGateway {

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    @Autowired
    public SesEmailSender(AmazonSimpleEmailService amazonSimpleEmailService) {
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(to))
                .withMessage(new Message()
                        .withBody(new Body().withText(new Content().withData(body)))
                        .withSubject(new Content().withData(subject)))
                .withSource("matheus.ksousa@sptech.school");

        try {
            this.amazonSimpleEmailService.sendEmail(request);
        } catch (AmazonServiceException e) {
            throw new EmailServiceException("Failed to send email", e);
        }
    }
}

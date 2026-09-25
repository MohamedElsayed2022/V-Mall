package com.api.config.auth.email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import java.util.HashMap;
import java.util.Map;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    @Async
    public void sendEmail(
            String to,
            String username,
            EmailTemplateName emailTemplate ,
            String confirmationUrl ,
            String activationCode ,
            String subject
    ) throws MessagingException {
        String templateName;
        if(emailTemplate == null){
            templateName = "confirm_email";
        }else{
            templateName = emailTemplate.getName();
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage ,
                MULTIPART_MODE_MIXED ,
                UTF_8.name()
        );
        Map<String, Object> properties = new HashMap<>();
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activation_code", activationCode);
        properties.put("username", username);
        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("mohamedsayed33333333@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName, context);

        helper.setText(template, true);

        mailSender.send(mimeMessage);

    }
}

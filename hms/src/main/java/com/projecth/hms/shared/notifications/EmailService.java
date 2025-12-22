package com.projecth.hms.shared.notifications;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

//    @Async
//    public void sendEmail(String to, String subject, String body) {
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(body);
//
//        mailSender.send(message);
//    }
    @Async
    public void sendEmail(String to, String subject, String username, String message, String actionLink) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

//            String content = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/email.html")), StandardCharsets.UTF_8);
//            content = content.replace("{{username}}", username)
//                    .replace("{{message}}", message)
//                    .replace("{{action_link}}", actionLink);
            ClassPathResource resource = new ClassPathResource("templates/email.html");
            String content = new String(Files.readAllBytes(resource.getFile().toPath()), StandardCharsets.UTF_8);

            content = content.replace("{{username}}", username)
                    .replace("{{message}}", message)
                    .replace("{{action_link}}", actionLink);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // true = HTML

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


package market.service.impl;

import lombok.extern.slf4j.Slf4j;
import market.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;

import java.util.Random;

import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    @Value("${app.mail.username}")
    private String username; // Замените на вашу электронную почту
    @Value("${app.mail.password}")
    private String password; // Замените на ваш пароль


    public Integer sendCodeToConfirmEmail(String recipientEmail) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.mail.ru");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            var generatedCode = generateCode();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

            message.setSubject("Confirming email");
            message.setText("Your code for confirming email: " + generatedCode);

            Transport.send(message);
            log.info("Email sent successfully!");
            return generatedCode;
        } catch (SendFailedException e) {
            log.info("Email is invalid");
            return 1;
        } catch (MessagingException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public Integer generateCode() {
        Random random = new Random();
        int min = 100000;
        int max = 999999;

        return random.nextInt((max - min) + 1) + min;
    }
}

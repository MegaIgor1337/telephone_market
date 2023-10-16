package market.service.impl;

import lombok.extern.slf4j.Slf4j;
import market.service.EmailService;
import org.springframework.stereotype.Service;

import javax.mail.*;

import java.util.Random;

import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    private static final String USERNAME = "mobile-online-store@mail.ru"; // Замените на вашу электронную почту
    private static final String PASSWORD = "Ckfe2r9jKyeTdgk7AyNV"; // Замените на ваш пароль


    public Integer sendCodeToConfirmEmail(String recipientEmail) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.mail.ru");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            var generatedCode = generateCode();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
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

    public static void main(String[] args) {
        final String username = "mobile-online-store@mail.ru";
        final String password = "Ckfe2r9jKyeTdgk7AyNV";
        String recipientEmail = "tawerka1337228321@mail.ru";

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
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            Random random = new Random();
            int min = 100000;
            int max = 999999;

            int sixDigitNumber = random.nextInt((max - min) + 1) + min;
            message.setSubject("Confirming email");
            message.setText("Your code for confirming email: " + sixDigitNumber);

            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (SendFailedException e) {
            System.out.println("Email is invalid");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

package market.service.impl;

import lombok.extern.slf4j.Slf4j;
import market.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.util.Properties;
import java.util.Random;


@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    @Value("${app.mail.username}")
    private String username; // Замените на вашу электронную почту
    @Value("${app.mail.password}")
    private String password; // Замените на ваш пароль

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.mail.ru");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        return properties;
    }

    public Integer sendCodeToConfirmEmail(String recipientEmail) {
        Properties properties = getProperties();

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

    public void sendPdfFileOnEmail(String recipientEmail, ByteArrayOutputStream pdfStream) {
        Properties properties = getProperties();

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Order Invoice");

            // Вложение PDF в сообщение
            DataSource source = new ByteArrayDataSource(pdfStream.toByteArray(), "application/pdf");

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName("Order_Invoice.pdf");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachmentBodyPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public Integer generateCode() {
        Random random = new Random();
        int min = 100000;
        int max = 999999;

        return random.nextInt((max - min) + 1) + min;
    }
}

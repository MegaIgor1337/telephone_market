package market.service;

import java.io.ByteArrayOutputStream;

public interface EmailService {
    Integer generateCode();
    Integer sendCodeToConfirmEmail(String recipientEmail);
    void   sendPdfFileOnEmail(String recipientEmail, ByteArrayOutputStream pdfStream);
}

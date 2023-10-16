package market.service;

public interface EmailService {
    Integer generateCode();
    Integer sendCodeToConfirmEmail(String recipientEmail);

}

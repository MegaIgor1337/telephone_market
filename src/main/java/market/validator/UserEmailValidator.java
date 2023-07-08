package market.validator;

import market.dto.IEmailUserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static market.util.ConstantContainer.*;

@Component
public class UserEmailValidator implements Validator<String> {
    private  List<IEmailUserDto> emails;

    @Override
    public ValidationResult isValid(String object) {
        var validationResult = new ValidationResult();
        Pattern patternForEmail = Pattern.compile(REGEX_FOR_EMAIL);
        Matcher matcherForEmail = patternForEmail.matcher(object);
        if (!matcherForEmail.find()) {
            validationResult.add(Error.of(Error.getMessage(EMAIL), EMAIL_IS_WRONG));
        }
        if (emails.stream().map(IEmailUserDto::getEmail).toList().contains(object)) {
            validationResult.add(Error.of(Error.getMessage(EMAIL), EMAIL_IS_USED));
        }
        return validationResult;
    }
    public void putEmails(List<IEmailUserDto> emails) {
        this.emails = emails;
    }
}

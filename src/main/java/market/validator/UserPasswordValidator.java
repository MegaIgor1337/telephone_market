package market.validator;

import market.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static market.util.StringContainer.*;

@Component
public class UserPasswordValidator implements Validator<String> {

    @Override
    public ValidationResult isValid(String object) {
        var validationResult = new ValidationResult();
        Pattern patternForPassword = Pattern.compile(REGEX_FOR_PASSWORD);
        Matcher matcherForPassword = patternForPassword.matcher(object);
        if (!matcherForPassword.find()) {
            validationResult.add(Error.of(Error.getMessage(PASSWORD), PASSWORD_IS_INVALID ));
        }
        return validationResult;
    }

    public ValidationResult isValid(UserDto userDto, String password) {
        var validationResult = new ValidationResult();
        if (!userDto.getPassword().equals(password)) {
            validationResult.add(Error.of(Error.getMessage(PASSWORD), PASSWORD_IS_WRONG));
        }
        return validationResult;
    }
}

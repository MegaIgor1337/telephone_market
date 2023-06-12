package market.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static market.util.StringContainer.*;

@Component
public class UserPassportNoValidator implements Validator<String> {
    @Override
    public ValidationResult isValid(String object) {
        var validationResult = new ValidationResult();
        Pattern patternForPassportNo = Pattern.compile(REGEX_FOR_PASSPORT);
        Matcher matcherForPassportNo = patternForPassportNo.matcher(object);
        if (!matcherForPassportNo.find()) {
            validationResult.add(Error.of(Error.getMessage(PASSPORT_NO), PASSPORT_IS_INVALID));
        }
        return validationResult;
    }
}

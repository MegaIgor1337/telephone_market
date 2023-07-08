package market.validator;

import market.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static market.util.ConstantContainer.LOGIN;
import static market.util.ConstantContainer.LOGIN_MESSAGE;

@Component
public class LoginUserValidator implements Validator<Optional<UserDto>> {

    @Override
    public ValidationResult isValid(Optional<UserDto> object) {
        var validationResult = new ValidationResult();
        if (object.isEmpty()) {
            validationResult.add(Error.of(Error.getMessage(LOGIN), LOGIN_MESSAGE));
        }
        return validationResult;
    }
}

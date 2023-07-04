package market.validator;

import market.dto.INameUserDto;
import org.springframework.stereotype.Component;

import java.util.List;

import static market.util.StringContainer.*;

@Component
public class UsernameValidator implements Validator<String> {

    private List<INameUserDto> usersNames;
    @Override
    public ValidationResult isValid(String object) {
        var validationResult = new ValidationResult();
        if (usersNames.stream().map(INameUserDto::getUsername).toList().contains(object)) {
            validationResult.add(Error.of(Error.getMessage(NAME), LOGIN_IS_USED));
        }
        if (object == null) {
            validationResult.add(Error.of(Error.getMessage(NAME), NAME_IS_NULL));
        }
        return validationResult;
    }

    public void putUsersNames(List<INameUserDto> names) {
        this.usersNames = names;
    }
}

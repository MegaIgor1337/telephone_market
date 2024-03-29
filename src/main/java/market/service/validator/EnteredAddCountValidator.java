package market.service.validator;

import org.springframework.stereotype.Component;

import static market.service.util.ConstantContainer.COUNT;
import static market.service.util.ConstantContainer.COUNT_ENTERED_INVALID;

@Component
public class EnteredAddCountValidator implements Validator<String> {
    @Override
    public ValidationResult isValid(String object) {
        var validationResult = new ValidationResult();
        if (object != null && !object.isBlank()
            && Integer.parseInt(object) < 1) {
            validationResult.add(Error.of(Error.getMessage(COUNT), COUNT_ENTERED_INVALID));
        }
        return validationResult;
    }
}

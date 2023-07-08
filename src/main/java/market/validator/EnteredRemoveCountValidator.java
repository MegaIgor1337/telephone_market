package market.validator;

import org.springframework.stereotype.Component;

import static market.util.ConstantContainer.*;

@Component
public class EnteredRemoveCountValidator implements Validator<String> {
    private Integer productCount;
    @Override
    public ValidationResult isValid(String object) {
        var validationResult = new ValidationResult();
        if (object != null && !object.isBlank()
            && Integer.parseInt(object) < 1) {
            validationResult.add(Error.of(Error.getMessage(COUNT), COUNT_ENTERED_INVALID));
        }
        if (object != null && !object.isBlank()
            && productCount < Integer.parseInt(object)) {
            validationResult.add(Error.of(Error.getMessage(COUNT), REMOVE_COUNT_INVALID));
        }
        return validationResult;
    }

    public void putProductCount(Integer productCount) {
        this.productCount = productCount;
    }
}

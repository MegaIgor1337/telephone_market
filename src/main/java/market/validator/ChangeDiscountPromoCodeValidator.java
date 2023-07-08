package market.validator;

import org.springframework.stereotype.Component;

import static market.util.ConstantContainer.*;

@Component
public class ChangeDiscountPromoCodeValidator implements Validator<Double> {
    @Override
    public ValidationResult isValid(Double object) {
        var validationResult = new ValidationResult();
        if (object < 0) {
            validationResult.add(Error.of(Error.getMessage(PROMO_CODE), DISCOUNT_LESS_ZERO));
        }
        if (object > 100) {
            validationResult.add(Error.of(Error.getMessage(PROMO_CODE), DISCOUNT_MORE_HUNDRED));
        }
        return validationResult;
    }
}

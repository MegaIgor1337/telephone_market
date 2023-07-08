package market.validator;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static market.util.ConstantContainer.BALANCE;
import static market.util.ConstantContainer.BALANCE_MESSAGE;

@Component
public class BalanceValidator implements Validator<String> {

    @Override
    public ValidationResult isValid(String object) {
        var balance = new BigDecimal(object);
        var validationResult = new ValidationResult();
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            validationResult.add(Error.of(Error.getMessage(BALANCE), BALANCE_MESSAGE));
        }
        return validationResult;
    }
}

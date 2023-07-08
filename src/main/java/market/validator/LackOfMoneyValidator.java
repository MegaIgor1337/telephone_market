package market.validator;


import lombok.NoArgsConstructor;
import market.dto.UserDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static market.util.ConstantContainer.*;

@Component
@NoArgsConstructor
public class LackOfMoneyValidator  implements Validator<UserDto> {
    private BigDecimal orderCost;


    @Override
    public ValidationResult isValid(UserDto object) {
        var validationResult = new ValidationResult();
        if (object.getBalance().compareTo(orderCost) < 0) {
            validationResult.add(Error.of(Error.getMessage(BALANCE), LACK_OF_MONEY_INVALID));
        }
        return validationResult;
    }

    public void putOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }
}

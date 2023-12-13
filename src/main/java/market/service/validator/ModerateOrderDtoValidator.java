package market.service.validator;

import market.service.dto.ModerateOrderDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static market.service.util.ConstantContainer.DATE_OF_DELIVERY;
import static market.service.util.ConstantContainer.MESSAGE_DATE_OF_DELIVERY_INVALID;

@Component
public class ModerateOrderDtoValidator implements Validator<ModerateOrderDto> {
    @Override
    public ValidationResult isValid(ModerateOrderDto object) {
        var validationResult = new ValidationResult();
        if (object.getDateOfDelivery() != null && object.getDateOfDelivery().isBefore(LocalDate.now())) {
            validationResult.add(Error.of(Error.getMessage(DATE_OF_DELIVERY), MESSAGE_DATE_OF_DELIVERY_INVALID));
        }
        return validationResult;
    }
}

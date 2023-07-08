package market.validator;

import market.dto.PromoCodeFilter;
import market.entity.PromoCode;
import org.springframework.stereotype.Component;

import java.util.List;

import static market.util.ConstantContainer.INCORRECT_PROMO_CODE;
import static market.util.ConstantContainer.PROMO_CODE;

@Component
public class EnteredPromoCodeValidator implements Validator<PromoCodeFilter> {
    private List<PromoCode> promoCodes;
    @Override
    public ValidationResult isValid(PromoCodeFilter object) {
        var validationResult = new ValidationResult();
        if (object.getName() != null && !object.getName().isBlank()
            && !promoCodes.stream().map(PromoCode::getName).toList().contains(object.getName())) {
            validationResult.add(Error.of(Error.getMessage(PROMO_CODE), INCORRECT_PROMO_CODE));
        }
        return validationResult;
    }

    public void putPromoCodes(List<PromoCode> promoCodes) {
        this.promoCodes = promoCodes;
    }
}

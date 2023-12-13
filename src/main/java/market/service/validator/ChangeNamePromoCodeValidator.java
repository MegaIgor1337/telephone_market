package market.service.validator;

import market.model.entity.PromoCode;
import org.springframework.stereotype.Component;

import java.util.List;

import static market.service.util.ConstantContainer.PROMO_CODE;
import static market.service.util.ConstantContainer.PROMO_CODE_NAME_INVALID;

@Component
public class ChangeNamePromoCodeValidator implements Validator<String> {
    private List<PromoCode> promoCodes;
    private Long promoCodeId;

    public void putPromoCodeAndPromoCodeId(List<PromoCode> promoCodes, Long promoCodeId) {
        this.promoCodes = promoCodes;
        this.promoCodeId = promoCodeId;
    }

    @Override
    public ValidationResult isValid(String object) {
        var validationResult = new ValidationResult();
        if (promoCodes.stream().filter(p -> !p.getId().equals(promoCodeId))
                .map(PromoCode::getName).toList().contains(object)) {
            validationResult.add(Error.of(Error.getMessage(PROMO_CODE), PROMO_CODE_NAME_INVALID));
        }
        return validationResult;
    }
}

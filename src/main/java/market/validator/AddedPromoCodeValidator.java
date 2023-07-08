package market.validator;

import market.dto.CreatePromoCodeDto;
import market.entity.PromoCode;
import org.springframework.stereotype.Component;

import java.util.List;

import static market.util.ConstantContainer.*;
import static market.util.ConstantContainer.DISCOUNT_MORE_HUNDRED;

@Component
public class AddedPromoCodeValidator implements Validator<CreatePromoCodeDto> {
    private List<PromoCode> promoCodes;
    @Override
    public ValidationResult isValid(CreatePromoCodeDto object) {
        var validationResult = new ValidationResult();
        if (object.getName() == null || object.getName().isBlank()) {
            validationResult.add(Error.of(Error.getMessage(PROMO_CODE), PROMO_CODE_NAME_EMPTY));
        }
        if (object.getDiscount() == null) {
            validationResult.add(Error.of(Error.getMessage(PROMO_CODE), PROMO_CODE_DISCOUNT_EMPTY));
        }
        if (promoCodes.stream().map(PromoCode::getName).toList().contains(object.getName())) {
            validationResult.add(Error.of(Error.getMessage(PROMO_CODE), PROMO_CODE_NAME_INVALID));
        }
        if (object.getDiscount() < 0) {
            validationResult.add(Error.of(Error.getMessage(PROMO_CODE), DISCOUNT_LESS_ZERO));
        }
        if (object.getDiscount() > 100) {
            validationResult.add(Error.of(Error.getMessage(PROMO_CODE), DISCOUNT_MORE_HUNDRED));
        }
        if (object.getProductsId() == null || object.getProductsId().isEmpty()) {
            validationResult.add(Error.of(Error.getMessage(PROMO_CODE), PRODUCTS_EMPTY));
        }
        return validationResult;
    }

    public void putPromoCodes(List<PromoCode> promoCodes) {
        this.promoCodes = promoCodes;
    }
}

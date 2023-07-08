package market.validator;

import market.entity.Order;
import market.entity.PromoCode;
import market.enums.PromoCodeStatus;
import org.springframework.stereotype.Component;

import java.util.List;

import static market.util.ConstantContainer.*;

@Component
public class PromoCodeValidator implements Validator<String> {
    private List<PromoCode> promoCodes;
    private List<Order> orders;
    @Override
    public ValidationResult isValid(String object) {
        var validationResult = new ValidationResult();
        if (object == null) {
            return validationResult;
        }
        if (orders.size() > 1 && FIRST_ORDER_PROMO_CODE.equals(object)) {
            validationResult.add(Error.of(Error.getMessage(PROMO_CODE), INCORRECT_FIRST_ORDER));
        }
        if (!promoCodes.stream().filter(p -> p.getStatus().equals(PromoCodeStatus.ACTIVE))
                .map(PromoCode::getName).toList().contains(object)) {
            validationResult.add(Error.of(Error.getMessage(PROMO_CODE), INCORRECT_PROMO_CODE));
        }
        return validationResult;
    }

    public void putOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void putPromCodes(List<PromoCode> promoCodes) {
        this.promoCodes = promoCodes;
    }
}

package market.validator;

import market.dto.ICreateAddressDto;
import org.springframework.stereotype.Component;

import static market.util.ConstantContainer.*;

@Component
public class CreateAddressValidator implements Validator<ICreateAddressDto> {
    @Override
    public ValidationResult isValid(ICreateAddressDto object) {
        var validationResult = new ValidationResult();
        if (object.getCountry() == null) {
            validationResult.add(Error.of(Error.getMessage(COUNTRY), COUNTRY_IS_NULL));
        }
        if (object.getCity() == null) {
            validationResult.add(Error.of(Error.getMessage(CITY), CITY_IS_NULL));
        }
        if (object.getStreet() == null) {
            validationResult.add(Error.of(Error.getMessage(STREET), STREET_IS_NULL));
        }
        if (object.getHouse() == null) {
            validationResult.add(Error.of(Error.getMessage(HOUSE), HOUSE_IS_NULL));
        }
        return validationResult;
    }

}

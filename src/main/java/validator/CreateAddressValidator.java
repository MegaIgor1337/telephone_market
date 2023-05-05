package validator;

import dto.CreateAddressDto;

public class CreateAddressValidator implements Validator<CreateAddressDto> {
    private static final CreateAddressValidator INSTANCE = new CreateAddressValidator();
    @Override
    public ValidationResult isValid(CreateAddressDto object) {
        var validationResult = new ValidationResult();
        if (object.getCountry() == null) {
            validationResult.add(Error.of("invalid.country", "country is null"));
        }
        if (object.getCity() == null) {
            validationResult.add(Error.of("invalid.city", "city is null"));
        }
        if (object.getStreet() == null) {
            validationResult.add(Error.of("invalid.street", "street is null"));
        }
        if (object.getHouse() == null) {
            validationResult.add(Error.of("invalid.house", "house is null"));
        }
        return validationResult;
    }

    public static CreateAddressValidator getInstance() {
        return INSTANCE;
    }
}

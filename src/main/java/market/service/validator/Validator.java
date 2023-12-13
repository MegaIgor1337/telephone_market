package market.service.validator;

public interface    Validator<T> {
    ValidationResult isValid(T object);
}

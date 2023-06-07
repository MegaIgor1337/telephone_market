package market.validator;

public interface Validator<T> {
    ValidationResult isValid(T object);
}

package market.exception;

import lombok.Getter;
import market.service.validator.Error;

import java.util.List;

public class LackOfMoneyException extends RuntimeException{
    @Getter
    private final List<Error> errors;

    public LackOfMoneyException(List<Error> errors) {
        this.errors = errors;
    }
}

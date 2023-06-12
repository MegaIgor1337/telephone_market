package market.validator;

import lombok.Value;

import static market.util.StringContainer.INVALID;


@Value(staticConstructor = "of")
public class Error {
    final static String CODE_MESSAGE = INVALID;
    String code;
    String message;

    public static String getMessage(String message) {
        return CODE_MESSAGE + message;
    }
}
package market.validator;

import lombok.Value;
import org.springframework.stereotype.Component;


@Value(staticConstructor = "of")
public class Error {
    String code;
    String message;
}
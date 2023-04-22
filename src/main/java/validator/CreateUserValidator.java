package validator;

import dto.user.CreateUserDto;
import entity.user.Gender;
import entity.user.Role;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDto> {

    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidationResult isValid(CreateUserDto object) {
        var validationResult = new ValidationResult();
        Pattern patternForPassword = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,}$");
        Matcher matcherForPassword = patternForPassword.matcher(object.getPassword());
        Pattern patternForPassportNo = Pattern.compile("^[a-zA-Z]{2}\\d{6}$");
        Matcher matcherForPassportNo = patternForPassportNo.matcher(object.getPassportNo());
        Pattern patternForEmail = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcherForEmail = patternForEmail.matcher(object.getEmail());
        if (!matcherForPassword.find()) {
            validationResult.add(Error.of("invalid.password",
                    "Password is simple." +
                    " The password must be at least 8 characters " +
                    "long, contain at least 1 uppercase letter, 1 lowercase letter," +
                    " and EITHER a special character OR a number."));
        }
        if (Gender.find(object.getGender()).isEmpty()) {
            validationResult.add(Error.of("invalid.gender", "Gender is invalid"));
        }
        if (!matcherForPassportNo.find()) {
            validationResult.add(Error.of("invalid.passportNo",
                    "Passport number is invalid"));
        }
        if (!matcherForEmail.find()) {
            validationResult.add(Error.of("invalid.email", "Email is invalid"));
        }

        if (Role.find(object.getRole()).isEmpty()) {
            validationResult.add(Error.of("invalid.role", "Role is invalid"));
        }
        return validationResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
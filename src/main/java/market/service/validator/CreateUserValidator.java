package market.service.validator;

import lombok.RequiredArgsConstructor;
import market.service.dto.CreateUserDto;
import market.service.dto.IValidateUserInfoDto;
import market.model.enums.Gender;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static market.service.util.ConstantContainer.*;

@Component
@RequiredArgsConstructor
public class CreateUserValidator implements Validator<CreateUserDto> {
    private List<IValidateUserInfoDto> validateUserInfoList;
    @Override
    public ValidationResult isValid(CreateUserDto object) {
        var validationResult = new ValidationResult();
        Pattern patternForPassword = Pattern.compile(REGEX_FOR_PASSWORD);
        Matcher matcherForPassword = patternForPassword.matcher(object.getRawPassword());
        Pattern patternForPassportNo = Pattern.compile(REGEX_FOR_PASSPORT);
        Matcher matcherForPassportNo = patternForPassportNo.matcher(object.getPassportNo());
        Pattern patternForEmail = Pattern.compile(REGEX_FOR_EMAIL);
        Matcher matcherForEmail = patternForEmail.matcher(object.getEmail());
        if (validateUserInfoList.stream().map(IValidateUserInfoDto::getUsername).toList().contains(object.getUsername())) {
            validationResult.add(Error.of(Error.getMessage(NAME), LOGIN_IS_USED));
        }
        if (validateUserInfoList.stream().map(IValidateUserInfoDto::getEmail).toList().contains(object.getEmail())) {
            validationResult.add(Error.of(Error.getMessage(EMAIL), EMAIL_IS_USED));
        }
        if (!matcherForPassword.find()) {
            validationResult.add(Error.of(Error.getMessage(PASSWORD), PASSWORD_IS_INVALID));
        }
        if (Gender.find(object.getGender()).isEmpty()) {
            validationResult.add(Error.of(Error.getMessage(GENDER), GENDER_IS_INVALID));
        }
        if (!matcherForPassportNo.find()) {
            validationResult.add(Error.of(Error.getMessage(PASSPORT_NO), PASSPORT_IS_INVALID));
        }
        if (!matcherForEmail.find()) {
            validationResult.add(Error.of(Error.getMessage(EMAIL), EMAIL_IS_WRONG));
        }
        return validationResult;
    }

    public void putUserValidationInfo(List<IValidateUserInfoDto> list) {
        this.validateUserInfoList = list;
    }


}
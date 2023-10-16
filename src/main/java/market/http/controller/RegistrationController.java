package market.http.controller;

import lombok.RequiredArgsConstructor;
import market.dto.CreateUserDto;
import market.dto.UserDto;
import market.enums.Gender;
import market.enums.Role;
import market.exception.ValidationException;
import market.service.EmailService;
import market.service.UserService;
import market.service.impl.UserServiceImpl;
import market.util.AccessDeniedExceptionHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static market.util.ModelHelper.addAttributes;
import static market.util.ModelHelper.redirectAttributes;
import static market.util.ConstantContainer.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
@SessionAttributes({USER_DTO, ERRORS, CREATE_USER_DTO})
public class RegistrationController {
    private final UserService userService;
    private final EmailService emailService;
    private Integer generatedCode;

    @ModelAttribute(ROLES)
    public List<Role> getRoles() {
        return Arrays.asList(Role.values());
    }

    @ModelAttribute(GENDERS)
    public List<Gender> getGenders() {
        return Arrays.asList(Gender.values());
    }



    @GetMapping
    public String registration() {
        SecurityContextHolder.clearContext();
        return "/registration";
    }

    @PostMapping
    public String registration(Model model, CreateUserDto createUserDto,
                               RedirectAttributes redirectAttributes) {
        try {
            String message;
            userService.validCreateUserDto(createUserDto);
            generatedCode = emailService.sendCodeToConfirmEmail(createUserDto.getEmail());
            if (generatedCode.equals(0)) {
                message = "Something wrong. Try again later";
                addAttributes(model, Map.of(ERRORS, message));
                redirectAttributes(redirectAttributes, createUserDto);
            } else if (generatedCode.equals(1)) {
                message = "This email does not exist";
                addAttributes(model, Map.of(ERRORS, message));
                redirectAttributes(redirectAttributes, createUserDto);
            }
            addAttributes(model, Map.of(CREATE_USER_DTO, createUserDto));
            return "redirect:/registration/confirmEmail";
        } catch (ValidationException exception) {
            addAttributes(model, Map.of(ERRORS, exception.getErrors()));
            redirectAttributes(redirectAttributes, createUserDto);
            return "redirect:/registration";
        }
    }

    @GetMapping("/confirmEmail")
    public String confirmEmail() {
        return "/confirmEmail";
    }

    @PostMapping("/confirmEmail")
    public String confirmEmail(@RequestParam String inputCode,
                               Model model,
                               RedirectAttributes redirectAttributes,
                               @SessionAttribute CreateUserDto createUserDto) {
        if (Objects.equals(generatedCode, Integer.valueOf(inputCode))) {
            var userDto = userService.create(createUserDto);
            addAttributes(model, Map.of(USER_DTO, userDto));
            return "redirect:/users";
        } else {
            redirectAttributes(redirectAttributes, Map.of(EMAIL_CONFIRM_ERROR,
                    MESSAGE_IF_CODE_FOR_EMAIL_IS_WRONG));
            return "redirect:/registration/confirmEmail";
        }
    }

}

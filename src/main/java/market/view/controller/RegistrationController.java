package market.view.controller;

import lombok.RequiredArgsConstructor;
import market.service.dto.CreateUserDto;
import market.model.enums.Gender;
import market.model.enums.Role;
import market.exception.ValidationException;
import market.service.EmailService;
import market.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static market.service.util.ModelHelper.addAttributes;
import static market.service.util.ModelHelper.redirectAttributes;
import static market.service.util.ConstantContainer.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
@SessionAttributes({USER_DTO, ERRORS, CREATE_USER_DTO})
public class RegistrationController {
    private final UserService userService;
    private final EmailService emailService;
    private Integer generatedCode;
    private MultipartFile multipartFile;
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
            multipartFile = createUserDto.getImage();
            createUserDto.setImage(null);
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
                               @SessionAttribute(name = CREATE_USER_DTO) CreateUserDto createUserDto) throws IOException {
        if (Objects.equals(generatedCode, Integer.valueOf(inputCode))) {
            createUserDto.setImage(multipartFile);
            var userDto = userService.create(createUserDto);
            User user = new User(createUserDto.getUsername(), createUserDto.getRawPassword(),
                    AuthorityUtils.createAuthorityList(Role.USER.toString()));
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user, createUserDto.getRawPassword(), user.getAuthorities())
            );
            addAttributes(model, Map.of(USER_DTO, userDto));
            return "redirect:/users";
        } else {
            redirectAttributes(redirectAttributes, Map.of(EMAIL_CONFIRM_ERROR,
                    MESSAGE_IF_CODE_FOR_EMAIL_IS_WRONG));
            return "redirect:/registration/confirmEmail";
        }
    }

}

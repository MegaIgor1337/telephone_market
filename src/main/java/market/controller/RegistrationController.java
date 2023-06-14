package market.controller;

import lombok.RequiredArgsConstructor;
import market.dto.CreateUserDto;
import market.enums.Gender;
import market.enums.Role;
import market.exception.ValidationException;
import market.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

import static market.util.StringContainer.*;

@Controller
@RequiredArgsConstructor
@SessionAttributes({USER_DTO, ERRORS})
public class RegistrationController {
    private final UserService userService;

    @ModelAttribute(ROLES)
    public List<Role> getRoles() {
        return Arrays.asList(Role.values());
    }

    @ModelAttribute(GENDERS)
    public List<Gender> getGenders() {
        return Arrays.asList(Gender.values());
    }



    @GetMapping("/registration")
    public String registration() {
        return "/registration";
    }

    @PostMapping("/registration")
    public String registration(Model model, String name, String password,
                               String passportNo, String email,
                               String gender, String role, RedirectAttributes redirectAttributes) {
        var createUserDto = CreateUserDto.builder()
                .name(name)
                .password(password)
                .passportNo(passportNo)
                .email(email)
                .gender(gender)
                .role(role)
                .build();
        try {
            var userDto = userService.create(createUserDto);
            model.addAttribute(USER_DTO, userDto);
            return "redirect:/address/addAddress";
        } catch (ValidationException exception) {
            model.addAttribute(ERRORS, exception.getErrors());
            redirectAttributes.addFlashAttribute(NAME, createUserDto.getName());
            redirectAttributes.addFlashAttribute(PASSWORD, createUserDto.getPassword());
            redirectAttributes.addFlashAttribute(PASSPORT_NO, createUserDto.getPassportNo());
            redirectAttributes.addFlashAttribute(EMAIL, createUserDto.getEmail());
            redirectAttributes.addFlashAttribute(ROLE, createUserDto.getRole());
            redirectAttributes.addFlashAttribute(GENDER, createUserDto.getGender());
            return "redirect:/registration";
        }
    }
}

package market.controller;

import lombok.RequiredArgsConstructor;
import market.dto.CreateUserDto;
import market.enums.Gender;
import market.enums.Role;
import market.exception.ValidationException;
import market.service.UserService;
import market.util.ModelHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static market.util.ModelHelper.addAttributes;
import static market.util.ModelHelper.redirectAttributes;
import static market.util.StringContainer.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
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



    @GetMapping
    public String registration() {
        return "/registration";
    }

    @PostMapping
    public String registration(Model model, CreateUserDto createUserDto,
                               RedirectAttributes redirectAttributes) {
        try {
            var userDto = userService.create(createUserDto);
            addAttributes(model, Map.of(USER_DTO, userDto));
            return "redirect:/address/addAddress";
        } catch (ValidationException exception) {
            addAttributes(model, Map.of(ERRORS, exception   .getErrors()));
            redirectAttributes(redirectAttributes, createUserDto);
            return "redirect:/registration";
        }
    }

}

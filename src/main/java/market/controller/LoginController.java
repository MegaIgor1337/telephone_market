package market.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import market.dto.UserDto;
import market.enums.Role;
import market.exception.ValidationException;
import market.service.CommentService;
import market.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static market.util.StringContainer.*;

@Controller
@SessionAttributes({USER_DTO, ERRORS, SIZE_MODERATE_COMMENTS})
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final CommentService commentService;
    @GetMapping("/login")
    public String loginPage() {
        return "/login";
    }

    @PostMapping("/login")
    public String validate(Model model, String name,
                           String password, RedirectAttributes redirectAttributes) {
        try {
            var user = userService.login(name, password);
            return user.map(userDto -> onLoginSuccess(model, userDto)).orElse(loginFail());
        } catch (ValidationException e) {
            model.addAttribute(ERRORS, e.getErrors());
            redirectAttributes.addFlashAttribute(NAME, name);
            redirectAttributes.addFlashAttribute(PASSWORD, password);
            return "redirect:/login";
        }
    }

    @GetMapping("login/fail")
    public String loginFail() {
        return "redirect:/login";
    }


    @SneakyThrows
    private String onLoginSuccess(Model model,
                                UserDto user) {
        model.addAttribute(USER_DTO, user);
        if (user.getRole().equals(Role.ADMIN)) {
           model.addAttribute(SIZE_MODERATE_COMMENTS, commentService.getModerateComments().size());
           return "redirect:/admin/menu";
        } else {
           return "redirect:/users/menu";
        }
    }
}

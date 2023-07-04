package market.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import market.dto.UserDto;
import market.enums.Role;
import market.exception.ValidationException;
import market.service.CommentService;
import market.service.UserService;
import market.util.ModelHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

import static market.util.ModelHelper.addAttributes;
import static market.util.StringContainer.*;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "/login";
    }

}

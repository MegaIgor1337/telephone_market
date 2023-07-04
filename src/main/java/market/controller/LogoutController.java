package market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static market.util.StringContainer.USER_DTO;

@Controller
public class LogoutController {
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}

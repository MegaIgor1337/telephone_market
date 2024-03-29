package market.view.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class LogoutController {
    @GetMapping("/logout")
    public String logout() {
        log.info("User log outs");
        return "redirect:/login";
    }
}

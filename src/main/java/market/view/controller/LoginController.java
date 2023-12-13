package market.view.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public String loginPage(SecurityContextHolder contextHolder) {
        log.info("User on logging");
        return "/login";
    }

}

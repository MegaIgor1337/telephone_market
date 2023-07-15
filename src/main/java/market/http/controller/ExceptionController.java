package market.http.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ExceptionController {
    @GetMapping("/error403")
    public String accessDenied() {
        return "error/error403";
    }
}
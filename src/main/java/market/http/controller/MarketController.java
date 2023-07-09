package market.http.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class MarketController {
    @GetMapping("/market")
    public String market() {
        log.info("User on market page");
        return "/market";
    }
}

package market.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MarketController {
    @GetMapping("/market")
    public String market() {
        return "/market";
    }
}

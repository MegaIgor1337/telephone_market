package market.view.controller;

import lombok.RequiredArgsConstructor;
import market.service.AIService;
import market.service.util.ModelHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"answer"})
@RequestMapping("/admin/predict")
public class PredictController {
    private final AIService aiService;
    @GetMapping
    public String getPagePredict(Model model) {
        return "/admin/predicts";
    }

    @GetMapping("/query")
    public String getAnswer(Model model, String query){
        String answer = aiService.getMessage(query);
        model.addAttribute("answer", answer);
        return "redirect:/admin/predict";
    }
}

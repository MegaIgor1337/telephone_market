package market.http.controller;

import lombok.RequiredArgsConstructor;
import market.service.CommentService;
import market.util.ModelHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Map;

import static market.util.ConstantContainer.COMMENTS;
import static market.util.ConstantContainer.USER_DTO;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
@SessionAttributes({COMMENTS, USER_DTO})
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public String getComments(Model model) {
        ModelHelper.addAttributes(model, Map.of(COMMENTS, commentService.getAccessedComments()));
        return "/comments";
    }

}

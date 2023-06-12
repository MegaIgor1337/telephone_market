package market.controller;

import lombok.RequiredArgsConstructor;
import market.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import static market.util.StringContainer.COMMENTS;
import static market.util.StringContainer.USER_DTO;

@Controller
@RequiredArgsConstructor
@SessionAttributes({COMMENTS, USER_DTO})
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/comments")
    public String getComments(Model model) {
        model.addAttribute(COMMENTS, commentService.getAccessedComments());
        return "/comments";
    }

}

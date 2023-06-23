package market.controller.rest;

import lombok.RequiredArgsConstructor;
import market.dto.CommentDto;
import market.service.CommentService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class CommentRestController {
    private final CommentService commentService;

    @GetMapping("/comments")
    public List<CommentDto> getComments(Model model) {
        return commentService.getAccessedComments();
    }
}

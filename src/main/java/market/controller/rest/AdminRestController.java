package market.controller.rest;


import lombok.RequiredArgsConstructor;
import market.dto.CommentDto;
import market.service.CommentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminRestController {
    private final CommentService commentService;

    @GetMapping("/menu/moderateComments")
    public List<CommentDto> moderateComments() {
        return  commentService.getModerateComments();
    }

    @GetMapping("/admin/menu/accessedComments")
    public List<CommentDto> accessedComments() {
        return commentService.getAccessedComments();
    }
}

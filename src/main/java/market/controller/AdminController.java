package market.controller;

import lombok.RequiredArgsConstructor;
import market.dto.CommentDto;
import market.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

import static market.util.StringContainer.*;

@Controller
@RequiredArgsConstructor
@SessionAttributes({SIZE_MODERATE_COMMENTS, ACCESSED_COMMENTS, MODERATE_COMMENTS})
public class AdminController {
    private final CommentService commentService;
    @GetMapping("/admin/menu")
    public String adminMenu() {
        return "admin/adminMenu";
    }

    @GetMapping("/admin/menu/moderateComments")
    public String moderateComments(Model model) {
        List<CommentDto> commentsDto = commentService.getModerateComments();
        model.addAttribute(MODERATE_COMMENTS, commentsDto);
        return "admin/moderateComments";
    }

    @PostMapping("/admin/menu/moderateComments")
    public String moderateComments(String commentId, String value, Model model) {
        if (value.equals(ACCESS)) {
            commentService.updateCommentFromModeratingToAccess(Long.valueOf(commentId));
        } else {
            commentService.deleteComment(Long.valueOf(commentId));
        }
        model.addAttribute(SIZE_MODERATE_COMMENTS, commentService.getModerateComments().size());
        return "redirect:/admin/menu/moderateComments";
    }

    @GetMapping("/admin/menu/accessedComments")
    public String accessedComments(Model model) {
        List<CommentDto> commentsDto = commentService.getAccessedComments();
        model.addAttribute(ACCESSED_COMMENTS, commentsDto);
        return "admin/accessedCommentsForAdmin";
    }

    @PostMapping("/admin/menu/accessedComments")
    public String accessedComments(String commentId) {
        commentService.deleteComment(Long.valueOf(commentId));
        return "redirect:/admin/menu/accessedComments";
    }
}

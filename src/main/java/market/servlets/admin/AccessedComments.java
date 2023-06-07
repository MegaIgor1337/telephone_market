package market.servlets.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import market.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import market.service.CommentService;
import org.springframework.stereotype.Component;
import market.util.JspHelper;


import java.io.IOException;
import java.util.List;

@WebServlet("/accessedComments")
@RequiredArgsConstructor
@Component
public class AccessedComments extends HttpServlet {
    private final CommentService commentService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CommentDto> commentsDto = commentService.getAccessedComments();
        req.getSession().setAttribute("accessedComments", commentsDto);
        req.getRequestDispatcher(JspHelper.getPath("accessedCommentsForAdmin"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("commentId"));
        commentService.deleteComment(id);
        doGet(req, resp);
    }
}

package servlets.admin;

import dto.CommentDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CommentService;
import util.JspHelper;

import java.io.IOException;
import java.util.List;

@WebServlet("/moderateComments")
public class ModerateCommentsServlet extends HttpServlet {
    private final CommentService commentService = CommentService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CommentDto> commentsDto = commentService.getModerateComments();
        req.setAttribute("moderateComments", commentsDto);
        req.getRequestDispatcher(JspHelper.getPath("moderateComments"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String value = req.getParameter("button");
        String commentId = req.getParameter("commentId");
        if (value.equals("access")) {
            commentService.updateCommentFromModeratingToAccess(Long.valueOf(commentId));
        } else {
            commentService.updateCommentFromModeratingToDelete(Long.valueOf(commentId));
        }
        req.getSession().setAttribute("sizeModerateComments", commentService.getModerateComments().size());
        req.setAttribute("moderateComments", commentService.getModerateComments());
        req.getRequestDispatcher(JspHelper.getPath("moderateComments"))
                .forward(req, resp);
    }
}

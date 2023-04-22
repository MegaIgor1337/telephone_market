package servlets;

import dto.comment.CommentDto;
import dto.user.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CommentService;
import util.JspHelper;

import java.io.IOException;
import java.util.List;

@WebServlet("/userComments")
public class UserCommentsServlet extends HttpServlet {
    private final CommentService commentService = CommentService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto userDto =  (UserDto)req.getSession().getAttribute("userDto");
        List<CommentDto> userComments = commentService.findCommentsByUserId(
                userDto.getId()
        );
        req.getSession().setAttribute("userComments", userComments);
        req.getRequestDispatcher(JspHelper.getPath("userComments")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("commentId"));
        commentService.deleteComment(id);
        doGet(req, resp);
    }

}

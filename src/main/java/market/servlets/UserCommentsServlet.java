package market.servlets;

import market.dto.CommentDto;
import market.dto.UserDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import market.service.CommentService;
import market.util.JspHelper;

import java.io.IOException;
import java.util.List;

@WebServlet("/userComments")
@Component
@RequiredArgsConstructor
public class UserCommentsServlet extends HttpServlet {
    private final CommentService commentService;
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

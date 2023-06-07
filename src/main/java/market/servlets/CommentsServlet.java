package market.servlets;

import market.dto.CommentDto;
import market.dto.UserDto;
import market.enums.CommentStatus;

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


@WebServlet("/comments")
@Component
@RequiredArgsConstructor
public class CommentsServlet extends HttpServlet {
    private final CommentService commentService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("comments", commentService.getAccessedComments());
        req.getRequestDispatcher(JspHelper.getPath("comments")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String comment = req.getParameter("comment");
        UserDto userDto = (UserDto) req.getSession().getAttribute("userDto");
        CommentDto commentDto = CommentDto.builder()
                .comment(comment)
                .userDto(userDto)
                .commentStatus(CommentStatus.MODERATING)
                .build();
        commentService.save(commentDto);
        resp.sendRedirect("/menu");
    }
}

package market.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import market.dto.UserDto;
import market.enums.Role;
import market.service.CommentService;
import market.service.UserService;
import market.util.JspHelper;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@WebServlet("/login")
@RequiredArgsConstructor
public class LoginServlet extends HttpServlet {
    private final UserService userService;
    private final CommentService commentService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("login"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.login(req.getParameter("name"), req.getParameter("password"))
                .ifPresentOrElse(
                        user -> onLoginSuccess(user, req, resp),
                        () -> onLoginFail(req, resp)
                );
    }

    @SneakyThrows
    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/login?error&email=" + req.getParameter("login"));
    }

    @SneakyThrows
    private void onLoginSuccess(UserDto user, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("userDto", user);
        if (user.getRole().equals(Role.ADMIN)) {
            req.getSession().setAttribute("sizeModerateComments", commentService.getModerateComments().size());
            resp.sendRedirect("/admin");
        } else {
            resp.sendRedirect("/menu");
        }
    }
}

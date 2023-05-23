package servlets.changeParams;

import dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserService;
import util.JspHelper;

import java.io.IOException;


@WebServlet("/changePassword")
public class ChangePasswordServlet extends HttpServlet {
    private final UserService userService = UserService.getINSTANCE();
    private String message;


    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        message = "";
        super.service(req, res);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("messagePassword", message);
        req.getRequestDispatcher(JspHelper.getPath("changePassword"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        message = "";
        String oldPassword = req.getParameter("oldPassword");
        UserDto userDto = (UserDto) req.getSession().getAttribute("userDto");
        if (userService.validateOnPassword(userDto, oldPassword)) {
            String newPassword = req.getParameter("newPassword");
            if (userService.validatePassword(newPassword)) {
                userService.setNewPassword(userDto, newPassword);
                req.getSession().setAttribute("userDto", userDto);
                resp.sendRedirect("/profileMenu");
            } else {
                message = "This password is too simple. " + "Password is simple." +
                          " The password must be at least 8 characters " +
                          "long, contain at least 1 uppercase letter, 1 lowercase letter," +
                          " and EITHER a special character OR a number.";
                doGet(req, resp);
            }
        } else {
            message = "Old password is wrong";
            doGet(req, resp);
        }
    }
}

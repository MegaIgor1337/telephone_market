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

@WebServlet("/changeEmail")
public class ChangeEmailServlet extends HttpServlet {
    private final UserService userService = UserService.getINSTANCE();
    private String message;



    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        message = "";
        super.service(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("messageEmail", message);
        req.getRequestDispatcher(JspHelper.getPath("changeEmail"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        message = "";
        String password = req.getParameter("password");
        UserDto userDto = (UserDto) req.getSession().getAttribute("userDto");
        if (userService.validateOnPassword(userDto, password)) {
            String newEmail = req.getParameter("newEmail");
            if (userService.validateEmail(newEmail)) {
                userService.setNewEmail(userDto, newEmail);
                req.getSession().setAttribute("userDto", userDto);
                resp.sendRedirect("/profileMenu");
            } else {
                message = "This email is wrong";
                doGet(req, resp);
            }
        } else {
            message = "Password is wrong";
            doGet(req, resp);
        }
    }


}

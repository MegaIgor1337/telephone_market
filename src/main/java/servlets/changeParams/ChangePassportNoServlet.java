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

@WebServlet("/changePassportNo")
public class ChangePassportNoServlet extends HttpServlet {
    private final UserService userService = UserService.getINSTANCE();
    private String message;



    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        message = "";
        super.service(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("messagePassportNo", message);
        req.getRequestDispatcher(JspHelper.getPath("changePassportNo"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        message = "";
        String password = req.getParameter("password");
        UserDto userDto = (UserDto) req.getSession().getAttribute("userDto");
        if (userService.validateOnPassword(userDto, password)) {
            String newPassportNo = req.getParameter("newPassportNo");
            if (userService.validatePassportNo(newPassportNo)) {
                userService.setNewPassportNo(userDto, newPassportNo);
                req.getSession().setAttribute("userDto", userDto);
                resp.sendRedirect("/profileMenu");
            } else {
                message = "This passport number is wrong";
                doGet(req, resp);
            }
        } else {
            message = "Password is wrong";
            doGet(req, resp);
        }
    }
}

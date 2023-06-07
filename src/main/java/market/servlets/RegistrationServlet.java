package market.servlets;

import market.converter.ConvertUserDto;
import market.enums.Gender;
import market.enums.Role;
import market.exception.ValidationException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import market.service.UserService;
import market.util.JspHelper;

import java.io.IOException;

@WebServlet("/registration")
@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationServlet extends HttpServlet {
    private final UserService userService;
    private final ConvertUserDto convertUserDto;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", Role.values());
        req.setAttribute("genders", Gender.values());
        req.getRequestDispatcher(JspHelper.getPath("registration"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var createUserDto = convertUserDto.convert(req);
        try {
            var userDto = userService.create(createUserDto);
            req.getSession().setAttribute("userDto", userDto);
            log.info("User logged: {}", userDto);
            resp.sendRedirect("/address");
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}

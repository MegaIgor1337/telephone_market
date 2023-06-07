package market.servlets;

import market.dto.AddressDto;
import market.dto.UserDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import market.service.AddressService;
import market.util.JspHelper;

import java.io.IOException;
import java.util.List;


@WebServlet("/profileMenu")
@Component
@RequiredArgsConstructor
public class ProfileMenuServlet extends HttpServlet {
    private final AddressService addressService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userDto = (UserDto) req.getSession().getAttribute("userDto");
        List<AddressDto> addressDtoList = addressService.getAddresses(userDto.getId());
        req.getSession().setAttribute("addresses", addressDtoList);
        req.getSession().setAttribute("userDro", userDto);
        req.getSession().setAttribute("userId", userDto.getId());
        req.getRequestDispatcher(JspHelper.getPath("profileMenu"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}

package servlets;

import dto.address.AddressDto;
import dto.user.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AddressService;
import util.JspHelper;

import java.io.IOException;
import java.util.List;


@WebServlet("/profileMenu")
public class ProfileMenuServlet extends HttpServlet {
    private final AddressService addressService = AddressService.getInstance();
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

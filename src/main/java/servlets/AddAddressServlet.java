package servlets;

import converter.ConvertAddressDto;
import dto.address.CreateAddressDto;
import dto.user.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AddressService;
import util.JspHelper;

import java.io.IOException;

@WebServlet("/address")
public class AddAddressServlet extends HttpServlet {
    private final AddressService addressService = AddressService.getInstance();
    private final ConvertAddressDto convertAddressDto = ConvertAddressDto.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("address"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            UserDto userDto = (UserDto) req.getSession().getAttribute("userDto");
            req.getSession().setAttribute("addressId", 0L);
            req.getSession().setAttribute("userId", userDto.getId());
            CreateAddressDto createAddressDto = convertAddressDto.convert(req);
            addressService.save(createAddressDto);
            resp.sendRedirect("/menu");
    }
}

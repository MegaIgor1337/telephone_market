package market.servlets;

import market.converter.ConvertAddressDto;
import market.dto.CreateAddressDto;
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

@WebServlet("/address")
@Component
@RequiredArgsConstructor
public class AddAddressServlet extends HttpServlet {
    private final AddressService addressService;
    private final ConvertAddressDto convertAddressDto;
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

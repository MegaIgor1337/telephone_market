package market.servlets.changeParams;

import market.converter.ConvertAddressDto;
import market.dto.CreateAddressDto;

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

@WebServlet("/changeAddress")
@Component
@RequiredArgsConstructor
public class ChangeAddressServlet extends HttpServlet {
    private final ConvertAddressDto convertAddressDto;
    private final AddressService addressService;
    private long addressId;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addressId = Long.valueOf(req.getParameter("addressId"));
        req.getRequestDispatcher(JspHelper.getPath("changeAddress")).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("addressId", addressId);
        CreateAddressDto createAddressDto = convertAddressDto.convert(req);
        addressService.update(createAddressDto);
        resp.sendRedirect("/profileMenu");
    }
}

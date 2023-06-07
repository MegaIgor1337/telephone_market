package market.servlets.changeParams;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import market.service.AddressService;

import java.io.IOException;

@WebServlet("/deleteAddress")
@Component
@RequiredArgsConstructor
public class DeleteAddressServlet extends HttpServlet {
    private final AddressService addressService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/profileMenu");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long addressId = Long.valueOf(req.getParameter("addressId"));
        addressService.delete(addressId);
        doGet(req, resp);
    }
}

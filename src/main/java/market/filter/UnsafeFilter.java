package market.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import market.dto.UserDto;
import market.enums.Role;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/admin")
public class UnsafeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        UserDto user = (UserDto) ((HttpServletRequest) servletRequest).getSession()
                .getAttribute("userDto");
        if (user != null && user.getRole().equals(Role.ADMIN)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse)servletResponse).sendRedirect("/registration");
        }
    }
}
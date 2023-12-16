package market.view.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;

@Slf4j
@ControllerAdvice(basePackages = "market.http.controller")
public class ControllerExceptionHandler implements AccessDeniedHandler {
    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception, HttpServletRequest request) {
        log.error("Failed to return response", exception);
        return "error/error500";
    }

    @ExceptionHandler(IOException.class)
    public String handleIoException(IOException exception, HttpServletRequest request) {
        log.error("Failed to return response", exception);
        return "error/error500";
    }

    @Override
    @ExceptionHandler(AccessDeniedException.class)
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        log.info("403 error");
        response.sendRedirect("/error/error403");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundHandler(NoHandlerFoundException ex) {
        log.info("404 error");
        return "/error/error404";
    }
}

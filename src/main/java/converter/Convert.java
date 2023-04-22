package converter;

import jakarta.servlet.http.HttpServletRequest;

public interface Convert<T> {
    T convert(HttpServletRequest httpServletRequest);
}

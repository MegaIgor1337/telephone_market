package market.converter;

import jakarta.servlet.http.HttpServletRequest;
import market.dto.CreateUserDto;
import org.springframework.stereotype.Component;


@Component
public class ConvertUserDto implements Convert<CreateUserDto>{


    @Override
    public CreateUserDto convert(HttpServletRequest req) {
        return CreateUserDto.builder()
                .name(req.getParameter("name"))
                .password(req.getParameter("password"))
                .passportNo(req.getParameter("passportNo"))
                .email(req.getParameter("email"))
                .role(req.getParameter("role"))
                .gender(req.getParameter("gender"))
                .build();
    }


}

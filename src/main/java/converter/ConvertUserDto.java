package converter;

import dto.user.CreateUserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ConvertUserDto implements Convert<CreateUserDto>{

    private final static ConvertUserDto INSTANCE = new ConvertUserDto();
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

    public static ConvertUserDto getInstance() {
        return INSTANCE;
    }
}

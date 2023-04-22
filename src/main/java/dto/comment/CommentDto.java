package dto.comment;

import dto.user.UserDto;
import entity.user.User;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CommentDto {
    Long id;
    String comment;
    UserDto userDto;
}

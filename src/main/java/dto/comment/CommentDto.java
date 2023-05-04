package dto.comment;

import dto.user.UserDto;
import entity.comment.CommentStatus;
import entity.user.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private String comment;
    private UserDto userDto;
    private CommentStatus commentStatus;
}

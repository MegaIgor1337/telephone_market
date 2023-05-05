package dto;

import entity.enums.CommentStatus;
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

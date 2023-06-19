package market.dto;

import market.enums.CommentStatus;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private String comment;
    private UserDto user;
    private CommentStatus status;
}

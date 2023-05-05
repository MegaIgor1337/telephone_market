package dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCommentDto {
    private String id;
    private String comment;
    private String userId;
    private String commentStatus;
}

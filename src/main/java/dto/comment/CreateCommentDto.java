package dto.comment;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateCommentDto {
    String id;
    String comment;
    String userId;
}

package entity.comment;


import entity.user.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    Long id;
    String comment;
    User user;
}

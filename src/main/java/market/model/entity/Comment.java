package market.model.entity;


import market.model.enums.CommentStatus;
import lombok.*;

import jakarta.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comments", schema = "public")
public class Comment implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private CommentStatus status;

}

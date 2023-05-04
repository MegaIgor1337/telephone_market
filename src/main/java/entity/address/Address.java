package entity.address;

import entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    private String city;
    private String street;
    private String house;
    private String flat;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

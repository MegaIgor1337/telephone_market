package market.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Address implements BaseEntity<Long> {
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

    @ToString.Exclude
    @OneToMany(mappedBy = "address")
    private List<Order> orders;
}

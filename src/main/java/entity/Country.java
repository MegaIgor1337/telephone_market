package entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Country implements BaseEntity<Long> {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

}

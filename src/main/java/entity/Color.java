package entity;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Colors")
@Entity
public class Color implements BaseEntity<Long> {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String color;
    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

}

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
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Brands")

@Entity
public class Brand implements BaseEntity<Long> {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();
}

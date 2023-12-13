package market.model.entity;

import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.Cache;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Brands")
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

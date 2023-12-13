package market.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.Cache;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Countries")
public class Country implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

}

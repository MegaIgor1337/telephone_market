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
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Models")
public class Model implements BaseEntity<Long> {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    @Builder.Default
    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Product> products = new ArrayList<>();


}

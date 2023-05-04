package entity.model;

import entity.product.Product;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Model {
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

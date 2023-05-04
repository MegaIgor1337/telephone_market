package entity.color;

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
public class Color {
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

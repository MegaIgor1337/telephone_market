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
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Countries")
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

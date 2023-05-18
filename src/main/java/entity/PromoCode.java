package entity;


import entity.enums.PromoCodeStatus;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "PromoCodes")
@Table(name = "promo_code", schema = "public")
public class PromoCode implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double discount;
    private String name;
    @Enumerated(EnumType.STRING)
    private PromoCodeStatus status;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<PromoCodeProduct> products = new ArrayList<>();
}

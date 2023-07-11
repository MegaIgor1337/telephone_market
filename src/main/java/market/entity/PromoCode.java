package market.entity;


import market.enums.PromoCodeStatus;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.Cache;
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "PromoCodes")
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
    @OneToMany(mappedBy = "promoCode", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PromoCodeProduct> products = new ArrayList<>();
}

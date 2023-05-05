package entity;


import entity.enums.PromoCodeStatus;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "promo_code", schema = "public")
public class PromoCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double discount;
    @Enumerated(EnumType.STRING)
    private PromoCodeStatus status;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<PromoCodeProduct> products = new ArrayList<>();
}

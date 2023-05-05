package entity;

import entity.Product;
import entity.PromoCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "promo_code_product", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromoCodeProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promo_code_id")
    private PromoCode promoCode;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "date_of_begin")
    private Timestamp dateOfBegin;
    @Column(name = "date_od_end")
    private Timestamp dateOfEnd;
}

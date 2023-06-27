package market.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import market.entity.PromoCodeProduct;
import market.enums.PromoCodeStatus;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromoCodeDtoWithPage {
    private Long id;
    private String name;
    private Double discount;
    private PromoCodeStatus status;
    private Page<PromoCodeProduct> products;
}

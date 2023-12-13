package market.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import market.model.entity.PromoCodeProduct;
import market.model.enums.PromoCodeStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromoCodeDto {
    private Long id;
    private String name;
    private Double discount;
    private PromoCodeStatus status;
    private List<PromoCodeProduct> products;

}

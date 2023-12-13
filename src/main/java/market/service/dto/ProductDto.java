package market.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private BrandDto brand;
    private ModelDto model;
    private ColorDto color;
    private CountryDto country;
    private Integer storageCount;
    private BigDecimal cost;
}

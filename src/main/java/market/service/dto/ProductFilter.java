package market.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductFilter {
    private String brand;
    private String model;
    private String color;
    private String country;
    private String count;
    private String minPrice;
    private String priceQuery;
    private String maxPrice;
}

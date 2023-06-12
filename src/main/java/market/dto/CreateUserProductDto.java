package market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateUserProductDto {
    private String brand;
    private String model;
    private String color;
    private String country;
    private String count;
    private String minPrice;
    private String maxPrice;
}

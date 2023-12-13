package market.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductDto {
    private String brand;
    private String model;
    private String color;
    private String country;
    private Double cost;
    private Integer count;}

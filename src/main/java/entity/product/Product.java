package entity.product;

import entity.brand.Brand;
import entity.color.Color;
import entity.country.Country;
import entity.model.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private Long id;
    private Brand brand;
    private Model model;
    private Color color;
    private Country country;
    private int storageCount;
    private BigDecimal cost;


}

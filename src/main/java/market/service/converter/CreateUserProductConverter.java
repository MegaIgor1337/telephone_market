package market.service.converter;

import market.service.dto.ProductFilter;
import org.springframework.stereotype.Component;

@Component
public class CreateUserProductConverter {
    public ProductFilter convert(String brand, String model, String color,
                                 String country, String maxPrice, String minPrice,
                                 String count, String priceQuery) {
        return  ProductFilter.builder()
                .brand(brand)
                .model(model)
                .color(color)
                .country(country)
                .maxPrice(maxPrice)
                .minPrice(minPrice)
                .count(count)
                .priceQuery(priceQuery)
                .build();
    }
}

package market.converter;

import market.dto.CreateUserProductDto;
import org.springframework.stereotype.Component;

@Component
public class CreateUserProductConverter {
    public CreateUserProductDto convert(String brand, String model, String color,
                                        String country, String maxPrice, String minPrice,
                                        String count) {
        return  CreateUserProductDto.builder()
                .brand(brand)
                .model(model)
                .color(color)
                .country(country)
                .maxPrice(maxPrice)
                .minPrice(minPrice)
                .count(count)
                .build();
    }
}

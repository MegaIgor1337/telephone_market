package market.validator;

import market.dto.CreateProductDto;
import market.dto.ValidateProductDto;
import market.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;

import static market.util.StringContainer.*;
@Component
public class CreateProductValidator implements Validator<CreateProductDto> {
    private List<Product> products;

    public void putProducts(List<Product> products) {
        this.products = products;
    }


    @Override
    public ValidationResult isValid(CreateProductDto object) {
        var validationResult = new ValidationResult();
        if (products.stream().map(p -> ValidateProductDto.builder()
                .brand(p.getBrand().getBrand())
                .model(p.getModel().getModel())
                .country(p.getCountry().getCountry())
                .color(p.getColor().getColor()).build()).toList()
                .contains(ValidateProductDto.builder()
                        .country(object.getCountry())
                        .model(object.getModel())
                        .color(object.getColor())
                        .brand(object.getBrand()).build())) {
            validationResult.add(Error.of(Error.getMessage(PRODUCT), PRODUCT_ALREADY_EXISTS));
        }
        if (object.getCount() < 0) {
            validationResult.add(Error.of(Error.getMessage(COUNT), COUNT_ENTERED_INVALID));
        }
        if (object.getCost() < 0) {
            validationResult.add(Error.of(Error.getMessage(COST), COST_INVALID));
        }
        return validationResult;
    }


}

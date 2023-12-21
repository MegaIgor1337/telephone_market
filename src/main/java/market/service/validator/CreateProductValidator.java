package market.service.validator;

import market.model.entity.Product;
import market.service.dto.CreateProductDto;
import market.service.dto.ValidateProductDto;
import org.springframework.stereotype.Component;

import java.util.List;

import static market.service.util.ConstantContainer.*;

@Component
public class CreateProductValidator implements Validator<CreateProductDto> {
    private List<Product> products;

    public void putProducts(List<Product> products) {
        this.products = products;
    }


    @Override
    public ValidationResult isValid(CreateProductDto object) {
        var validationResult = new ValidationResult();
        checkProduct(object, validationResult);
        if (object.getCount() < 0) {
            validationResult.add(Error.of(Error.getMessage(COUNT), COUNT_ENTERED_INVALID));
        }
        if (object.getCost() < 0) {
            validationResult.add(Error.of(Error.getMessage(COST), COST_INVALID));
        }
        return validationResult;
    }

    public ValidationResult isValid(List<CreateProductDto> objects) {
        var validationResult = new ValidationResult();
        for (CreateProductDto object : objects) {
            checkProduct(object, validationResult);
        }
        for (CreateProductDto object : objects) {
            if (object.getCount() < 0) {
                validationResult.add(Error.of(Error.getMessage(COUNT), COUNT_ENTERED_INVALID));
            }
        }
        for (CreateProductDto object : objects) {
            if (object.getCost() < 0) {
                validationResult.add(Error.of(Error.getMessage(COST), COST_INVALID));
            }
        }
        return validationResult;
    }

    private void checkProduct(CreateProductDto object, ValidationResult validationResult) {
        if (products.stream().map(p -> ValidateProductDto.builder()
                        .brand(p.getBrand().getBrand())
                        .model(p.getModel().getModel())
                        .country(p.getCountry().getCountry())
                        .color(p.getColor().getColor()).build()).toList()
                .contains(ValidateProductDto.builder()
                        .country(object.getCountry())
                        .model(object.getModel())
                        .color(object.getColor())
                        .brand(object.getBrand()).build(

                        ))) {
            validationResult.add(Error.of(Error.getMessage(PRODUCT), PRODUCT_ALREADY_EXISTS));
        }
    }
}

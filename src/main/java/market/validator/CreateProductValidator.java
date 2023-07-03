package market.validator;

import market.dto.CreateProductDto;
import market.entity.Brand;
import market.entity.Color;
import market.entity.Country;
import market.entity.Model;
import org.springframework.stereotype.Component;

import java.util.List;

import static market.util.StringContainer.*;
@Component
public class CreateProductValidator implements Validator<CreateProductDto> {
    private List<Brand> brands;
    private List<Model> models;
    private List<Color> colors;
    private List<Country> countries;

    public void putBrands(List<Brand> brands) {
        this.brands = brands;
    }

    public void putModels(List<Model> models) {
        this.models = models;
    }

    public void putColors(List<Color> colors) {
        this.colors = colors;
    }

    public void putCountries(List<Country> countries) {
        this.countries = countries;
    }

    @Override
    public ValidationResult isValid(CreateProductDto object) {
        var validationResult = new ValidationResult();
        if (brands.stream().map(Brand::getBrand).map(String::toLowerCase)
                    .toList().contains(object.getBrand().toLowerCase()) &&
            models.stream().map(Model::getModel).map(String::toLowerCase)
                    .toList().contains(object.getModel().toLowerCase()) &&
            colors.stream().map(Color::getColor).map(String::toLowerCase)
                    .toList().contains(object.getColor().toLowerCase()) &&
            countries.stream().map(Country::getCountry).map(String::toLowerCase)
                    .toList().contains(object.getCountry().toLowerCase())) {
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

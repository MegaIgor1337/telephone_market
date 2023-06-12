package market.validator;

import lombok.NoArgsConstructor;
import market.dto.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static market.util.StringContainer.*;

@Component
@NoArgsConstructor
public class EnteredProductInfoValidator implements Validator<CreateUserProductDto> {
    private List<BrandDto> brands;
    private List<ModelDto> models;
    private List<CountryDto> countries;
    private List<ColorDto> colors;

    @Override
    public ValidationResult isValid(CreateUserProductDto object) {
        var validationResult = new ValidationResult();
        if (object.getColor() != null || object.getCount() != null || object.getModel() != null
        || object.getBrand() != null || object.getCountry() != null) {
            if (!EMPTY_PARAM.equals(object.getBrand()) && !brands.stream().map(BrandDto::getBrand).toList()
                    .contains(object.getBrand())) {
                validationResult.add(Error.of(Error.getMessage(BRAND), BRAND_ENTERED_INVALID));
            }
            if (!models.stream().map(ModelDto::getModel).toList()
                    .contains(object.getModel()) && !EMPTY_PARAM.equals(object.getModel())) {
                validationResult.add(Error.of(Error.getMessage(MODEL), MODEL_ENTERED_INVALID));
            }
            if (!colors.stream().map(ColorDto::getColor).toList()
                    .contains(object.getColor()) && !EMPTY_PARAM.equals(object.getColor())) {
                validationResult.add(Error.of(Error.getMessage(COLOR), COLOR_ENTERED_INVALID));
            }
            if (!countries.stream().map(CountryDto::getCountry).toList()
                    .contains(object.getCountry()) && !EMPTY_PARAM.equals(object.getCountry())) {
                validationResult.add(Error.of(Error.getMessage(COUNTRY), COUNTRY_ENTERED_INVALID));
            }
            if (!EMPTY_PARAM.equals(object.getCount()) && Integer.parseInt(object.getCount()) < 1) {
                validationResult.add(Error.of(Error.getMessage(COUNT), COUNT_ENTERED_INVALID));
            }
        }
        if (object.getMaxPrice() != null) {
            var maxPrice = !EMPTY_PARAM.equals(object.getMaxPrice()) ? new BigDecimal(object.getMaxPrice()) : null;
            if (maxPrice != null && maxPrice.compareTo(BigDecimal.ZERO) < 0) {
                validationResult.add(Error.of(Error.getMessage(MONEY), MONEY_LESS_ENTERED_INVALID));
            }
        }
        if (object.getMinPrice() != null) {
            var minPrice = !EMPTY_PARAM.equals(object.getMinPrice()) ? new BigDecimal(object.getMinPrice()) : null;
            if (minPrice != null && minPrice.compareTo(BigDecimal.ZERO) < 0) {
                validationResult.add(Error.of(Error.getMessage(MONEY), MONEY_LESS_ENTERED_INVALID));
            }
        }
        return validationResult;
    }

    public void putBrands(List<BrandDto> brands) {
        this.brands = brands;
    }

    public void putColors(List<ColorDto> colors) {
        this.colors = colors;
    }

    public void putModels(List<ModelDto> models) {
        this.models = models;
    }

    public void putCountries(List<CountryDto> countries) {
        this.countries = countries;
    }

}

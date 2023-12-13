package market.service.mapper;

import market.service.dto.CountryDto;
import market.model.entity.Country;
import org.mapstruct.Mapper;

import static market.service.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING)
public interface CountryMapper {
    CountryDto countryToCountryDto(Country country);
    Country countryDtoToCountry(CountryDto countryDto);
}

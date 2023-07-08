package market.mapper;

import market.dto.CountryDto;
import market.entity.Country;
import org.mapstruct.Mapper;

import static market.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING)
public interface CountryMapper {
    CountryDto countryToCountryDto(Country country);
    Country countryDtoToCountry(CountryDto countryDto);
}

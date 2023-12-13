package market.service;

import market.service.dto.CountryDto;

import java.util.List;

public interface CountryService {
    List<CountryDto> getAllCountries();
}

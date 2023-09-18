package market.service;

import market.dto.CountryDto;

import java.util.List;

public interface CountryService {
    List<CountryDto> getAllCountries();
}

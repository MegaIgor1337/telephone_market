package market.service.impl;

import lombok.RequiredArgsConstructor;
import market.service.dto.CountryDto;
import market.service.mapper.CountryMapper;
import market.model.repository.CountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CountryServiceImpl implements market.service.CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Override
    public List<CountryDto> getAllCountries() {
        return countryRepository.findAll().stream()
                .map(countryMapper::countryToCountryDto).toList();
    }
}

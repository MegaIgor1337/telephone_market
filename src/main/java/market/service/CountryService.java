package market.service;

import lombok.RequiredArgsConstructor;
import market.dto.CountryDto;
import market.mapper.CountryMapper;
import market.repository.CountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    public List<CountryDto> getAllCountries() {
        return countryRepository.findAll().stream()
                .map(countryMapper::countryToCountryDto).toList();
    }
}

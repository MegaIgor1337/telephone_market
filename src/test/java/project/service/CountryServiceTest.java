package project.service;


import lombok.RequiredArgsConstructor;
import market.service.CountryService;
import market.service.impl.CountryServiceImpl;
import org.junit.jupiter.api.Test;
import project.annotation.IT;

import static org.assertj.core.api.Assertions.assertThat;


@IT
@RequiredArgsConstructor
public class CountryServiceTest {
    private final CountryService countryService;
    @Test
    void getCountries() {
        var result = countryService.getAllCountries();
        assertThat(result).hasSize(3);
    }
}

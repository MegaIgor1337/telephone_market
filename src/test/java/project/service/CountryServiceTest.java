package project.service;


import lombok.RequiredArgsConstructor;
import market.service.impl.CountryServiceImpl;
import org.junit.jupiter.api.Test;
import project.annotation.IT;

import static org.assertj.core.api.Assertions.assertThat;
@RequiredArgsConstructor
@IT
public class CountryServiceTest {
    private final CountryServiceImpl countryService;
    @Test
    void getCountries() {
        var result = countryService.getAllCountries();
        assertThat(result).hasSize(3);
    }
}

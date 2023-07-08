package project.service;


import lombok.RequiredArgsConstructor;
import market.service.CountryService;
import org.junit.jupiter.api.Test;
import project.annotation.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

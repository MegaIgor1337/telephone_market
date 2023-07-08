package project.service;


import lombok.RequiredArgsConstructor;
import market.service.BrandService;
import org.junit.jupiter.api.Test;
import project.IntegrationTestBase;
import project.annotation.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;
@RequiredArgsConstructor
public class BrandServiceTest extends IntegrationTestBase {
    private final BrandService brandService;
    @Test
    void gerBrands() {
        var result = brandService.getAllBrands();
        assertThat(result).hasSize(5);
    }
}

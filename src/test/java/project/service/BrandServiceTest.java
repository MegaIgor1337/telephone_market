package project.service;


import lombok.RequiredArgsConstructor;
import market.service.BrandService;
import market.service.impl.BrandServiceImpl;
import org.junit.jupiter.api.Test;
import project.annotation.IT;

import static org.assertj.core.api.Assertions.assertThat;
@IT
@RequiredArgsConstructor
public class BrandServiceTest  {
    private final BrandService brandService;
    @Test
    void gerBrands() {
        var result = brandService.getAllBrands();
        assertThat(result).hasSize(5);
    }
}

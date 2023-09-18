package project.service;


import lombok.RequiredArgsConstructor;
import market.service.impl.ColorServiceImpl;
import org.junit.jupiter.api.Test;
import project.annotation.IT;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@IT
public class ColorServiceTest  {
    private final ColorServiceImpl colorService;
    @Test
    void getColors() {
        var result = colorService.getAllColors();
        assertThat(result).hasSize(4);
    }
}

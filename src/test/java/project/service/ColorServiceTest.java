package project.service;


import lombok.RequiredArgsConstructor;
import market.service.ColorService;
import org.junit.jupiter.api.Test;
import project.annotation.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;
@IT
@RequiredArgsConstructor
public class ColorServiceTest {
    private final ColorService colorService;
    @Test
    void getColors() {
        var result = colorService.getAllColors();
        assertThat(result).hasSize(4);
    }
}

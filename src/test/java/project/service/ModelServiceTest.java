package project.service;


import lombok.RequiredArgsConstructor;
import market.service.ModelService;
import org.junit.jupiter.api.Test;
import project.annotation.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;
@IT
@RequiredArgsConstructor
public class ModelServiceTest {
    private final ModelService modelService;

    @Test
    void getAllModels() {
        var result = modelService.getAllModels();
        assertThat(result).hasSize(5);
    }

}

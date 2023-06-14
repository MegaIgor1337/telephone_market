package project.service;


import lombok.RequiredArgsConstructor;
import market.ApplicationRunner;
import market.service.ColorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.TestApplicationRunner;
import project.annotation.IT;

import static java.util.stream.Collectors.toList;
import static market.util.StringContainer.ID;
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

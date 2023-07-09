package project.controller;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.annotation.IT;


@RequiredArgsConstructor
@AutoConfigureMockMvc
@IT
public class MarketControllerTest {
    private final MockMvc mockMvc;

    @Test
    void market() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/market"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.view().name("/market"));
    }
}

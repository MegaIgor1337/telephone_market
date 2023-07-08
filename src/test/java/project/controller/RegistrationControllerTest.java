package project.controller;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.IntegrationTestBase;
import project.annotation.IT;

@RequiredArgsConstructor
@AutoConfigureMockMvc
public class RegistrationControllerTest extends IntegrationTestBase {
    private final MockMvc mockMvc;

    @Test
    void registration() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/registration"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.view().name("/registration"));
    }
}

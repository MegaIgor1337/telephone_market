package project.controller;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.annotation.IT;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IT
@RequiredArgsConstructor
@AutoConfigureMockMvc
public class LogoutControllerTest {
    private final MockMvc mockMvc;

    @Test
    void logout() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/logout"))
                .andExpect(status().is2xxSuccessful());
    }
}

package project.controller;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import project.IntegrationTestBase;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RequiredArgsConstructor
@AutoConfigureMockMvc
@WithMockUser(username = "Gennadiy22", password = "test", authorities = {"ADMIN", "USER"})
public class AdminControllerTest extends IntegrationTestBase {
    private final MockMvc mockMvc;

    @Test
    void adminMenu() throws Exception {
        mockMvc.perform(get("/admin/menu"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adminMenu"));
    }

    @Test
    void delete() throws Exception {
        String commentId = "1";
        mockMvc.perform(post("/admin/menu/accessedComments")
                        .param("commentId", commentId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/menu/accessedComments"));
    }
}

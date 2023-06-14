package project.controller;


import lombok.RequiredArgsConstructor;
import market.dto.CommentDto;
import market.enums.CommentStatus;
import market.mapper.CommentMapper;
import market.repository.CommentRepository;
import market.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import project.annotation.IT;

import java.util.Arrays;
import java.util.List;

import static market.util.StringContainer.MODERATE_COMMENTS;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IT
@RequiredArgsConstructor
@AutoConfigureMockMvc
public class AdminControllerTest {
    private final MockMvc mockMvc;
    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Test
    void adminMenu() throws Exception {
        mockMvc.perform(get("/admin/menu"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/adminMenu"));
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

package project.controller;


import lombok.RequiredArgsConstructor;
import market.service.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.annotation.IT;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RequiredArgsConstructor
@AutoConfigureMockMvc
@IT
@WithMockUser(username = "test@gmail.com", password = "test", authorities = {"ADMIN", "USER"})
public class AddressControllerTest  {
    private final MockMvc mockMvc;

    @Test
    void testAddress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/address/addAddress"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/address"));
    }

    @Test
    void testAddAddress() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setId(1L); // Задайте значение идентификатора пользователя
        mockMvc.perform(MockMvcRequestBuilders.post("/address/addAddress")
                        .param("country", "TestCountry")
                        .param("city", "TestCity")
                        .param("street", "TestStreet")
                        .param("house", "TestHouse")
                        .param("flat", "TestFlat")
                        .sessionAttr("userDto", userDto)) // Установка атрибута в сессии
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    void changeAddress() throws Exception {
        String addressId = "5";
        mockMvc.perform(MockMvcRequestBuilders.get("/address/changeAddress")
                        .param("addressId", addressId)
                        .param("pageA", "0"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/changeAddress"))
                .andExpect(MockMvcResultMatchers.model().attribute("userDto", addressId));
    }


    @Test
    public void testChangeAddress() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(2L); //
        mockMvc.perform(MockMvcRequestBuilders.post("/address/changeAddress")
                        .param("country", "Country")
                        .param("city", "City")
                        .param("street", "Street")
                        .param("house", "House")
                        .param("flat", "Flat")
                        .sessionAttr("userDto", userDto)
                        .sessionAttr("addressId", "2")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    void delete() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/address/deleteAddress")
                        .param("addressId", "2")
                        .param("pageA", "1")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }
}

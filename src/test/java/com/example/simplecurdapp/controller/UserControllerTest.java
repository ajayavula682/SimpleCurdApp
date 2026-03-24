package com.example.simplecurdapp.controller;

import com.example.simplecurdapp.exception.ResourceNotFoundException;
import com.example.simplecurdapp.model.User;
import com.example.simplecurdapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void getAllUsersShouldReturnOkAndList() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(createUser(1L, "user1@example.com")));

        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].email").value("user1@example.com"));
    }

    @Test
    void getUserByEmailShouldReturnOkWhenFound() throws Exception {
        when(userService.findByEmail("user2@example.com")).thenReturn(Optional.of(createUser(2L, "user2@example.com")));

        mockMvc.perform(get("/api/users/email/user2@example.com"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.email").value("user2@example.com"));
    }

    @Test
    void getUserByEmailShouldReturnNotFoundWhenMissing() throws Exception {
        when(userService.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/email/missing@example.com"))
            .andExpect(status().isNotFound());
    }

    @Test
    void getUserByIdShouldReturnNotFoundWhenServiceThrows() throws Exception {
        doThrow(new ResourceNotFoundException("User", "id", 99L)).when(userService).getUserById(99L);

        mockMvc.perform(get("/api/users/99"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void createUserShouldReturnCreated() throws Exception {
        User created = createUser(3L, "new@example.com");
        when(userService.createUser(any(User.class))).thenReturn(created);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUser(null, "new@example.com"))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.email").value("new@example.com"));
    }

    @Test
    void deactivateUserShouldReturnOkAndUpdatedUser() throws Exception {
        User deactivated = createUser(5L, "active@example.com");
        deactivated.setIsActive(false);
        when(userService.deactivateUser(5L)).thenReturn(deactivated);

        mockMvc.perform(patch("/api/users/5/deactivate"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(5))
            .andExpect(jsonPath("$.isActive").value(false));
    }

    private User createUser(Long id, String email) {
        User user = new User();
        user.setId(id);
        user.setName("Test User");
        user.setEmail(email);
        user.setPhone("1234567890");
        user.setAddress("Address");
        user.setIsActive(true);
        return user;
    }
}

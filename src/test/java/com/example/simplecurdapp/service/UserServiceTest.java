package com.example.simplecurdapp.service;

import com.example.simplecurdapp.exception.DuplicateResourceException;
import com.example.simplecurdapp.exception.ResourceNotFoundException;
import com.example.simplecurdapp.model.User;
import com.example.simplecurdapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private UserService userService;

    @Test
    void getUserDataShouldReturnCachedValueWhenPresent() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        User cached = createUser(1L, "cache@example.com");
        when(valueOperations.get("User_1")).thenReturn(cached);

        User actual = userService.getUserData(1L);

        assertEquals(1L, actual.getId());
        verify(userRepository, never()).findById(1L);
    }

    @Test
    void getUserDataShouldFetchAndCacheWhenMissingInRedis() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        User dbUser = createUser(2L, "db@example.com");
        when(valueOperations.get("User_2")).thenReturn(null);
        when(userRepository.findById(2L)).thenReturn(Optional.of(dbUser));

        User actual = userService.getUserData(2L);

        assertEquals(2L, actual.getId());
        verify(valueOperations).set("User_2", dbUser);
    }

    @Test
    void getUserDataShouldReturnNullWhenNotFoundInCacheOrDb() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("User_3")).thenReturn(null);
        when(userRepository.findById(3L)).thenReturn(Optional.empty());

        User actual = userService.getUserData(3L);

        assertNull(actual);
    }

    @Test
    void getUserByIdShouldThrowWhenMissing() {
        when(userRepository.findById(77L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(77L));
    }

    @Test
    void createUserShouldThrowWhenEmailExists() {
        User input = createUser(null, "duplicate@example.com");
        when(userRepository.existsByEmail("duplicate@example.com")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> userService.createUser(input));
    }

    @Test
    void createUserShouldSaveAndCacheWhenEmailIsUnique() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        User input = createUser(null, "new@example.com");
        User saved = createUser(10L, "new@example.com");
        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(userRepository.save(input)).thenReturn(saved);

        User actual = userService.createUser(input);

        assertEquals(10L, actual.getId());
        verify(valueOperations).set("User_10", saved);
    }

    @Test
    void updateUserShouldThrowWhenEmailChangedToExistingOne() {
        User existing = createUser(5L, "old@example.com");
        User updates = createUser(null, "taken@example.com");
        when(userRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(userRepository.existsByEmail("taken@example.com")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> userService.updateUser(5L, updates));
    }

    @Test
    void updateUserShouldPersistChangesAndRefreshCache() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        User existing = createUser(6L, "same@example.com");
        User updates = createUser(null, "same@example.com");
        updates.setName("Updated Name");
        updates.setPhone("9999999999");
        updates.setAddress("Updated Address");
        updates.setIsActive(false);

        when(userRepository.findById(6L)).thenReturn(Optional.of(existing));
        when(userRepository.save(existing)).thenReturn(existing);

        User actual = userService.updateUser(6L, updates);

        assertEquals("Updated Name", actual.getName());
        assertEquals(false, actual.getIsActive());
        verify(valueOperations).set("User_6", existing);
    }

    @Test
    void deleteUserShouldDeleteFromDbAndRedis() {
        User existing = createUser(9L, "delete@example.com");
        when(userRepository.findById(9L)).thenReturn(Optional.of(existing));

        userService.deleteUser(9L);

        verify(userRepository).delete(existing);
        verify(redisTemplate).delete("User_9");
    }

    @Test
    void deactivateUserShouldSetInactiveAndCache() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        User existing = createUser(12L, "active@example.com");
        existing.setIsActive(true);
        when(userRepository.findById(12L)).thenReturn(Optional.of(existing));
        when(userRepository.save(existing)).thenReturn(existing);

        User actual = userService.deactivateUser(12L);

        assertEquals(false, actual.getIsActive());
        verify(valueOperations).set("User_12", existing);
    }

    @Test
    void activateUserShouldSetActiveAndCache() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        User existing = createUser(13L, "inactive@example.com");
        existing.setIsActive(false);
        when(userRepository.findById(13L)).thenReturn(Optional.of(existing));
        when(userRepository.save(existing)).thenReturn(existing);

        User actual = userService.activateUser(13L);

        assertEquals(true, actual.getIsActive());
        verify(valueOperations).set("User_13", existing);
    }

    @Test
    void getActiveUsersShouldDelegateToRepository() {
        when(userRepository.findByIsActive(true)).thenReturn(List.of(createUser(15L, "a@example.com")));

        List<User> users = userService.getActiveUsers();

        assertEquals(1, users.size());
        verify(userRepository).findByIsActive(true);
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

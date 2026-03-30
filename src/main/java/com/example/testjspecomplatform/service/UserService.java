package com.example.testjspecomplatform.service;

import com.example.testjspecomplatform.exception.DuplicateResourceException;
import com.example.testjspecomplatform.exception.ResourceNotFoundException;
import com.example.testjspecomplatform.model.User;
import com.example.testjspecomplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Redis disabled
    // @Autowired
    // private RedisTemplate<String, Object> redisTemplate;


    public User getUserData(Long id)
    {
        // Redis caching disabled - fetching directly from database
        Optional<User> optData = userRepository.findById(id);
        return optData.orElse(null);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("User", "email", user.getEmail());
        }
        User savedUser = userRepository.save(user);
        // Redis caching disabled
        // redisTemplate.opsForValue().set("User_" + savedUser.getId(), savedUser);
        return savedUser;
    }

    public User updateUser(Long id, User userDetails) {
        User existingUser = getUserById(id);

        // Check email uniqueness only if email is being changed
        if (!existingUser.getEmail().equals(userDetails.getEmail())
            && userRepository.existsByEmail(userDetails.getEmail())) {
            throw new DuplicateResourceException("User", "email", userDetails.getEmail());
        }

        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setPhone(userDetails.getPhone());
        existingUser.setAddress(userDetails.getAddress());
        existingUser.setIsActive(userDetails.getIsActive());

        User updatedUser = userRepository.save(existingUser);
        // Redis caching disabled
        // redisTemplate.opsForValue().set("User_" + id, updatedUser);
        return updatedUser;
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
        // Redis caching disabled
        // redisTemplate.delete("User_" + id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> searchUsers(String keyword) {
        return userRepository.searchByKeyword(keyword);
    }

    public List<User> getActiveUsers() {
        return userRepository.findByIsActive(true);
    }

    public User deactivateUser(Long id) {
        User user = getUserById(id);
        user.setIsActive(false);
        User deactivatedUser = userRepository.save(user);
        // Redis caching disabled
        // redisTemplate.opsForValue().set("User_" + id, deactivatedUser);
        return deactivatedUser;
    }

    public User activateUser(Long id) {
        User user = getUserById(id);
        user.setIsActive(true);
        User activatedUser = userRepository.save(user);
        // Redis caching disabled
        // redisTemplate.opsForValue().set("User_" + id, activatedUser);
        return activatedUser;
    }
}

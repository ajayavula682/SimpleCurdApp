package com.example.simplecurdapp.service;

import com.example.simplecurdapp.exception.DuplicateResourceException;
import com.example.simplecurdapp.exception.ResourceNotFoundException;
import com.example.simplecurdapp.model.User;
import com.example.simplecurdapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    public User getUserData(Long id)
    {

        //User_10

        User user=(User) redisTemplate.opsForValue().get("User_"+id);

        System.out.println(user);

        if(user==null)
        {
            System.out.println("Fetching from DB");
           Optional<User> optData = userRepository.findById(id);
           if(optData.isEmpty()==false)
           {
               user=optData.get();
               redisTemplate.opsForValue().set("User_"+id, user);
           }

        }
        
       
        return user;

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
        // Cache the newly created user
        redisTemplate.opsForValue().set("User_" + savedUser.getId(), savedUser);
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
        // Update cache with modified user
        redisTemplate.opsForValue().set("User_" + id, updatedUser);
        return updatedUser;
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
        // Remove from cache
        redisTemplate.delete("User_" + id);
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
        // Update cache with deactivated user
        redisTemplate.opsForValue().set("User_" + id, deactivatedUser);
        return deactivatedUser;
    }

    public User activateUser(Long id) {
        User user = getUserById(id);
        user.setIsActive(true);
        User activatedUser = userRepository.save(user);
        // Update cache with activated user
        redisTemplate.opsForValue().set("User_" + id, activatedUser);
        return activatedUser;
    }
}

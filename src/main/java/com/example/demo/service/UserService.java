package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.simple.User;
import com.example.demo.repository.simple.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserDTO> getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return Optional.of(toDTO(user));
        } else {
            return Optional.empty();
        }
    }

    public void saveUser(UserDTO userDTO) {
        log.info("{} created.", userDTO.toString());
        userRepository.save(toEntity(userDTO));
        User user = userRepository.findByUsername(userDTO.username());
        log.info("{} retrieved.", user.toString());
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword());
    }

    private User toEntity(UserDTO userDTO) {
        return new User(userDTO.id(), userDTO.username(), userDTO.password());
    }

    public boolean authenticate(String username, String password) {
        Optional<UserDTO> userOptional = getUserByUsername(username);

        return userOptional.isPresent() && userOptional.get().password().equals(password);
    }
}


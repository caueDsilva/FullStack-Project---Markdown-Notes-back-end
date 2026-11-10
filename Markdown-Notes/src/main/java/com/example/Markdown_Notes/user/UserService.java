package com.example.Markdown_Notes.user;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Markdown_Notes.DTO.UserCreateResquest;
import com.example.Markdown_Notes.exception.ResourceNotFoundException;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User nao encontrado"));
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User nao encontrado"));
    }

    public User createUser(UserCreateResquest request) {
        User newUser = new User();

        newUser.setName(request.name());
        newUser.setEmail(request.email());
        newUser.setPassword(request.password());
        newUser.setUserId(request.userId());
        return userRepository.save(newUser);
    }
}

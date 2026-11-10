package com.example.Markdown_Notes.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Markdown_Notes.DTO.UserResponseDTO;
import com.example.Markdown_Notes.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping({"/api/users"})
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping({"/profile"})
    public ResponseEntity<UserResponseDTO> getAuthenticatedUserProfile(@AuthenticationPrincipal User user) {
        if (user == null) {
            throw new ResourceNotFoundException("Usuário não autenticado");
        }

        User freshUser = userRepository.findById(user.getUserId())
                .orElse(user);

        return ResponseEntity.ok(UserResponseDTO.fromEntity(freshUser));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> findByEmail(@PathVariable String email) {

        User user = userService.findByEmail(email);
        return ResponseEntity.ok(UserResponseDTO.fromEntity(user));
    }
    
}

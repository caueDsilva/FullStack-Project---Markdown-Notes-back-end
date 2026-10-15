package com.example.Markdown_Notes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.Markdown_Notes.DTO.LoginRequestDTO;
import com.example.Markdown_Notes.DTO.RegisterRequestDTO;
import com.example.Markdown_Notes.DTO.ResponseDTO;
import com.example.Markdown_Notes.auth.AuthController;
import com.example.Markdown_Notes.exception.EmailAlreadyExistsException;
import com.example.Markdown_Notes.exception.InvalidCredentialsException;
import com.example.Markdown_Notes.infra.security.TokenService;
import com.example.Markdown_Notes.user.User;
import com.example.Markdown_Notes.user.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    private AuthController authController;

    @BeforeEach
    void setUp() {
        authController = new AuthController(userRepository, passwordEncoder, tokenService);
    }

    @Test
    void shouldRegisterNewUserSuccessfully() {
        RegisterRequestDTO request = new RegisterRequestDTO("Alice", "alice@example.com", "password123");

        when(userRepository.existsByEmail("alice@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(tokenService.generateToken(any(User.class))).thenReturn("jwt-token-123");

        ResponseEntity<ResponseDTO> response = authController.register(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Alice", response.getBody().name());
        assertEquals("jwt-token-123", response.getBody().token());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenRegisteringExistingEmail() {
        RegisterRequestDTO request = new RegisterRequestDTO("Alice", "alice@example.com", "password123");

        when(userRepository.existsByEmail("alice@example.com")).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> authController.register(request));
    }

    @Test
    void shouldLoginSuccessfullyWithValidCredentials() {
        LoginRequestDTO request = new LoginRequestDTO("alice@example.com", "password123");

        User user = new User();
        user.setId(1L);
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("alice@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(tokenService.generateToken(user)).thenReturn("jwt-token-123");

        ResponseEntity<ResponseDTO> response = authController.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Alice", response.getBody().name());
        assertEquals("jwt-token-123", response.getBody().token());
    }

    @Test
    void shouldThrowExceptionWhenLoginWithInvalidPassword() {
        LoginRequestDTO request = new LoginRequestDTO("alice@example.com", "wrong-password");

        User user = new User();
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("alice@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong-password", "encodedPassword")).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> authController.login(request));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundOnLogin() {
        LoginRequestDTO request = new LoginRequestDTO("notfound@example.com", "password123");

        when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> authController.login(request));
    }
}

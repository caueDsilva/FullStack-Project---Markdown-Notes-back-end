package com.example.Markdown_Notes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.Markdown_Notes.infra.security.TokenService;
import com.example.Markdown_Notes.user.User;

class TokenServiceTest {

    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        tokenService = new TokenService();
        ReflectionTestUtils.setField(tokenService, "secret", "test-secret-key-for-testing-12345");
        ReflectionTestUtils.setField(tokenService, "expirationMillis", 3600000L);
    }

    @Test
    void shouldGenerateAndValidateTokenSuccessfully() {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");

        String token = tokenService.generateToken(user);

        assertNotNull(token);
        String subject = tokenService.validateToken(token);
        assertEquals("test@example.com", subject);
    }

    @Test
    void shouldReturnNullForInvalidToken() {
        String invalidToken = "invalid.token.string";
        String subject = tokenService.validateToken(invalidToken);
        assertNull(subject);
    }
}

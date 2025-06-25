package com.product.trial.backend.test.controller;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.product.trial.backend.controller.AuthController;

import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {

    private final AuthController authController = new AuthController();

    @Test
    void testLoginSuccess() {
        // Arrange
        Map<String, String> validCredentials = Map.of("username", "sofiane", "role", "admin");

        // Act
        ResponseEntity<?> response = authController.login(validCredentials);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(((Map<?, ?>) response.getBody()).containsKey("token"));
    }

    @Test
    void testLoginFailure() {
        // Arrange
        Map<String, String> invalidCredentials = Map.of("username", "unknown", "role", "admin");

        // Act
        ResponseEntity<?> response = authController.login(invalidCredentials);

        // Assert
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Nom ou rôle invalide", response.getBody());
    }

    @Test
    void testValidateTokenSuccess() {
        // Arrange
        String token = Jwts.builder()
                .setSubject("sofiane")
                .claim("role", "admin")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 heure
                .signWith(SignatureAlgorithm.HS256, "IraNivtPdCc2kRa7QsLWZ+9n0vDPY2dgbcAWuU8NDOA=")
                .compact();

        // Act
        ResponseEntity<?> response = authController.validateToken(token);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("sofiane", ((Map<?, ?>) response.getBody()).get("username"));
    }

    @Test
    void testValidateTokenFailure() {
        // Arrange
        String invalidToken = "invalid.token.value";

        // Act
        ResponseEntity<?> response = authController.validateToken(invalidToken);

        // Assert
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Token invalide ou expiré", response.getBody());
    }
}

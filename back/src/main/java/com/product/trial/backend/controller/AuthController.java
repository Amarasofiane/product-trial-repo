package com.product.trial.backend.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final String SECRET_KEY = "IraNivtPdCc2kRa7QsLWZ+9n0vDPY2dgbcAWuU8NDOA=";

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String role = credentials.get("role");

        // Validation simple des noms et rôles
        if (!("sofiane".equals(username) && "admin".equalsIgnoreCase(role)) &&
            !("juliette".equals(username) && "user".equalsIgnoreCase(role))) {
            return ResponseEntity.status(401).body("Nom ou rôle invalide");
        }

        // Générer un token
        String token = Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 heure
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return ResponseEntity.ok(Map.of(
            "username", username,
            "role", role,
            "token", token
        ));
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {
            var claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            return ResponseEntity.ok(Map.of(
                "username", claims.getSubject(),
                "role", claims.get("role")
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token invalide ou expiré");
        }
    }
}

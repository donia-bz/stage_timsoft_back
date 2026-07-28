package tn.esprit.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.auth.dto.AuthResponse;
import tn.esprit.auth.dto.LoginRequest;
import tn.esprit.auth.dto.RegisterRequest;
import tn.esprit.auth.entity.Utilisateur;
import tn.esprit.auth.service.AuthService;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Utilisateur> getProfile(@PathVariable String id) {
        return ResponseEntity.ok(authService.getProfile(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<Utilisateur>> getAllUsers() {
        return ResponseEntity.ok(authService.getAllUsers());
    }

    @GetMapping("/users/role/{role}")
    public ResponseEntity<List<Utilisateur>> getUsersByRole(@PathVariable String role) {
        return ResponseEntity.ok(authService.getUsersByRole(role));
    }
}

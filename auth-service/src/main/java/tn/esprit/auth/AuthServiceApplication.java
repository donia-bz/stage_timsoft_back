package tn.esprit.auth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.esprit.auth.entity.Admin;
import tn.esprit.auth.repository.UtilisateurRepository;

import java.time.LocalDateTime;

@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDefaultAdmin(UtilisateurRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!repository.existsByEmail("admin@entreprise.tn")) {
                Admin admin = Admin.builder()
                        .nom("Admin")
                        .prenom("Super")
                        .email("admin@entreprise.tn")
                        .motDePasseHash(passwordEncoder.encode("admin123"))
                        .telephone("22000000")
                        .role("ADMIN")
                        .dateCreation(LocalDateTime.now())
                        .niveauAcces("SUPER_ADMIN")
                        .build();
                repository.save(admin);
                System.out.println("✅ Default Admin Created: admin@entreprise.tn / admin123");
            }
        };
    }
}

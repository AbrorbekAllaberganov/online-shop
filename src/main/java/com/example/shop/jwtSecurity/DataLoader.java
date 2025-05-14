package com.example.shop.jwtSecurity;


import com.example.shop.entity.Role;
import com.example.shop.entity.User;
import com.example.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String init;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (init.equals("create")){
            User user = new User();
            user.setRole(Role.ADMIN);
            user.setUserName("admin");
            user.setPassword(passwordEncoder.encode("OIL_GAS_SYSTEM_123"));
            user.setFullName("Admin Super");
            user.setActive(true);

            userRepository.save(user);

        }
    }
}

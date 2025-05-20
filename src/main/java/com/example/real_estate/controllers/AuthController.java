package com.example.real_estate.controllers;

/* imports */
import com.example.real_estate.entities.Role;
import com.example.real_estate.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {


    RoleRepository roleRepository;

    public AuthController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void setup() {
        initializeRole("ROLE_USER");
        initializeRole("ROLE_ADMIN");
        initializeRole("ROLE_TENANT");
        initializeRole("ROLE_OWNER");
    }

    private void initializeRole(String roleName) {
        roleRepository.updateOrInsert(new Role(roleName));
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}

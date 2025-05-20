package com.example.real_estate.controllers;

/* imports */
import com.example.real_estate.entities.Owner;
import com.example.real_estate.entities.User;
import com.example.real_estate.service.OwnerService;
import com.example.real_estate.service.UserService;
import com.example.real_estate.repositories.RoleRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("owner")
public class OwnerController {


    private OwnerService ownerService;
    private UserService userService;
    private RoleRepository roleRepository;

    public OwnerController(OwnerService ownerService, UserService userService, RoleRepository roleRepository) {
        this.ownerService = ownerService;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/new")
    public String saveOwner(@Valid @RequestParam("userId") Integer userId,
                            @RequestParam(value = "firstName", required = false) String firstName,
                            @RequestParam(value = "lastName", required = false) String lastName,
                            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                            Model model) {

        if (firstName == null || lastName == null || phoneNumber == null) {
            model.addAttribute("errorMessage",
                    "First name, last name, and phone number are required for new owner.");
            return "owner/ownerform";
        }
        User user = (User) userService.getUser(userId);
        if (user == null) {
            model.addAttribute("errorMessage", "User not found.");
            return "auth/users";
        }
        Owner owner = ownerService.getOwnerByAdmin(userId, firstName, lastName, phoneNumber);
        if (owner == null) {
            model.addAttribute("errorMessage",
                    "Your role as 'OWNER' has been revoked by the Administrator." +
                            " Please contact us for further details.");
            return "apartment/apartment";
        }
        ownerService.saveOwner(owner);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "auth/users";
    }

    @GetMapping("/{id}/apartments")
    public String showApartments(@PathVariable("id") Integer id, Model model){
        Owner owner = ownerService.getOwner(id);
        model.addAttribute("apartments", owner.getApartments());
        return "apartment/apartments";
    }

}
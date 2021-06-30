package by.kopetcev.shop.controllers;

import by.kopetcev.shop.model.User;
import by.kopetcev.shop.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@io.swagger.v3.oas.annotations.tags.Tag(name="User", description="User  register API")
public class  UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('read')")
    @Operation(summary = "register new user")
     public User newUser(@RequestParam String username, @RequestParam String password, @RequestParam String email) {

        return userService.save(username, password, email);
    }
}


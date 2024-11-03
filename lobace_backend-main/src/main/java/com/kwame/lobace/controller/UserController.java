package com.kwame.lobace.controller;

import com.kwame.lobace.Models.User;
import com.kwame.lobace.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    @PostMapping("/find-by-id/{id}")
    public User findById(@PathVariable Integer id) {
        return userService.findUserById(id);
    }

    @PostMapping("/update-by-id/{id}")
    public User updateUserById(@PathVariable Integer id, @RequestBody User user) {
        return userService.updateUserById(id, user);
    }

    @PostMapping("/delete-user/{id}")
    public User deleteUserById(@PathVariable Integer id) {
        return userService.deleteUserById(id);
    }


    @PostMapping("/find-all-users")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

}

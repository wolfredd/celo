package com.kwame.lobace.authentication;

import com.kwame.lobace.Models.User;
import com.kwame.lobace.dtos.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    private UserResponse signUpp(@RequestBody User user){
        return authenticationService.signUpp(user);
    }

    @PostMapping("/sign-up")
    public UserResponse signUp(
            @RequestPart ("firstName") String firstName,
            @RequestPart("lastName") String lastName,
            @RequestPart("age") String age,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("phoneNumber") String phoneNumber
    ) throws IOException {
        return authenticationService.signUp(firstName,lastName,age,email,password,phoneNumber);
    }


    @PostMapping("/login")
    public UserResponse login(@RequestBody User user) {
        return authenticationService.login(user);
    }
}

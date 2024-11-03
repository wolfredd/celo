package com.kwame.lobace.authentication;


import com.kwame.lobace.Models.User;
import com.kwame.lobace.dtos.UserResponse;
import com.kwame.lobace.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;


    public UserResponse signUpp (User user){

        UserResponse userResponse = new UserResponse();

        List<User> userList = userRepository.findAllByEmail(user.getEmail());

        if(userList.size() > 0){

            userResponse.setResponseCode("111");
            userResponse.setResponseMessage("USER ALREADY EXIST");
            return userResponse;
        }
        else{
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

            userResponse.setResponseCode("000");
            userResponse.setResponseMessage("USER CREATED");
            userResponse.setData(user);
            return userResponse;
        }
    }


    public UserResponse signUp(String firstName, String lastName, String age, String email, String password, String phoneNumber) throws IOException {

        UserResponse userResponse = new UserResponse();
        List<User> userList = userRepository.findAllByEmail(email);

        if(userList.size() > 0) {
            userResponse.setResponseCode("111");
            userResponse.setResponseMessage("User already exists");

            return userResponse;
        }
        else {
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAge(age.length());
            user.setEmail(email);
            user.setPassword(password);
            user.setPhoneNumber(phoneNumber.length());

//            byte[] imageBytes = image.getBytes();
//            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
//            user.setImage(base64Image);

            userRepository.save(user);

            userResponse.setResponseCode("000");
            userResponse.setResponseMessage("USER SIGNED UP SUCCESSFULLY");
            userResponse.setData(user);

            return userResponse;
        }
    }

    public UserResponse login(User user) {
        User user1 = userRepository.findByEmail(user.getEmail());

        UserResponse userResponse = new UserResponse();

        if(Objects.equals(user.getPassword(), user1.getPassword())) {
            log.info("Login successful");

            userResponse.setResponseMessage("USER LOGGED IN SUCCESSFULLY");
            userResponse.setResponseCode("000");
            userResponse.setData(user1);
            return userResponse;
        }

        else {
            log.warn("Login failed");

            userResponse.setResponseMessage("USER COULD NOT LOG IN");
            userResponse.setResponseCode("111");
            return userResponse;
        }
    }

}

package com.kwame.lobace.services;

import com.kwame.lobace.Models.User;
import com.kwame.lobace.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User findUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUserById(Integer id, User patient) {
        User user1 = userRepository.findById(id).orElse(null);

            user1.setFirstName(patient.getFirstName());
            user1.setLastName(patient.getLastName());
            user1.setEmail(patient.getEmail());
            user1.setPassword(patient.getPassword());
            user1.setAge(patient.getAge());
//            user1.setPhoneNumber(patient.getPhoneNumber());
//            user1.setImage(patient.getImage());

            userRepository.save(user1);

        return user1;
    }

    public User deleteUserById(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        userRepository.delete(user);

        return user;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
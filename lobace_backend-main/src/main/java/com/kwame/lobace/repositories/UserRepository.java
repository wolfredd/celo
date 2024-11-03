package com.kwame.lobace.repositories;

import com.kwame.lobace.Models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

//    List<User> findAllByEmail(String email);


    List<User> findAllByEmail(String email);

    User findByEmail(String email);

}

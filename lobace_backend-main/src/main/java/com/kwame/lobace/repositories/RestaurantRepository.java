package com.kwame.lobace.repositories;

import com.kwame.lobace.Models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query("SELECT a FROM Restaurant a WHERE a.name = :name")
    List<Restaurant> viewAllByName(String name);

}


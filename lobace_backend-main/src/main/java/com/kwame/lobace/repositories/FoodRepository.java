package com.kwame.lobace.repositories;

import com.kwame.lobace.Models.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {

    @Query("SELECT a FROM Food a WHERE a.name = :name")
    List<Food> viewAllByName(String name);

    List<Food> findAllByRestaurantId(Integer name);

}

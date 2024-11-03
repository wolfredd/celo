package com.kwame.lobace.controller;


import com.kwame.lobace.Models.Food;
import com.kwame.lobace.services.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/food")
@CrossOrigin(origins = "*")
public class FoodController {

    private final FoodService foodService;

    @PostMapping("/create-food")
    public Food createFood(
            @RequestPart ("name") String name,
            @RequestPart("price") String price,
            @RequestPart("image") MultipartFile image,
            @RequestPart("restaurantId") String restaurantId


    ) throws IOException {
        return foodService.createFood(name, price, image, Integer.parseInt(restaurantId));
    }

    @PostMapping("/find-all-food")
    public List<Food> findAllFood() {
        return foodService.findAllFood();
    }

    @PostMapping("/find-by-id/{id}")
    public Food findById(@PathVariable Integer id) {
        return foodService.findFoodById(id);
    }

    @PostMapping("/find-foods-by-restaurant-id/{id}")
    public List<Food> findFoodsByRestaurantId(@PathVariable Integer id) {
        return foodService.findFoodByRestaurantId(id);
    }

    @PostMapping("/update-by-id/{id}")
    public Food updateFoodById(@PathVariable Integer id, @RequestBody Food food) {
        return foodService.updateFoodById(id, food);
    }

    @PostMapping("/delete-food/{id}")
    public Food deleteFoodById(@PathVariable Integer id) {
        return foodService.deleteFoodById(id);
    }

}


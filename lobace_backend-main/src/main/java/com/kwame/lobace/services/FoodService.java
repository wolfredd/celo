package com.kwame.lobace.services;

import com.kwame.lobace.Models.Food;
import com.kwame.lobace.repositories.FoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodService {

    private final FoodRepository foodRepository;

    public Food createFood(String name, String price, MultipartFile image, Integer restaurantId) throws IOException {

        Food food = new Food();

        byte[] imageBytes = image.getBytes();

        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        food.setImage(base64Image);
        food.setName(name);
        food.setPrice(price);
        food.setRestaurantId(restaurantId);

        return foodRepository.save(food);
    }

    public List<Food> findAllFood() {
        return foodRepository.findAll();
    }

    public Food findFoodById(Integer id) {
        return foodRepository.findById(id).orElse(null);
    }

    public List<Food> findFoodByRestaurantId(Integer id) {
        return foodRepository.findAllByRestaurantId(id);
    }

    public Food updateFoodById(Integer id, Food food) {
        Food food1 = foodRepository.findById(id).orElse(null);

        food1.setName(food.getName());
        food1.setImage(food.getImage());
        food1.setPrice(food.getPrice());
        foodRepository.save(food1);

        return food1;
    }

    public Food deleteFoodById(Integer id) {
        Food food = foodRepository.findById( id).orElse(null);
        foodRepository.delete(food);

        return food;
    }

}


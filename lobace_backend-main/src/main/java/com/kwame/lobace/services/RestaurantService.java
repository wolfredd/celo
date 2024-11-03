package com.kwame.lobace.services;


import com.kwame.lobace.Models.Restaurant;
import com.kwame.lobace.repositories.RestaurantRepository;
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
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public Restaurant createRestaurant(String name, String category, MultipartFile image) throws IOException {

        Restaurant restaurant = new Restaurant();

        byte[] imageBytes = image.getBytes();
        // Convert the byte array to a Base64 encoded string

//        byte[] newImageBytes = ImageUtil.compressImage(imageBytes, 1);
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        restaurant.setImage(base64Image);
        restaurant.setName(name);
        restaurant.setCategory(category);


        return restaurantRepository.save(restaurant);
    }


    public Restaurant createNewRestaurant(String name, String category, MultipartFile image) throws IOException {

        Restaurant restaurant = new Restaurant();

        byte[] imageBytes = image.getBytes();
        // Convert the byte array to a Base64 encoded string

//        byte[] newImageBytes = ImageUtil.compressImage(imageBytes, 1);
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        restaurant.setImage(base64Image);
        restaurant.setName(name);
        restaurant.setCategory(category);


        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> findAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant findRestaurantById(Integer id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    public Restaurant updateRestaurantById(Integer id, Restaurant restaurant) {
        Restaurant restaurant1 = restaurantRepository.findById(id).orElse(null);

        restaurant1.setName(restaurant.getName());
        restaurant1.setCategory(restaurant.getCategory());

        return restaurant1;
    }

    public Restaurant deleteRestaurantById(Integer id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        restaurantRepository.delete(restaurant);

        return restaurant;
    }

}


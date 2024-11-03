package com.kwame.lobace.controller;

import com.kwame.lobace.Models.Restaurant;
import com.kwame.lobace.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
@CrossOrigin(origins = "*")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/create-restaurant")
    public Restaurant createRestaurant(
            @RequestPart ("name") String name,
            @RequestPart("image") MultipartFile image,
            @RequestPart("category") String category

    ) throws IOException {
        return restaurantService.createRestaurant(name, category, image);
    }


//    @PostMapping("/create-new-restaurant")
//    public Restaurant createNewRestaurant(@RequestBody Restaurant restaurant){
//        return restaurantService.createNewRestaurant(restaurant);
//    }

    @PostMapping("/find-all-restaurants")
    public List<Restaurant> findAllRestaurants() {
        return restaurantService.findAllRestaurants();
    }

    @PostMapping("/find-by-id/{id}")
    public Restaurant findById(@PathVariable Integer id) {
        return restaurantService.findRestaurantById(id);
    }

    @PostMapping("/update-by-id/{id}")
    public Restaurant updateRestaurantById(@PathVariable Integer id, @RequestBody Restaurant restaurant) {
        return restaurantService.updateRestaurantById(id, restaurant);
    }

    @PostMapping("/delete-restaurant/{id}")
    public Restaurant deleteRestaurantById(@PathVariable Integer id) {
        return restaurantService.deleteRestaurantById(id);
    }
}


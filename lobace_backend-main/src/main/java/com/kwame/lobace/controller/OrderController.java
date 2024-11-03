package com.kwame.lobace.controller;

import com.kwame.lobace.Models.Order;
import com.kwame.lobace.Models.Restaurant;
import com.kwame.lobace.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create-order")
    public Order createOrder(@RequestBody Order order) throws IOException {
        return orderService.createOrder(order);
    }


    @PostMapping("/find-order-by-user/{id}")
    public Map<String, List<Order>> viewOrders(@PathVariable Integer id) throws IOException {
        return orderService.getOrdersGroupedByIdForOrder(id);
    }

}

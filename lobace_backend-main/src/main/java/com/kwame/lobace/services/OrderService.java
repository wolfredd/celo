package com.kwame.lobace.services;

import com.kwame.lobace.Models.Food;
import com.kwame.lobace.Models.Order;
import com.kwame.lobace.Models.Restaurant;
import com.kwame.lobace.dtos.OrderListResponse;
import com.kwame.lobace.repositories.FoodRepository;
import com.kwame.lobace.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;

    public Order createOrder(Order order) {

        try{
            order.setDateCreated(LocalDate.now());
            order.setCompletionStatus("Pending");
            Food food = foodRepository.findById(order.getFoodId()).orElseThrow(() -> new RuntimeException("Food not found"));
            order.setFoodName(food.getName());

        }
        catch (Exception e){
            System.out.println("#########################################");
            System.out.println(e.getMessage());
            return null;
        }

        return orderRepository.save(order);
    }


//    public OrderListResponse findOrdersByIdOfOrder

    public Map<String, List<Order>> getOrdersGroupedByIdForOrder(Integer userId) {
        // Fetch all orders for the user, ordered by idForOrder
        List<Order> orders = orderRepository.findAllByUserIdGroupedByIdForOrder(userId);

        // Group orders by idForOrder
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getIdForOrder));
    }

    public List<Order> findAllOrders() throws IOException {
        return orderRepository.findAll();
    }

    public Order updateOrderById(Integer id, Order order) {
        Order order1 = orderRepository.findById(id).orElse(null);

        order1.setFoodId(order.getFoodId());
        order1.setQuantity(order.getQuantity());

        return order1;
    }

    public Order deleteOrderById(Integer id) {
        Order order = orderRepository.findById(id).orElse(null);
        orderRepository.delete(order);

        return order;
    }


}

package com.kwame.lobace.dtos;

import com.kwame.lobace.Models.Order;
import com.kwame.lobace.Models.User;

import java.util.List;

public class OrderListResponse extends ResponseMessage{

    private String idOfOrder;

    private List<Order> data;
}
package com.kwame.lobace.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "lobace_order")
@RequiredArgsConstructor
@Data
public class Order {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer foodId;
    private Integer quantity;
    private Integer userId;
    private String idForOrder;


    private String foodName;
    private LocalDate dateCreated;
    private String completionStatus;

}

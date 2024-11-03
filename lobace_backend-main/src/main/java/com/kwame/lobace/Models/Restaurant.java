package com.kwame.lobace.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "restaurant")
@RequiredArgsConstructor
@Data
public class Restaurant {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String category;
    private String image;

}

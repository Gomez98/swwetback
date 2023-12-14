package com.sweet.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product")
public class Product extends BaseEntity {

    @Column(name = "price")
    private Double price;

    @Column(name = "title")
    private String title;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "sku")
    private String sku;

    @Column(name = "description")
    private String description;

}

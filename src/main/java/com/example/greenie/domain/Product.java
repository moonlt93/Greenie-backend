package com.example.greenie.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="productId",nullable = false)
    private Long id;

    private String productName;
    private String description;
    private String imageUrl;
    private String imageLink;

    private String hashName;

    @Builder.Default
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<HashTag> hash = new ArrayList<>();


public void addHashName(String name){
    this.hashName += name+",";
}
}

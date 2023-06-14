package com.example.greenie.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HashTag {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hashId",nullable = false)
    private Long id;


    private String hashTagName;


    @ManyToOne(fetch = FetchType.LAZY)
    public Product product;



}

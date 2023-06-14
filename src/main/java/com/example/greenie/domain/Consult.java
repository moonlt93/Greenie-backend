package com.example.greenie.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="consultId",nullable = false)
    private Long id;

    private String title;
    private String number;
    private String url;

    private String description;

}

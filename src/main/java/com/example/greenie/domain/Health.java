package com.example.greenie.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Health extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="healthId",nullable = false)
    private Long id;

    private String title;
    private String extra;
    private String imageUrl;


    @CreatedDate
    @Column(name="createdAt",nullable = false,updatable = false)
    private LocalDateTime createdAt;


    @Builder.Default
    @OneToMany(mappedBy = "health",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<HealthList> list = new ArrayList<>();

}

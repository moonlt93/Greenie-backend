package com.example.greenie.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Noise extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="noiseId",nullable = false)
    private Long id;

    private int depth;
    private String korName;
    private String engName;

    @CreatedDate
    @Column(name="createdAt",nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="parentId")
    private Noise parentNoise;

    @OneToMany(mappedBy = "parentNoise",cascade = CascadeType.ALL)
    private  List<Noise> subNoise = new ArrayList<>();

    @OneToMany(mappedBy = "noise",cascade = CascadeType.ALL, orphanRemoval = true)
    private  List<NoiseContent> contents = new ArrayList<>();


    @Builder
    public Noise(Long id,int depth,List<NoiseContent> noiseContent ,String korName, String engName,String contentUrl,Noise parentNoise,List<Noise> subNoise){
        this.id = id;
        this.depth = depth;
        this.korName =korName;
        this.engName = engName;
        this.contents = noiseContent;
        this.parentNoise = parentNoise;
        this.subNoise = subNoise;
    }


    public void addSubNode(Noise noise){
        this.subNoise.add(noise);
        noise.setParentNoise(this);
    }


}

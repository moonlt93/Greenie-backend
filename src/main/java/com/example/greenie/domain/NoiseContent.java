package com.example.greenie.domain;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicUpdate
public class NoiseContent {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="noiseConId")
    private Long id;


    private String subTitle;
    private String content;
    private String contentUrl;

    private String imageUrl;


    @ManyToOne(fetch=FetchType.LAZY)
    private Noise noise;

    public void addNoiseCon(Noise noise){
        this.noise = noise;
        noise.getContents().add(this);
    }

    @Transactional
    public void modify(NoiseContent content){
        this.subTitle = content.getSubTitle();
        this.content = content.getContent();
        this.imageUrl= content.getImageUrl();
    }

}

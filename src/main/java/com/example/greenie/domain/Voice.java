package com.example.greenie.domain;

import com.example.greenie.type.VoiceStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Voice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dataId",nullable = false)
    private Long dataId;

    private String username;
    private String title;

    private Double bell;


    private String analysisData;

    @Enumerated(EnumType.STRING)
    private VoiceStatus status;



    @CreatedDate
    @Column(name="createdAt",nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name="updateAt", nullable = false)
    private LocalDateTime updateAt;

    @Builder
    public Voice(String username,String analysisData,String title,Double bell,VoiceStatus status
                 ){
        this.status =status;
        this.title= title;
        this.username = username;
        this.analysisData = analysisData;
        this.bell = bell;

    }



    public void update(Voice voice){
        this.analysisData = voice.getAnalysisData();
        this.bell = voice.getBell();

    }


}


package com.example.greenie.dto;

import lombok.Builder;
import lombok.Getter;
import org.json.simple.JSONArray;

@Getter
@Builder
public class NoiseAllDto {

    private Long noiseId;
    private Long parentId;

    private int depth;

    private String korName;
    private String engName;

    private JSONArray item;




}

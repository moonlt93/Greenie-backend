package com.example.greenie.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.springframework.web.multipart.MultipartFile;


public class NoiseDto {


    @Builder
    @Getter
    public static class Request {
        private Long parentId;
        private Long id;

        private String korName;
        private String engName;
        private String subTitle;
        // analysisData;

        private int depth;
        private String content;
        private String contentUrl;
        private MultipartFile file;
        private String analysisData;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response {

        private JSONArray item;


        public Response(JSONArray item) {
            this.item = item;
        }



    }


}

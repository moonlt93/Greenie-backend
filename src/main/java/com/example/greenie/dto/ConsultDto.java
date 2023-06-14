package com.example.greenie.dto;

import com.example.greenie.domain.Consult;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class ConsultDto {

    public Long id;

    public String title;
    public String description;
    public String phoneNumber;
    public String url;


    public static List<ConsultDto> of(List<Consult> lists) {

        if(lists.size() > 0){
            List<ConsultDto> resultList = new ArrayList<>();

            for (Consult con: lists
                 ) {

                ConsultDto dto = ConsultDto.builder()
                        .id(con.getId())
                        .title(con.getTitle())
                        .url(con.getUrl())
                        .description(con.getDescription())
                        .phoneNumber(con.getNumber())
                        .build();
                resultList.add(dto);
            }
            return resultList;
        }
        return null;
    }
}

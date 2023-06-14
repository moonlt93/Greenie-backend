package com.example.greenie.dto;

import com.example.greenie.domain.Voice;
import com.example.greenie.exception.SaesackException;
import lombok.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.greenie.exception.ErrorCodeImpl.VoiceErrorCodeImpl.ARGUMENT_ERROR;


@Builder
public class VoiceDto {

  static ArrayList<Node> list;

    @Builder
    @Getter
    public static class Request {
        private String username;
        private Long dataId;
        private String title;

        private Double bell; //평균

        private String analysisData;


    }


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {


        private JSONArray item;

        private Long dataId;

        private String username;
        private String title;

        private Double bell;//평균
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;


    }


    public static JSONArray of(String analysisData) {
        String[] str = analysisData.split(",");
        list = new ArrayList<>();
        double sum = 0.0;
        for (String val : str
        ) {
            try {
                String[] splits = val.split(" ");

                sum += Double.parseDouble(splits[1]);
                list.add(new Node(splits[0], Double.parseDouble(splits[1])));
            } catch (NumberFormatException e) {
                throw new SaesackException(ARGUMENT_ERROR, ARGUMENT_ERROR.getDescription());
            }
        }

        list.sort((o1, o2) -> Double.compare(o2.bell, o1.bell));


        JSONArray jsonArray = new JSONArray();
        try {

            for (Node node : list) {
                JSONObject sObj = new JSONObject();
                sObj.put("category", node.category);
                sObj.put("bell", node.bell);
                sObj.put("percent", Math.round((node.bell) * 100 / sum));
                jsonArray.add(sObj);


            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return jsonArray;
    }

    public static List<Response> off(List<Voice> userList) {

        if (userList.size() > 0) {
            List<Response> solutionList = new ArrayList<>();
            for (Voice u : userList) {

                Response dto = Response.builder()
                        .dataId(u.getDataId())
                        .title(u.getTitle())
                        .bell(u.getBell())
                        .username(u.getUsername())
                        .createdAt(u.getCreatedAt())
                        .updatedAt(u.getUpdatedAt())
                        .build();
                solutionList.add(dto);
            }
            return solutionList;
        }
        return null;

    }


    static class Node {
        String category;
        Double bell;

        public Node(String category, double bell) {
            this.category = category;
            this.bell = bell;
        }


    }
}

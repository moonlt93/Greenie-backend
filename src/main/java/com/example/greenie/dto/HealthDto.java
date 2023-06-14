package com.example.greenie.dto;

import com.example.greenie.domain.Health;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class HealthDto {



    @Getter
    public static class Request{

        public Long healthId;

        public String title;
        public String content;
        public String extra;

        public MultipartFile file;


    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response{

        private List<JSONObject> item;


        public Response(List<JSONObject> item){
            this.item = item;
        }

        public static List<JSONObject> of(List<Health> HealthList) throws Exception {

           if(HealthList.size() >0) {
               List<JSONObject> resultList = new ArrayList<>();

               for (Health health: HealthList
                    ) {
                   JSONObject Sobj = createObj(health);
                   resultList.add(Sobj);
               }
              return resultList;
           }
          return null;
        }

        private static JSONObject createObj(Health health) throws Exception {

            JSONObject obj = new JSONObject();

            try {
                JSONArray jsonArray = new JSONArray();

                for (int i = 0; i < health.getList().size(); i++) {
                    JSONObject sObj = new JSONObject();
                    sObj.put(i,health.getList().get(i).getContent());
                    jsonArray.add(sObj);
                }
                obj.put("fileUrl",health.getImageUrl());
                obj.put("title",health.getTitle());
                obj.put("conList",jsonArray);
            }catch(Exception e){
                throw new Exception(e);
            }
            return obj;
        }


    }
}

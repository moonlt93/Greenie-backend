package com.example.greenie.dto;

import com.example.greenie.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class ProductDto {

    public Long productId;

    public String productName;
    public String description;
    public String imageUrl;
    public String imageLink;

    public MultipartFile file;

    public String hashTagName;
    public String tags;


    @Getter
    public static class Request {

        private String analysisData;
    }

    @Getter
    @Setter
    public static class Response {

        JSONArray item;

    }


    @Setter
    @NoArgsConstructor
    public static class ResponseList {
        public Long productId;
        public String productName;
        public String description;
        public String imageUrl;
        public JSONArray hashTagName;
    }


    public static List<ResponseList> DtoOf(List<Product> list) {

        if (list.size() > 0) {

            List<ResponseList> result = new ArrayList<>();


            for (Product pro : list
            ) {
                String[] changeHashs = pro.getHashName().split(",");
                JSONArray jArr = new JSONArray();
                try {

                    for (int i = 0; i < changeHashs.length; i++) {
                        JSONObject sObj = new JSONObject();
                        sObj.put(i + 1, changeHashs[i]);
                        jArr.add(sObj);
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                ResponseList dto = new ResponseList();

                dto.setProductId(pro.getId());
                dto.setDescription(pro.getDescription());
                dto.setHashTagName(jArr);
                dto.setProductName(pro.getProductName());
                dto.setImageUrl(pro.getImageUrl());

                result.add(dto);
            }
            return result;
        }
        return null;
    }
    public static Response ResponseOf(List<Product> list, List<Map<Integer, String>> mapList) {
        Response res = new Response();
        JSONArray jsonArray = new JSONArray();
        try {

            for (int i = 0; i < list.size(); i++) {
                JSONObject sObj = new JSONObject();
                sObj.put("img", list.get(i).getImageUrl());
                sObj.put("link",list.get(i).getImageLink());
                sObj.put("title", list.get(i).getProductName());
                sObj.put("description", list.get(i).getDescription());
                sObj.put("tags", mapList.get(i));
                jsonArray.add(sObj);
            }
            res.setItem(jsonArray);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return res;
    }


}

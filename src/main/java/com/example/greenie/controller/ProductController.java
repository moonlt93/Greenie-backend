package com.example.greenie.controller;

import com.example.greenie.dto.ProductDto;
import com.example.greenie.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;



    @PostMapping
    public void createProduct(@RequestPart("product") ProductDto dto,
                              @RequestPart(value = "file",required = false)MultipartFile file) throws IOException {

        productService.createProduct(dto,file);
    }

    @GetMapping
    public ResponseEntity<ProductDto.Response> getProducts(@RequestBody ProductDto.Request req){

            String data = req.getAnalysisData();

            ProductDto.Response list =  productService.getProduct(data);
            return ResponseEntity.ok(list);

    }


    @GetMapping("/list")
    public ResponseEntity<List<ProductDto.ResponseList>> getAllProducts(){

       return ResponseEntity.ok(productService.getAllProduct());
    }
}

package com.example.greenie.controller;

import com.example.greenie.dto.ConsultDto;
import com.example.greenie.service.ConsultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/consult")
public class ConsultController {

    private final ConsultService consultService;

    @PostMapping
    public ResponseEntity<?> CreateConsult(@RequestBody ConsultDto dto){
        consultService.createConsult(dto);//뭔데
        return new ResponseEntity<>(HttpStatusCode.valueOf(201));
    }


    @GetMapping("/list")
    public ResponseEntity<List<ConsultDto>> getList(){
        List<ConsultDto> list = consultService.getAllConsult();
        return ResponseEntity.ok(list);
    }

}

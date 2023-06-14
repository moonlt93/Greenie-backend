package com.example.greenie.service;


import com.example.greenie.dto.ConsultDto;

import java.util.List;

public interface ConsultService {
    void createConsult(ConsultDto req);

    List<ConsultDto> getAllConsult();
}

package com.example.greenie.service.impl;

import com.example.greenie.domain.Consult;
import com.example.greenie.dto.ConsultDto;
import com.example.greenie.exception.SaesackException;
import com.example.greenie.repository.ConsultRepository;
import com.example.greenie.repository.VoiceRepository;
import com.example.greenie.service.ConsultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.greenie.exception.ErrorCodeImpl.ConsultErrorCodeImpl.ALREADY_EXIST_TITLE;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultServiceImpl implements ConsultService {


    private final ConsultRepository consultRepository;


    @Override
    public void createConsult(ConsultDto req) {

        Optional<Consult> con = consultRepository.findByTitle(req.getTitle());

        if (con.isEmpty()) {
            Consult consult = Consult.builder()
                    .description(req.getDescription())
                    .number(req.getPhoneNumber())
                    .url(req.getUrl())
                    .title(req.getTitle())
                    .build();

            consultRepository.save(consult);

        } else {
            throw new SaesackException(ALREADY_EXIST_TITLE,
                    ALREADY_EXIST_TITLE.getDescription());
        }
    }


    @Override
    public List<ConsultDto> getAllConsult() {


        return ConsultDto.of(consultRepository.findAll());

    }
}



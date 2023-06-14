package com.example.greenie.service.impl;

import com.example.greenie.domain.Health;
import com.example.greenie.domain.HealthList;
import com.example.greenie.dto.HealthDto;
import com.example.greenie.repository.HealthListRepository;
import com.example.greenie.repository.HealthRepository;
import com.example.greenie.service.HealthService;
import com.example.greenie.service.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HealthServiceImpl implements HealthService {

        private final HealthRepository healthRepository;
        private final HealthListRepository healthListRepository;

        private final S3Service s3Service;

    @Transactional
    @Override
    public void createHealth(HealthDto.Request dto, MultipartFile file) throws IOException {

        if(dto.getHealthId() != null) {
            Optional<Health> healthOptional = healthRepository.findById(dto.getHealthId());

            if (healthOptional.isPresent()) {

                Health health = healthOptional.get();

                HealthList list = new HealthList();
                list.setContent(dto.getContent());

                list.setHealth(health);
                health.getList().add(list);

                healthListRepository.save(list);
            }
        }else {
            String url = s3Service.uploadFile(file);
            Health health = Health.builder()
                    .imageUrl(url)
                    .title(dto.getTitle())
                    .list(new ArrayList<>())
                    .build();


            HealthList list = new HealthList();
            list.setContent(dto.getContent());
            list.setHealth(health);

            health.getList().add(list);

            healthRepository.save(health);
        }
    }

    @Override
    public HealthDto.Response getAllList() throws Exception {
            List<Health> list = healthRepository.findAll();

          return new HealthDto.Response(HealthDto.Response.of(list));
    }
}

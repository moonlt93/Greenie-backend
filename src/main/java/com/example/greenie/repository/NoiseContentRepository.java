package com.example.greenie.repository;

import com.example.greenie.domain.NoiseContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoiseContentRepository extends JpaRepository<NoiseContent,Long> {



    List<NoiseContent> findByNoise_Id(Long idx);

    Optional<NoiseContent> findBySubTitle(String subTitle);
}

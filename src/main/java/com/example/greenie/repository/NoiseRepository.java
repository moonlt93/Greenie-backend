package com.example.greenie.repository;

import com.example.greenie.domain.Noise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoiseRepository extends JpaRepository<Noise,Long> {


    @Query("SELECT u FROM Noise as u WHERE u.depth > 0")
    List<Noise> findAllThings();

    Optional<Noise> findByEngName(String engName);
}

package com.example.greenie.repository;

import com.example.greenie.domain.Health;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthRepository extends JpaRepository<Health,Long> {

    Optional<Health> findByTitle(String title);
}

package com.example.greenie.repository;

import com.example.greenie.domain.HealthList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthListRepository extends JpaRepository<HealthList,Long> {
}

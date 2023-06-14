package com.example.greenie.repository;

import com.example.greenie.domain.Consult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsultRepository extends JpaRepository<Consult,Long> {

    Optional<Consult> findByTitle(String title);
}

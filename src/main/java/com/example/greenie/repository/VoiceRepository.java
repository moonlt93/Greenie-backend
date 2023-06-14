package com.example.greenie.repository;

import com.example.greenie.domain.Voice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoiceRepository extends JpaRepository<Voice,Long> {
    Optional<Voice> findByUsernameAndTitle(String username, String title);

    List<Voice> findAllByUsername(String username);

    Optional<Voice> findByUsername(String username);
}

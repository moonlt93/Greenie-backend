package com.example.greenie.repository;

import com.example.greenie.domain.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {


    List<HashTag> findAllByHashTagName(String hashTagName);
}

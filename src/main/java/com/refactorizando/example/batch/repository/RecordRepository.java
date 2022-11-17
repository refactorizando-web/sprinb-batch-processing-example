package com.refactorizando.example.batch.repository;

import com.refactorizando.example.batch.entity.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<RecordEntity, Long> {
}

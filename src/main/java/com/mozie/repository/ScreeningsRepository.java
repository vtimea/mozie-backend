package com.mozie.repository;

import com.mozie.model.database.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningsRepository extends JpaRepository<Screening, Integer> {
}

package com.example.diagnostic.repositories;

import com.example.diagnostic.entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}

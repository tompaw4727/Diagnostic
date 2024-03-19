package com.example.diagnostic.services;

import com.example.diagnostic.entities.Test;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.diagnostic.repositories.TestRepository;

import java.util.List;

@Service
public class TestService {
    private final TestRepository testRepository;
    @PersistenceContext
    EntityManager entityManager; /// To do tego uzywania zpaytan sql


    @Autowired
    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }


    public List<Test> getTests() {
        return this.testRepository.findAll();

    }

    public Test getTestById(Integer id) {
        return testRepository.getById(Long.valueOf(id));

    }

}

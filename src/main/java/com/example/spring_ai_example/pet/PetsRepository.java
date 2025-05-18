package com.example.spring_ai_example.pet;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetsRepository extends ListCrudRepository<Pets, Integer> {
}

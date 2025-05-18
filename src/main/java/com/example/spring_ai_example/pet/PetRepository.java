package com.example.spring_ai_example.pet;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends ListCrudRepository<Pet, Integer> {
}

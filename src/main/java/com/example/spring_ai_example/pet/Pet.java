package com.example.spring_ai_example.pet;
import org.springframework.data.annotation.Id;

import java.util.Date;

public record Pet(
        @Id int id,
        String name,
        String species,
        String breed,
        Date dateOfBirth,
        double weight,
        String description,
        String ownerName,
        String ownerContact) {}

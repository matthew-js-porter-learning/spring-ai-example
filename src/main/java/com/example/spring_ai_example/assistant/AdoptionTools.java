package com.example.spring_ai_example.assistant;

import com.example.spring_ai_example.pet.Pet;
import com.example.spring_ai_example.pet.PetRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

public class AdoptionTools {

    private final PetRepository petRepository;

    public AdoptionTools(final PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Tool(description = "Adopts a pet and sets the new owner for the pet")
    public void adopt(
            @ToolParam(description = "The id for the Pet") final int petId,
            @ToolParam(description = " The new owners name for the Pet. If you do not know the name. Ask") final String owerName,
            @ToolParam(description = "The Contact info for an owner. Can be an email") final String ownerContact) {

        petRepository.findById(petId).ifPresent(pet -> {
                    var updatedPet = new Pet(
                            pet.id(),
                            pet.name(),
                            pet.species(),
                            pet.breed(),
                            pet.dateOfBirth(),
                            pet.weight(),
                            pet.description(),
                            owerName,
                            ownerContact);
                    petRepository.save(updatedPet);
        });
    }
}

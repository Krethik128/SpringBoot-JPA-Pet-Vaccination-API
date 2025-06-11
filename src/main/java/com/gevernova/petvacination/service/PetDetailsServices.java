package com.gevernova.petvacination.service;

import com.gevernova.petvacination.dto.PetDTO;
import com.gevernova.petvacination.entity.PetDetails;

import java.util.List;
import java.util.Optional;

public interface PetDetailsServices {

    List<PetDTO> getAllPetDetails();
    PetDTO createPetDetails(PetDetails petDetails);
    Optional<PetDTO> getPetDetailsById(Long id);
    PetDTO updatePetDetails(Long id,PetDetails petDetails);
    void deletePetDetails(Long id);
    List<PetDTO> getPetsByVaccinationName(String name);
}

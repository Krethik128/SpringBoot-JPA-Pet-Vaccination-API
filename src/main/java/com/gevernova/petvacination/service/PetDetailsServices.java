package com.gevernova.petvacination.service;

import com.gevernova.petvacination.dto.PetResponseDTO;
import com.gevernova.petvacination.entity.PetDetails;

import java.util.List;
import java.util.Optional;

public interface PetDetailsServices {

    List<PetResponseDTO> getAllPetDetails();
    PetResponseDTO createPetDetails(PetDetails petDetails);
    Optional<PetResponseDTO> getPetDetailsById(Long id);
    PetResponseDTO updatePetDetails(Long id, PetDetails petDetails);
    void deletePetDetails(Long id);
    List<PetResponseDTO> getPetsByVaccinationName(String name);
}

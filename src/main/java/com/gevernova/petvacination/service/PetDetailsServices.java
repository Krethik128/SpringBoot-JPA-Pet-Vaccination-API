package com.gevernova.petvacination.service;

import com.gevernova.petvacination.dto.PetDataDTO;
import com.gevernova.petvacination.dto.PetRequestDTO;
import com.gevernova.petvacination.entity.PetDetails;

import java.util.List;
import java.util.Optional;

public interface PetDetailsServices {

    List<PetDetails> getAllPetDetails();
    PetDetails createPetDetails(PetDetails petDetails);
    Optional<PetDetails> getPetDetailsById(Long id);
    PetDetails updatePetDetails(Long id,PetDetails petDetails);
    void deletePetDetails(Long id);
    List<PetDetails> getPetsByVaccinationName(String name);
    PetDetails mapToEntity(PetRequestDTO requestDTO);
    PetDataDTO mapToResponse(PetDetails petDetails);


}

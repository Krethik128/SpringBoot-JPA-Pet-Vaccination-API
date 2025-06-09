package com.gevernova.petvacination.service;

import com.gevernova.petvacination.dto.PetDataDTO;
import com.gevernova.petvacination.dto.PetRequestDTO;
import com.gevernova.petvacination.dto.VaccinationDataDTO;
import com.gevernova.petvacination.entity.PetDetails;
import com.gevernova.petvacination.entity.VaccinationDetails;
import com.gevernova.petvacination.exceptionhandling.PetNotFoundException;
import com.gevernova.petvacination.repository.PetDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class PetServiceImplementation implements PetDetailsServices {

    private final PetDetailsRepository petDetailsRepository;

    private static final Logger logger = LoggerFactory.getLogger(PetServiceImplementation.class);

    // Business logic for mapping DTO to Entity using Builder
    public PetDetails mapToEntity(PetRequestDTO requestDTO) {
        logger.debug("Converting PetDetailsRequestDTO to PetDetails entity using Builder.");

        List<VaccinationDetails> vaccinationDetailsList = null;
        if (requestDTO.getVaccines() != null) {
            vaccinationDetailsList = requestDTO.getVaccines().stream()
                    .map(vaccineDTO -> VaccinationDetails.builder() // Use builder for VaccinationDetails
                            .name(vaccineDTO.getName())
                            .dateGiven(vaccineDTO.getDateGiven())
                            .build())
                    .collect(Collectors.toList());
        }

        return PetDetails.builder() // Use builder for PetDetails
                .petName(requestDTO.getName()) // Mapping DTO 'name' to entity 'petName'
                .species(requestDTO.getSpecies())
                .breed(requestDTO.getBreed())
                .ownerName(requestDTO.getOwnerName())
                .ownerContact(requestDTO.getOwnerContact())
                .vaccines(vaccinationDetailsList != null ? vaccinationDetailsList : List.of()) // Handle null list
                .build();
    }

    // Business logic for mapping Entity to Response DTO using Builder
    public PetDataDTO mapToResponse(PetDetails petDetails) {
        if (petDetails == null) {
            return null;
        }
        logger.debug("Converting pet entity with ID {} to PetDataDTO using Builder.", petDetails.getId());

        List<VaccinationDataDTO> vaccinationDataDTOList = null;
        if (petDetails.getVaccines() != null) {
            vaccinationDataDTOList = petDetails.getVaccines().stream()
                    .map(vaccineDetail -> VaccinationDataDTO.builder() // Use builder for VaccinationDataDTO
                            .name(vaccineDetail.getName())
                            .dateGiven(vaccineDetail.getDateGiven())
                            .build())
                    .collect(Collectors.toList());
        }

        return PetDataDTO.builder() // Use builder for PetDataDTO
                .id(petDetails.getId())
                .name(petDetails.getPetName()) // Mapping entity 'petName' to DTO 'name'
                .species(petDetails.getSpecies())
                .breed(petDetails.getBreed())
                .ownerName(petDetails.getOwnerName())
                .ownerContact(petDetails.getOwnerContact())
                .vaccines(vaccinationDataDTOList != null ? vaccinationDataDTOList : List.of()) // Handle null list
                .build();
    }

    public PetDataDTO requestToResponse(PetRequestDTO requestDTO){
        PetDetails petDetails=mapToEntity(requestDTO);
        PetDetails newPetDetails=createPetDetails(petDetails);
        PetDataDTO petDataDTO=mapToResponse(newPetDetails);
        return petDataDTO;
    }

    @Override
    public PetDetails createPetDetails(PetDetails petDetail) {
        logger.info("Saving new Pet Details {}", petDetail.getPetName());
        PetDetails savedPetDetails = petDetailsRepository.save(petDetail);
        logger.info("Pet Details Saved with ID:{}", savedPetDetails.getId());
        return savedPetDetails;
    }



    @Override
    public PetDetails updatePetDetails(Long id, PetDetails updatedPetDetails) {
        logger.info("Updating Pet Details for ID: {}", id);
        PetDetails existingPetDetail = petDetailsRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Attempted to update non-existent pet Details with ID: {}.", id);
                    return new PetNotFoundException("Pet with ID: " + id + " was not found.");
                });

        PetDetails petToSave = PetDetails.builder()
                .id(existingPetDetail.getId())
                .petName(updatedPetDetails.getPetName())
                .species(updatedPetDetails.getSpecies())
                .breed(updatedPetDetails.getBreed())
                .ownerName(updatedPetDetails.getOwnerName())
                .ownerContact(updatedPetDetails.getOwnerContact())
                .vaccines(updatedPetDetails.getVaccines())
                .build(); // Build the new instance

        logger.info("Saving updated pet details for ID: {}", petToSave.getId());
        return petDetailsRepository.save(petToSave);
    }

    @Override
    public List<PetDetails> getAllPetDetails() {
        logger.info("Getting all Pet Details");
        List<PetDetails> petDetails = petDetailsRepository.findAll();
        logger.info("Found {} Pet Details", petDetails.size());
        return petDetails;
    }

    @Override
    public Optional<PetDetails> getPetDetailsById(Long id) {
        Optional<PetDetails> pet = petDetailsRepository.findById(id);
        if (pet.isPresent()) {
            logger.debug("Fetched pet Details for Pet ID: {}", id);
        } else {
            logger.debug("pet with ID: {} not found in repository.", id);
        }
        return pet;
    }

    @Override
    public void deletePetDetails(Long id) {
        logger.info("Attempting to delete pet details with ID: {}.", id);
        if (!petDetailsRepository.existsById(id)) {
            logger.error("Cannot delete: Pet with ID: {} does not exist.", id);
            throw new PetNotFoundException("Pet with ID: " + id + " was not found.");
        }
        petDetailsRepository.deleteById(id);
        logger.info("Pet with ID: {} deleted successfully.", id);
    }

    @Override
    public List<PetDetails> getPetsByVaccinationName(String vaccineName){
        return petDetailsRepository.petsVaccinatedBySameDisease(vaccineName);
    }


}
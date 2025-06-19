package com.gevernova.petvacination.service;

import com.gevernova.petvacination.dto.PetResponseDTO;
import com.gevernova.petvacination.entity.PetDetails;
import com.gevernova.petvacination.exceptionhandling.PetNotFoundException;
import com.gevernova.petvacination.mapper.Mapper;
import com.gevernova.petvacination.repository.PetDetailsRepository;
import lombok.RequiredArgsConstructor;
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
    private final EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(PetServiceImplementation.class);

    @Override
    public PetResponseDTO createPetDetails(PetDetails petDetail) {
        PetDetails savedPetDetails = petDetailsRepository.save(petDetail);

        if(emailService.sendRegistrationEmail(petDetail.getOwnerEmail(),petDetail.getPetName())){
            logger.info("Pet Details Saved with Email Sent to owner");
        }

        return Mapper.mapToDTO(savedPetDetails);
    }



    @Override
    public PetResponseDTO updatePetDetails(Long id, PetDetails updatedPetDetails) {
        PetDetails newUpdatedPetDetails = petDetailsRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Attempted to update non-existent pet Details with ID: {}.", id);
                    return new PetNotFoundException("Pet with ID: " + id + " was not found.");
                });
        newUpdatedPetDetails.setPetName(updatedPetDetails.getPetName());
        newUpdatedPetDetails.setSpecies(updatedPetDetails.getSpecies());
        newUpdatedPetDetails.setBreed(updatedPetDetails.getBreed());
        newUpdatedPetDetails.setOwnerContact(updatedPetDetails.getOwnerContact());
        newUpdatedPetDetails.setOwnerName(updatedPetDetails.getOwnerName());
        newUpdatedPetDetails.setOwnerEmail(updatedPetDetails.getOwnerEmail());
        newUpdatedPetDetails.setVaccines(updatedPetDetails.getVaccines());

        PetDetails savedPetDetails = petDetailsRepository.save(newUpdatedPetDetails); // Save the updated entity
        logger.info("Saving updated pet details for ID: {}", savedPetDetails.getId());
        return Mapper.mapToDTO(savedPetDetails); // Return PetDTO
    }

    @Override
    public List<PetResponseDTO> getAllPetDetails() {
        List<PetDetails> petDetails = petDetailsRepository.findAll();
        return petDetails.stream().
                map(Mapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PetResponseDTO> getPetDetailsById(Long id) {
        Optional<PetDetails> pet = petDetailsRepository.findById(id);
        if (pet.isPresent()) {
            logger.debug("Fetched pet Details for Pet ID: {}", id);
            return pet.map(Mapper::mapToDTO);
        } else {
            logger.debug("pet with ID: {} not found in repository.", id);
            return Optional.empty();
        }
    }

    @Override
    public void deletePetDetails(Long id) {
        if (!petDetailsRepository.existsById(id)) {
            logger.error("Cannot delete: Pet with ID: {} does not exist.", id);
            throw new PetNotFoundException("Pet with ID: " + id + " was not found.");
        }
        petDetailsRepository.deleteById(id);
    }

    @Override
    public List<PetResponseDTO> getPetsByVaccinationName(String vaccineName){
        List<PetDetails> petDetailsList = petDetailsRepository.petsVaccinatedBySameDisease(vaccineName);
        return petDetailsList.stream()
                .map(Mapper::mapToDTO)
                .collect(Collectors.toList());
    }


}
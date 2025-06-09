package com.gevernova.petvacination.controller;
import com.gevernova.petvacination.dto.PetDataDTO;
import com.gevernova.petvacination.dto.PetRequestDTO;
import com.gevernova.petvacination.dto.ResponseDTO;
import com.gevernova.petvacination.entity.PetDetails;
import com.gevernova.petvacination.exceptionhandling.PetNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gevernova.petvacination.service.PetDetailsServices; // Import the interface
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor// Lombok to auto-inject PetServiceImplementation
public class PetDetailsController {

    // Change from PetServiceImplementation to PetDetailsServices (the interface)
    private final PetDetailsServices petDetailsServices; // Use the interface type


    private static final Logger logger = LoggerFactory.getLogger(PetDetailsController.class);


    @GetMapping
    public ResponseEntity<ResponseDTO> getAllPets() {
        logger.info("Received request to get all pets.");

        List<PetDetails> pets = petDetailsServices.getAllPetDetails();
        List<PetDataDTO> petDataDTOs = pets.stream()
                .map(petDetailsServices::mapToResponse)
                .collect(Collectors.toList());

        logger.info("Fetched {} pets.", petDataDTOs.size());
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .message("Successfully retrieved all "+petDataDTOs.size()+" pets details ")
                        .data(petDataDTOs)
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> registerPet(@Valid @RequestBody PetRequestDTO requestDTO) {
        logger.info("Received request to register a new pet for owner: {}", requestDTO.getOwnerName());

        PetDetails petDetails = petDetailsServices.mapToEntity(requestDTO);
        PetDetails savedPet = petDetailsServices.createPetDetails(petDetails);
        PetDataDTO responseData = petDetailsServices.mapToResponse(savedPet);

        logger.info("Pet registered successfully with ID: {}", responseData.getId());
        return new ResponseEntity <> (
                ResponseDTO.builder()
                        .message("Successfully registered new Pet")
                        .data(responseData)
                        .build()
                ,HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getPetById(@PathVariable Long id){
        logger.info("Received request to get pet by ID: {}", id);

        Optional<PetDetails> petOptional=petDetailsServices.getPetDetailsById(id);

        if(petOptional.isPresent()){
            PetDataDTO responseData=petDetailsServices.mapToResponse(petOptional.get());
            return new ResponseEntity<>(ResponseDTO.builder()
                    .message("Fetched Pet details with pet Id: "+id)
                    .data(responseData)
                    .build(),
                    HttpStatus.OK);
        }else{
            logger.warn("Pet with ID {} not found. Throwing PetNotFoundException.", id);
            throw new PetNotFoundException("Pet with ID: " + id + " was not found.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updatePetDetailsById(@PathVariable Long id, @Valid @RequestBody PetRequestDTO requestDTO){
        logger.info("Received request to update pet with ID: {}", id);
        PetDetails petDetailsToUpdate = petDetailsServices.mapToEntity(requestDTO);
        PetDetails updatedPet = petDetailsServices.updatePetDetails(id, petDetailsToUpdate);

        PetDataDTO responseData = petDetailsServices.mapToResponse(updatedPet);

        logger.info("Pet with ID {} updated successfully.", id);
        return new ResponseEntity<>(ResponseDTO.builder()
                .message("Pet updated successfully")
                .data(responseData)
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deletePetDetails(@PathVariable Long id){
        logger.info("Received request to delete pet with ID: {}", id);
        petDetailsServices.deletePetDetails(id); //this will throw exception if not present
        logger.info("Pet with ID {} deleted successfully.", id);
        return new ResponseEntity<>(ResponseDTO.builder()
                .message("Pet deleted successfully")
                .data(null)
                .build(),
                HttpStatus.NO_CONTENT);
    }

    @GetMapping("/vaccinated/{name}")
    public ResponseEntity<ResponseDTO> getPetsWithSameVaccine(@PathVariable String name) {
        List<PetDetails> petDetailsList = petDetailsServices.getPetsByVaccinationName(name);
        List<PetDataDTO> petDataDTO = petDetailsList.stream()
                .map(petDetailsServices::mapToResponse)
                .toList();

        return new ResponseEntity<>(ResponseDTO.builder()
                .message("Fetched all pet details with vaccination: " + name)
                .data(petDataDTO)
                .build(), HttpStatus.OK);
    }
}
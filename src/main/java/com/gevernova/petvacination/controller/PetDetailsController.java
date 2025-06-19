package com.gevernova.petvacination.controller;
import com.gevernova.petvacination.dto.PetResponseDTO;
import com.gevernova.petvacination.dto.PetRequestDTO;
import com.gevernova.petvacination.dto.ResponseDTO;
import com.gevernova.petvacination.entity.PetDetails;
import com.gevernova.petvacination.exceptionhandling.PetNotFoundException;
import com.gevernova.petvacination.mapper.Mapper;
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

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor// Lombok to auto-inject PetServiceImplementation
public class PetDetailsController {

    // Change from PetServiceImplementation to PetDetailsServices (the interface)
    private final PetDetailsServices petDetailsServices; // Use the interface type
    private static final Logger logger = LoggerFactory.getLogger(PetDetailsController.class);


    @GetMapping({"/","/get",""})
    public ResponseEntity<ResponseDTO> getAllPets() {

        List<PetResponseDTO> petDTOs = petDetailsServices.getAllPetDetails();

        logger.info("Fetched {} pets.", petDTOs.size());
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .message("Successfully retrieved all "+petDTOs.size()+" pets details ")
                        .data(petDTOs)
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> registerPet(@Valid @RequestBody PetRequestDTO requestDTO) {

        PetDetails petDetailsToSave = Mapper.mapToEntity(requestDTO);
        PetResponseDTO responseData = petDetailsServices.createPetDetails(petDetailsToSave);

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
        Optional<PetResponseDTO> petOptional = petDetailsServices.getPetDetailsById(id);

        if(petOptional.isPresent()){
            PetResponseDTO responseData = petOptional.get(); // Get PetDTO directly
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

        PetDetails petDetailsToUpdate = Mapper.mapToEntity(requestDTO);
        PetResponseDTO responseData = petDetailsServices.updatePetDetails(id, petDetailsToUpdate);

        return new ResponseEntity<>(ResponseDTO.builder()
                .message("Pet updated successfully")
                .data(responseData)
                .build(),
                HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deletePetDetails(@PathVariable Long id){
        petDetailsServices.deletePetDetails(id);

        logger.info("Pet with ID {} deleted successfully.", id);
        return new ResponseEntity<>(ResponseDTO.builder()
                .message("Pet deleted successfully")
                .data(null)
                .build(),
                HttpStatus.NO_CONTENT);
    }

    @GetMapping("/vaccinated/{name}")
    public ResponseEntity<ResponseDTO> getPetsWithSameVaccine(@PathVariable String name) {
        List<PetResponseDTO> petDTOs = petDetailsServices.getPetsByVaccinationName(name);

        return new ResponseEntity<>(ResponseDTO.builder()
                .message("Fetched all pet details with vaccination: " + name)
                .data(petDTOs)
                .build(), HttpStatus.OK);
    }
}
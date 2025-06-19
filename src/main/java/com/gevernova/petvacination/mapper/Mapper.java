package com.gevernova.petvacination.mapper;

import com.gevernova.petvacination.dto.PetResponseDTO;
import com.gevernova.petvacination.dto.PetRequestDTO;
import com.gevernova.petvacination.dto.VaccinationDTO;
import com.gevernova.petvacination.entity.PetDetails;
import com.gevernova.petvacination.entity.VaccinationDetails;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Mapper {

    private static final Logger logger = LoggerFactory.getLogger(Mapper.class);

    public static PetDetails mapToEntity(PetRequestDTO requestDTO) {
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
                .ownerEmail(requestDTO.getOwnerEmail())
                .vaccines(vaccinationDetailsList != null ? vaccinationDetailsList : List.of()) // Handle null list
                .build();

    }

    // Business logic for mapping Entity to Response DTO using Builder
    public static PetResponseDTO mapToDTO(PetDetails petDetails) {
        if (petDetails == null) {
            return null;
        }

        List<VaccinationDTO> vaccinationDataDTOList = null;
        if (petDetails.getVaccines() != null) {
            vaccinationDataDTOList = petDetails.getVaccines().stream()
                    .map(vaccineDetail -> VaccinationDTO.builder() // Use builder for VaccinationDataDTO
                            .name(vaccineDetail.getName())
                            .dateGiven(vaccineDetail.getDateGiven())
                            .build())
                    .collect(Collectors.toList());
        }

        return PetResponseDTO.builder() // Use builder for PetDataDTO
                .id(petDetails.getId())
                .name(petDetails.getPetName()) // Mapping entity 'petName' to DTO 'name'
                .species(petDetails.getSpecies())
                .breed(petDetails.getBreed())
                .ownerName(petDetails.getOwnerName())
                .ownerContact(petDetails.getOwnerContact())
                .ownerEmail(petDetails.getOwnerEmail())
                .vaccines(vaccinationDataDTOList != null ? vaccinationDataDTOList : List.of()) // Handle null list
                .build();
    }

}

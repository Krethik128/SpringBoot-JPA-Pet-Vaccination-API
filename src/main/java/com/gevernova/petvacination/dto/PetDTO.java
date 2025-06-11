package com.gevernova.petvacination.dto;

import com.gevernova.petvacination.entity.Species;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PetDTO {
    private Long id;
    private String name;
    private Species species;
    private String breed;
    private String ownerName;
    private String ownerContact;
    private java.util.List<VaccinationDTO> vaccines;
}

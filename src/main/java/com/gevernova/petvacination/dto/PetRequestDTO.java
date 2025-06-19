package com.gevernova.petvacination.dto;

import com.gevernova.petvacination.entity.Species;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PetRequestDTO {

    @NotBlank
    @Size(max = 50, message = "First name must not exceed 50 characters in length")
    private String name;

    @NotNull(message = "Species is required")
    private Species species;

    private String breed;

    @NotBlank(message = "Owner name is required")
    @Size(max = 50, message = " name of owner must not exceed 50 characters in length")
    private String ownerName;

    @NotBlank(message = "Owner contact is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be 10 digits long")
    private String ownerContact;

    @NotBlank(message = "Email should not be empty")
    @Email
    private String ownerEmail;

    @Valid
    private java.util.List<VaccinationRequestDTO> vaccines; //s;
}

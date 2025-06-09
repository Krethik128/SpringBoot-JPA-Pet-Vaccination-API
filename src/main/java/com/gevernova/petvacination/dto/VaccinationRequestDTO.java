package com.gevernova.petvacination.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VaccinationRequestDTO {
    @NotBlank(message = "Vaccination name cannot be blank")
    private String name;

    @PastOrPresent(message = "Vaccination date must be in the past or present") // Corrected validation message
    private LocalDate dateGiven;
}

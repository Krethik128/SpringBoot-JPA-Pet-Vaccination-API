package com.gevernova.petvacination.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinationDTO {
    private String name;
    private LocalDate dateGiven;
}

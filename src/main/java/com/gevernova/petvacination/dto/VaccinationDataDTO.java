package com.gevernova.petvacination.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinationDataDTO {
    private String name;
    private LocalDate dateGiven;
}

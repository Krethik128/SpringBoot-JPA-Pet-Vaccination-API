package com.gevernova.petvacination.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDate;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VaccinationDetails {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate dateGiven;


}

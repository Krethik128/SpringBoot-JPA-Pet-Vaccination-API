package com.gevernova.petvacination.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pet_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class PetDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String petName;

    @Column(nullable = false)
    private Species species;

    private String breed;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String ownerContact;

    @Column(nullable = false)
    private String ownerEmail;

    @ElementCollection
    @CollectionTable(name="pet_vaccine" ,joinColumns=@JoinColumn(name="pet_id"))
    private java.util.List<VaccinationDetails> vaccines;

}

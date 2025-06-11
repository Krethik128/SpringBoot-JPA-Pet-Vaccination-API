package com.gevernova.petvacination.repository;

import com.gevernova.petvacination.entity.PetDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PetDetailsRepository extends JpaRepository<PetDetails, Long> {

    @Query(value = "SELECT p FROM PetDetails p JOIN p.vaccines v WHERE LOWER(v.name) = LOWER(:vaccineName)")
    java.util.List<PetDetails> petsVaccinatedBySameDisease(@Param("vaccineName") String vaccineName);
}

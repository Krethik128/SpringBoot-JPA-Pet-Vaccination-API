package com.gevernova.petvacination.repository;

import com.gevernova.petvacination.entity.PetDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetDetailsRepository extends JpaRepository<PetDetails, Long> {

}

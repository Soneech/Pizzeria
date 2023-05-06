package org.soneech.repository;

import org.soneech.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT a FROM Address a WHERE a.city = ?1 AND a.street = ?2 AND a.houseNumber = ?3 AND a.details = ?4")
    Address findDuplicate(String city, String street, String houseNumber, String details);
}

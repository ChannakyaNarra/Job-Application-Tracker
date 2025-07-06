package com.clueper.funapptracker.repository;

import com.clueper.funapptracker.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {

    // Finds all companies owned by a specific user.
    List<Company> findAllByUser_Id(UUID userId);

    // Implements the search feature: finds companies by name for a specific user.
    // The "ContainingIgnoreCase" part makes the search flexible and case-insensitive.
    List<Company> findByNameContainingIgnoreCaseAndUser_Id(String name, UUID userId);

    @Query("SELECT COUNT(DISTINCT a.company) FROM Application a WHERE a.company.user.id = :userId")
    long countDistinctCompaniesByUserId(UUID userId);
}

package com.clueper.funapptracker.repository;

import com.clueper.funapptracker.model.Company;
import com.clueper.funapptracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {

    // Finds all companies owned by a specific user.
    List<Company> findAllByUser_Username(String username);

   Optional<Company> findByNameIgnoreCaseAndUser_Username(String companyName,String userNme);

    Optional<Company> findByIdAndUser(UUID companyId,User user);

    // Implements the search feature: finds companies by name for a specific user.
    // The "ContainingIgnoreCase" part makes the search flexible and case-insensitive.
    List<Company> findByNameContainingIgnoreCaseAndUser_Username(String name, String username);
//
//    @Query("SELECT COUNT(DISTINCT a.company) FROM Application a WHERE a.company.user.id = :userId")
//    long countDistinctCompaniesByUserId(UUID userId);

   // List<Company> findByUser(User user);
}

package com.clueper.funapptracker.repository;

import com.clueper.funapptracker.dto.ApplicationDTO;
import com.clueper.funapptracker.model.Application;
import com.clueper.funapptracker.model.ApplicationStatus;
import com.clueper.funapptracker.model.Company;
import com.clueper.funapptracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID> {

    //List<ApplicationDTO> getAllApplicationsForCompanyAndUser(Company company, User user);

    List<Application> findAllByCompany(Company company);

    // Find all jobs added by a specific user
    List<Application> findByUser_Username(String username);

    // Find a job by ID, company, and user (for granular authorization)
    Optional<Application> findByJobIdAndCompany_nameAndUser_username(String jobId,String companyName, String userName);

//    // Finds all applications associated with a specific company ID.
//    List<Application> findAllByCompany_Id(Integer companyId);
//
//    // --- Methods for Dashboard Stats ---
//
//    // Counts total applications for a specific user by joining through Company.
//    @Query("SELECT count(a) FROM Application a WHERE a.company.user.id = :userId")
//    long countByUserId(Integer userId);
//
//    // Counts applications by status for a specific user.
//    @Query("SELECT count(a) FROM Application a WHERE a.company.user.id = :userId AND a.status = :status")
//    long countByUserIdAndStatus(Integer userId, ApplicationStatus status);
//
//    // A more advanced query to get top companies by application count for a user.
//    // This returns a List of Object arrays, where each array is [companyName, applicationCount]
//    @Query("SELECT a.company.name, COUNT(a) as app_count FROM Application a WHERE a.company.user.id = :userId GROUP BY a.company.name ORDER BY app_count DESC")
//    List<Object[]> findTopCompaniesByApplicationCount(Integer userId);
}


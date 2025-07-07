package com.clueper.funapptracker.service;

import com.clueper.funapptracker.dto.ApplicationDTO;
import com.clueper.funapptracker.dto.ApplicationRequestDTO;
import com.clueper.funapptracker.model.Application;
import com.clueper.funapptracker.model.ApplicationStatus;
import com.clueper.funapptracker.model.Company;
import com.clueper.funapptracker.model.User;
import com.clueper.funapptracker.repository.ApplicationRepository;
import com.clueper.funapptracker.repository.CompanyRepository;
import com.clueper.funapptracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired private ApplicationRepository applicationRepository;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private UserRepository userRepository;


    public List<ApplicationDTO> getAllForCompanyAndUser(String companyName, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        Company company = companyRepository.findByNameIgnoreCaseAndUser_Username(companyName,username)
                .orElseThrow(() -> new RuntimeException("Company not found or unauthorized for user: " + username));
        List<Application> applications= applicationRepository.findAllByCompany(company);
        return applications.stream()
                .map(this::mapToApplicationDTO)
                .collect(Collectors.toList());
    }

    private ApplicationDTO mapToApplicationDTO(Application application) {
        return new ApplicationDTO(
                application.getId(),
                application.getRoleName(),
                application.getJobId(),
                application.getJobDescription(),
                application.getApplyLink(),
                application.getStatus(),
                application.getCreatedAt(),
                application.getCompany().getId(),
                        application.getCompany().getName(),
                        application.getUser().getId(),
                application.getUser().getUsername()
        );
    }


    @Transactional
    public ApplicationDTO createApplication(ApplicationRequestDTO requestDTO,String username) {
        System.out.println("called service");
        Company company = companyRepository.findByNameIgnoreCaseAndUser_Username(
                requestDTO.getCompanyName(),username)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // Authorization Check: Ensure the user owns the company
        if (!company.getUser().getUsername().equals(username)) {
            throw new SecurityException("User does not have permission to add an application to this company");
        }

        Application application = new Application();
        application.setCompany(company);
        application.setRoleName(requestDTO.getRoleName());
        application.setJobId(requestDTO.getJobId());
        application.setJobDescription(requestDTO.getJobDescription());
        application.setApplyLink(requestDTO.getApplyLink());
        application.setUser(company.getUser());
        // Status defaults to PENDING in the entity




        Application savedApp = applicationRepository.save(application);
        return mapToApplicationDTO(savedApp);
    }

   // @Transactional(readOnly = true)
//    public List<ApplicationDTO> getApplicationsForCompany(UUID companyId) {
//        User currentUser = getCurrentUser();
//        Company company = companyRepository.findById(companyId)
//                .orElseThrow(() -> new RuntimeException("Company not found"));
//
//        // Authorization Check
//        if (!company.getUser().getId().equals(currentUser.getId())) {
//            throw new SecurityException("User does not have permission to view these applications");
//        }
//
//        return applicationRepository.findAllByCompany(company).stream()
//                .map(this::mapToDTO)
//                .collect(Collectors.toList());
//    }

    @Transactional
    public void deleteApplication(UUID applicationId) {
        User currentUser = getCurrentUser();
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        // Authorization Check
        if (!application.getCompany().getUser().getId().equals(currentUser.getId())) {
            throw new SecurityException("User does not have permission to delete this application");
        }

        applicationRepository.delete(application);
    }

//    @Transactional
//    public ApplicationDTO updateApplication(UUID applicationId, ApplicationRequestDTO requestDTO) {
//        User currentUser = getCurrentUser();
//        Application application = applicationRepository.findById(applicationId)
//                .orElseThrow(() -> new RuntimeException("Application not found"));
//
//        // Authorization Check
//        if (!application.getCompany().getUser().getId().equals(currentUser.getId())) {
//            throw new SecurityException("User does not have permission to update this application");
//        }
//
//        // Update fields from DTO
//        application.setRoleName(requestDTO.roleName());
//        application.setJobId(requestDTO.jobId());
//        application.setJobDescription(requestDTO.jobDescription());
//        application.setApplyLink(requestDTO.applyLink());
//        if (requestDTO.status() != null) {
//            application.setStatus(requestDTO.status());
//        }
//
//        Application updatedApp = applicationRepository.save(application);
//        return mapToDTO(updatedApp);
//    }

    // Helper to get the currently authenticated user entity
    private User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Authenticated user not found in database"));
    }

//    // Helper to map an entity to a DTO
//    private ApplicationDTO mapToDTO(Application app) {
//        return new ApplicationDTO(
//                app.getId(), app.getCompany().getId(), app.getRoleName(), app.getJobId(),
//                app.getJobDescription(), app.getApplyLink(), app.getStatus(),
//                app.getCreatedAt(), app.getUpdatedAt()
//        );
//    }
}

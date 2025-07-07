
package com.clueper.funapptracker.service;

import com.clueper.funapptracker.dto.CompanyCreateDTO;
import com.clueper.funapptracker.dto.CompanyDTO;
import com.clueper.funapptracker.model.Company;
import com.clueper.funapptracker.model.User;
import com.clueper.funapptracker.repository.CompanyRepository;
import com.clueper.funapptracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new company for a given user.
     * @param companyDTO The DTO containing company details.
     * @return A DTO of the newly created company.
     */
    @Transactional
    public CompanyDTO createCompany(CompanyCreateDTO companyDTO, String username) {
        // Find the user by their username instead of ID
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        // Create a new Company entity
        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setLogoUrl(companyDTO.getLogoUrl());
        company.setUser(user);

        // Save the new company to the database
        Company savedCompany = companyRepository.save(company);

        // Return the DTO representation of the saved company
        return mapToCompanyDTO(savedCompany);
    }

    /**
     * Retrieves all companies for a given user.
     * @param userId The ID of the user.
     * @return A list of company DTOs.
     */
    //@Transactional(readOnly = true)
    public List<CompanyDTO> getCompaniesForUser(String username) {
        List<Company> companies = companyRepository.findAllByUser_Username(username);
        return companies.stream()
                .map(this::mapToCompanyDTO)
                .collect(Collectors.toList());
    }

//    /**
//     * Searches for companies by name for a given user.
//     * @param name The search term for the company name.
//     * @param userId The ID of the user.
//     * @return A list of matching company DTOs.
//     */
    //@Transactional(readOnly = true)
    public List<CompanyDTO> searchCompaniesForUser(String name, String username) {
        List<Company> companies = companyRepository.findByNameContainingIgnoreCaseAndUser_Username(name, username);
        return companies.stream()
                .map(this::mapToCompanyDTO)
                .collect(Collectors.toList());
    }
//
    // Helper method to map a Company entity to a CompanyDTO
    private CompanyDTO mapToCompanyDTO(Company company) {
        return new CompanyDTO(
                company.getId(),
                company.getName(),
                company.getLogoUrl(),
                company.getCreatedAt()
        );
    }

//    public CompanyDTO create(CompanyCreateDTO request, String name) {
//        User user=userRepository.findByUsername(name).orElseThrow();
//        Company company=Company.builder()
//                .logoUrl(request.getLogoUrl())
//                .name(request.getName())
//                .user(user)
//                .build();
//
//        companyRepository.save(company);
//        return mapToResponse(company);
//
//
//    }
//    public CourseResponse create(CourseRequest request,String username){
//        User user=userRepo.findByUsername(username).orElseThrow();
//        Course course=Course.builder()
//                .title(request.getTitle())
//                .description(request.getDescription())
//                .user(user)
//                .build();
//        courseRepo.save(course);
//        return mapToResponse(course);
//    }

//    private CompanyDTO mapToResponse(Company course) {
//        return new CompanyDTO(course.getId(),course.getName(),course.getLogoUrl(),course.getCreatedAt());
//    }
//
//    public List<CompanyDTO> getAll(String username) {
//        User user=userRepository.findByUsername(username).orElseThrow();
//        return companyRepository.findByUser(user).stream()
//                .map(this::mapToResponse)
//                .toList();
//    }

//    public List<CourseResponse> getAll(String username){
//        User user=userRepo.findByUsername(username).orElseThrow();
//        return courseRepo.findByUser(user).stream()
//                .map(this::mapToResponse)
//                .toList();
//    }
}

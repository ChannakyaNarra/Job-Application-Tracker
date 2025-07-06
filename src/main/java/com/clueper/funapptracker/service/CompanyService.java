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
    @Transactional(readOnly = true)
    public List<CompanyDTO> getCompaniesForUser(UUID userId) {
        List<Company> companies = companyRepository.findAllByUser_Id(userId);
        return companies.stream()
                .map(this::mapToCompanyDTO)
                .collect(Collectors.toList());
    }

    /**
     * Searches for companies by name for a given user.
     * @param name The search term for the company name.
     * @param userId The ID of the user.
     * @return A list of matching company DTOs.
     */
    @Transactional(readOnly = true)
    public List<CompanyDTO> searchCompaniesForUser(String name, UUID userId) {
        List<Company> companies = companyRepository.findByNameContainingIgnoreCaseAndUser_Id(name, userId);
        return companies.stream()
                .map(this::mapToCompanyDTO)
                .collect(Collectors.toList());
    }

    // Helper method to map a Company entity to a CompanyDTO
    private CompanyDTO mapToCompanyDTO(Company company) {
        return new CompanyDTO(
                company.getId(),
                company.getName(),
                company.getLogoUrl(),
                company.getCreatedAt()
        );
    }
}
package com.clueper.funapptracker.controller;

import com.clueper.funapptracker.dto.CompanyCreateDTO;
import com.clueper.funapptracker.dto.CompanyDTO;
import com.clueper.funapptracker.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * POST /api/companies : Creates a new company.
     * The new company is associated with the currently authenticated user.
     */
    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyCreateDTO companyCreateDTO,Principal principal) {
//        // Get the Authentication object from the security context
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Get the username of the currently authenticated user
        String currentUsername = principal.getName();

        CompanyDTO createdCompany = companyService.createCompany(companyCreateDTO, currentUsername);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

//    @PostMapping
//    public ResponseEntity<CompanyDTO> createCourse(@RequestBody CompanyCreateDTO request,
//                                                       Principal principal){
//        return  ResponseEntity.ok(companyService.create(request,principal.getName()));
//    }

    /**
     * GET /api/companies : Gets all companies for the current user.
     * GET /api/companies?name=... : Searches companies by name for the user.
     */
    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getUserCompanies(Principal principal) {

//        // Placeholder for the authenticated user's ID
//        UUID currentUserId = UUID.fromString("...-...");

        List<CompanyDTO> companies=companyService.getCompaniesForUser(principal.getName());
//        if (name != null && !name.isEmpty()) {
//            // If a name query param is provided, search by name
//            companies = companyService.searchCompaniesForUser(name, currentUserId);
//        } else {
//            // Otherwise, get all companies for the user
//            companies = companyService.getCompaniesForUser(currentUserId);
//        }

        return ResponseEntity.ok(companies);
    }

//@GetMapping
//public ResponseEntity<List<CompanyDTO>> getCourses(Principal principal){
//    return ResponseEntity.ok(companyService.getAll(principal.getName()));
//}
}

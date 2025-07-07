package com.clueper.funapptracker.controller;

import com.clueper.funapptracker.dto.ApplicationDTO;
import com.clueper.funapptracker.dto.ApplicationRequestDTO;
import com.clueper.funapptracker.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<?> createApplication(@RequestBody ApplicationRequestDTO requestDTO, Principal principal) {
       System.out.println("called controller");
        try {
            ApplicationDTO createdApplication = applicationService.createApplication(requestDTO, principal.getName());
            return new ResponseEntity<>(createdApplication, HttpStatus.CREATED);
        } catch (SecurityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/company/{companyName}")
    public ResponseEntity<?> getApplicationsForCompany(@PathVariable String companyName,Principal principal) {
        try {
            List<ApplicationDTO> applications = applicationService.getAllForCompanyAndUser(companyName,principal.getName());
            return ResponseEntity.ok(applications);
        } catch (SecurityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateApplication(@PathVariable UUID id, @RequestBody ApplicationRequestDTO requestDTO) {
//        try {
//            ApplicationDTO updatedApplication = applicationService.updateApplication(id, requestDTO);
//            return ResponseEntity.ok(updatedApplication);
//        } catch (SecurityException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteApplication(@PathVariable UUID id) {
//        try {
//            applicationService.deleteApplication(id);
//            return ResponseEntity.noContent().build(); // Standard for successful DELETE
//        } catch (SecurityException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
   // }
}

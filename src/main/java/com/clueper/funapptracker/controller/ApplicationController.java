package com.clueper.funapptracker.controller;

import com.clueper.funapptracker.dto.ApplicationDTO;
import com.clueper.funapptracker.dto.ApplicationRequestDTO;
import com.clueper.funapptracker.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<?> createApplication(@RequestBody ApplicationRequestDTO requestDTO) {
        try {
            ApplicationDTO createdApplication = applicationService.createApplication(requestDTO);
            return new ResponseEntity<>(createdApplication, HttpStatus.CREATED);
        } catch (SecurityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<?> getApplicationsForCompany(@PathVariable UUID companyId) {
        try {
            List<ApplicationDTO> applications = applicationService.getApplicationsForCompany(companyId);
            return ResponseEntity.ok(applications);
        } catch (SecurityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateApplication(@PathVariable UUID id, @RequestBody ApplicationRequestDTO requestDTO) {
        try {
            ApplicationDTO updatedApplication = applicationService.updateApplication(id, requestDTO);
            return ResponseEntity.ok(updatedApplication);
        } catch (SecurityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApplication(@PathVariable UUID id) {
        try {
            applicationService.deleteApplication(id);
            return ResponseEntity.noContent().build(); // Standard for successful DELETE
        } catch (SecurityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

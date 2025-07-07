package com.clueper.funapptracker.dto;

import com.clueper.funapptracker.model.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

// DTO for both creating and updating an application

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationRequestDTO{
//        UUID companyId, // Required for creation
        String companyName;
        String roleName;
        String jobId;
        String jobDescription;
        String applyLink;
        ApplicationStatus status; // For updates
        }
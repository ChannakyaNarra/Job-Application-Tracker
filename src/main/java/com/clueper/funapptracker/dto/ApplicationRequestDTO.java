package com.clueper.funapptracker.dto;

import com.clueper.funapptracker.model.ApplicationStatus;

import java.util.UUID;

// DTO for both creating and updating an application
public record ApplicationRequestDTO(
        UUID companyId, // Required for creation
        String roleName,
        String jobId,
        String jobDescription,
        String applyLink,
        ApplicationStatus status // For updates
) {}
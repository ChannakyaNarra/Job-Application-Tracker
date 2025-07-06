package com.clueper.funapptracker.dto;

import com.clueper.funapptracker.model.ApplicationStatus;
import java.sql.Timestamp;
import java.util.UUID;

// DTO for returning full application details
public record ApplicationDTO(
        UUID id,
        UUID companyId,
        String roleName,
        String jobId,
        String jobDescription,
        String applyLink,
        ApplicationStatus status,
        Timestamp createdAt,
        Timestamp updatedAt
) {}

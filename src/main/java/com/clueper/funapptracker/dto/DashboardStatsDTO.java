package com.clueper.funapptracker.dto;

// DTO for the main stat cards
public record DashboardStatsDTO(
        long totalApplications,
        long pendingApplications,
        long acceptedApplications,
        long rejectedApplications,
        long totalCompanies
) {}

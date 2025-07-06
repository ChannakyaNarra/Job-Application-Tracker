package com.clueper.funapptracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "job_id")
    private String jobId;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    @Lob // Use @Lob for potentially long text fields
    @Column(name = "job_description")
    private String jobDescription;

    @Column(name = "apply_link")
    private String applyLink;

    @Enumerated(EnumType.STRING) // Store the enum as a string (e.g., "PENDING")
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING; // Default value

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    // Foreign key relationship to the Company
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

}

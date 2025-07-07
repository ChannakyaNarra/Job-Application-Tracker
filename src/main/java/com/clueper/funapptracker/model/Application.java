package com.clueper.funapptracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "applications")
@Data
@AllArgsConstructor
@Builder
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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "company_id", nullable = false) // Foreign key to companies table
//    private Company company; // The company this job belongs to

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Foreign key to users table (redundant but simplifies queries)
    private User user; // The user who added this job (implicitly via company, but explicit is easier)

//    @Column(name = "created_at", nullable = false)
//    private LocalDateTime createdAt;

    public Application() {
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    }




}

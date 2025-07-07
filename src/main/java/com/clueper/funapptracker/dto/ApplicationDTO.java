package com.clueper.funapptracker.dto;

import com.clueper.funapptracker.model.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

// DTO for returning full application details
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO{
       private UUID id;
       private String roleName;
       private String jobId;
       private String jobDescription;
       private String applyLink;
       private ApplicationStatus status;
       private Timestamp createdAt;
       private UUID companyId;
       private String companyName;
       private UUID userId;           // ID of the user who added it
       private String username;


//    String roleName;
//    String jobId;
//    String jobDescription;
//    String applyLink;
//    ApplicationStatus status;

}

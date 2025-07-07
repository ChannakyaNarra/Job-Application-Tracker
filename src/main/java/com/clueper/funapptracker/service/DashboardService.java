//package com.clueper.funapptracker.service;
//
//import com.clueper.funapptracker.dto.CompanyRankingDTO;
//import com.clueper.funapptracker.dto.DashboardStatsDTO;
//import com.clueper.funapptracker.dto.XpDTO;
//import com.clueper.funapptracker.model.ApplicationStatus;
//import com.clueper.funapptracker.model.User;
//import com.clueper.funapptracker.repository.ApplicationRepository;
//import com.clueper.funapptracker.repository.CompanyRepository;
//import com.clueper.funapptracker.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional(readOnly = true)
//public class DashboardService {
//
//    @Autowired private ApplicationRepository applicationRepository;
//    @Autowired private CompanyRepository companyRepository;
//    @Autowired private UserRepository userRepository;
//
//    public DashboardStatsDTO getStats() {
//        User currentUser = getCurrentUser();
//        UUID userId = currentUser.getId();
//
//        long totalApps = applicationRepository.countByUserId(userId);
//        long pendingApps = applicationRepository.countByUserIdAndStatus(userId, ApplicationStatus.PENDING);
//        long acceptedApps = applicationRepository.countByUserIdAndStatus(userId, ApplicationStatus.ACCEPTED);
//        long rejectedApps = applicationRepository.countByUserIdAndStatus(userId, ApplicationStatus.REJECTED);
//        long totalCompanies = companyRepository.countDistinctCompaniesByUserId(userId);
//
//        return new DashboardStatsDTO(totalApps, pendingApps, acceptedApps, rejectedApps, totalCompanies);
//    }
//
//    public List<CompanyRankingDTO> getTopCompanies() {
//        User currentUser = getCurrentUser();
//
//        return applicationRepository.findTopCompaniesByApplicationCount(currentUser.getId()).stream()
//                .map(result -> new CompanyRankingDTO((String) result[0], (Long) result[1]))
//                .limit(5) // Ensure we only take the top 5
//                .collect(Collectors.toList());
//    }
//
//    public XpDTO getXpLevel() {
//        User currentUser = getCurrentUser();
//        UUID userId = currentUser.getId();
//
//        long totalApps = applicationRepository.countByUserId(userId);
//        long acceptedApps = applicationRepository.countByUserIdAndStatus(userId, ApplicationStatus.ACCEPTED);
//        long rejectedApps = applicationRepository.countByUserIdAndStatus(userId, ApplicationStatus.REJECTED);
//
//        int totalXp = (int) ((totalApps * 10) + (acceptedApps * 50) + (rejectedApps * 5));
//
//        // Simple leveling logic
//        int level = (totalXp / 100) + 1;
//        String levelName = "Level " + level; // You can define fun names for levels later
//
//        return new XpDTO(totalXp, level, levelName);
//    }
//
//    private User getCurrentUser() {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return userRepository.findByUsername(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
//    }
//}

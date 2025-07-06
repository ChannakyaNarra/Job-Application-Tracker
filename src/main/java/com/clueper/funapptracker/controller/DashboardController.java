package com.clueper.funapptracker.controller;

import com.clueper.funapptracker.dto.CompanyRankingDTO;
import com.clueper.funapptracker.dto.DashboardStatsDTO;
import com.clueper.funapptracker.dto.XpDTO;
import com.clueper.funapptracker.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * GET /api/dashboard/stats : Get summary statistics for the user.
     */
    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        DashboardStatsDTO stats = dashboardService.getStats();
        return ResponseEntity.ok(stats);
    }

    /**
     * GET /api/dashboard/rankings : Get top 5 companies by application count.
     */
    @GetMapping("/rankings")
    public ResponseEntity<List<CompanyRankingDTO>> getCompanyRankings() {
        List<CompanyRankingDTO> rankings = dashboardService.getTopCompanies();
        return ResponseEntity.ok(rankings);
    }

    /**
     * GET /api/dashboard/xp : Get the user's current XP and level.
     */
    @GetMapping("/xp")
    public ResponseEntity<XpDTO> getUserXp() {
        XpDTO xpData = dashboardService.getXpLevel();
        return ResponseEntity.ok(xpData);
    }
}

package com.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.application.dto.AppStatusTrackDTO;
import com.application.dto.GenericDropdownDTO;
import com.application.dto.MetricCardDTO;
import com.application.repository.AppStatusTrackRepository;
import com.application.repository.DgmRepository;

@Service
public class AppStatusTrackService {

    @Autowired
    private AppStatusTrackRepository appStatusTrackRepository;
    
    @Autowired
    private DgmRepository dgmRepository;

    /**
     * Gets the overall dashboard cards with year-over-year percentage change.
     */
//    @Cacheable(value = "dashboardCards")
    public List<MetricCardDTO> getDashboardCards() {
        Optional<AppStatusTrackDTO> currentStatsOptional = appStatusTrackRepository.findLatestAggregatedStats();
        AppStatusTrackDTO currentStats = currentStatsOptional.orElse(new AppStatusTrackDTO());
        return transformToMetricCards(currentStats);
    }

    /**
     * Gets dashboard cards for a single employee with year-over-year percentage change.
     */
//    @Cacheable(value = "dashboardCardsByEmployee", key = "#empId")
    public List<MetricCardDTO> getDashboardCardsByEmployee(Integer empId) {
        Optional<AppStatusTrackDTO> statsOptional = appStatusTrackRepository.findAggregatedStatsByEmployee(empId);
        AppStatusTrackDTO employeeStats = statsOptional.orElse(new AppStatusTrackDTO());
        return transformToMetricCards(employeeStats);
    }

    /**
     * PRIVATE HELPER: Converts stats DTO into metric cards with year-over-year percentage change.
     */
    private List<MetricCardDTO> transformToMetricCards(AppStatusTrackDTO stats) {
        List<MetricCardDTO> cards = new ArrayList<>();

        // Total Applications
        long totalThisYear = (stats.getTotalApplications() != null) ? stats.getTotalApplications() : 0L;
        long totalLastYear = (stats.getTotalApplicationsLastYear() != null) ? stats.getTotalApplicationsLastYear() : 0L;
        cards.add(new MetricCardDTO("Total Applications", (int) totalThisYear,
                calculatePercentageChange(totalThisYear, totalLastYear), "total_applications"));

        // Sold
        long soldThisYear = (stats.getAppSold() != null) ? stats.getAppSold() : 0L;
        long soldLastYear = (stats.getAppSoldLastYear() != null) ? stats.getAppSoldLastYear() : 0L;
        cards.add(new MetricCardDTO("Sold", (int) soldThisYear,
                calculatePercentageChange(soldThisYear, soldLastYear), "sold"));

        // Confirmed
        long confirmedThisYear = (stats.getAppConfirmed() != null) ? stats.getAppConfirmed() : 0L;
        long confirmedLastYear = (stats.getAppConfirmedLastYear() != null) ? stats.getAppConfirmedLastYear() : 0L;
        cards.add(new MetricCardDTO("Confirmed", (int) confirmedThisYear,
                calculatePercentageChange(confirmedThisYear, confirmedLastYear), "confirmed"));

        // Available
        long availableThisYear = (stats.getAppAvailable() != null) ? stats.getAppAvailable() : 0L;
        long availableLastYear = (stats.getAppAvailableLastYear() != null) ? stats.getAppAvailableLastYear() : 0L;
        cards.add(new MetricCardDTO("Available", (int) availableThisYear,
                calculatePercentageChange(availableThisYear, availableLastYear), "available"));

        // Issued
        long issuedThisYear = (stats.getAppIssued() != null) ? stats.getAppIssued() : 0L;
        long issuedLastYear = (stats.getAppIssuedLastYear() != null) ? stats.getAppIssuedLastYear() : 0L;
        cards.add(new MetricCardDTO("Issued", (int) issuedThisYear,
                calculatePercentageChange(issuedThisYear, issuedLastYear), "issued"));

        // Damaged
        long damagedThisYear = (stats.getAppDamaged() != null) ? stats.getAppDamaged() : 0L;
        long damagedLastYear = (stats.getAppDamagedLastYear() != null) ? stats.getAppDamagedLastYear() : 0L;
        cards.add(new MetricCardDTO("Damaged", (int) damagedThisYear,
                calculatePercentageChange(damagedThisYear, damagedLastYear), "damaged"));

        // Unavailable
        long unavailableThisYear = (stats.getAppUnavailable() != null) ? stats.getAppUnavailable() : 0L;
        long unavailableLastYear = (stats.getAppUnavailableLastYear() != null) ? stats.getAppUnavailableLastYear() : 0L;
        cards.add(new MetricCardDTO("Unavailable", (int) unavailableThisYear,
                calculatePercentageChange(unavailableThisYear, unavailableLastYear), "unavailable"));

        return cards;
    }

    /**
     * HELPER METHOD: Calculates percentage change between a current and previous value.
     */
    private int calculatePercentageChange(long currentValue, long previousValue) {
        if (previousValue == 0) {
            // If previous was 0, any increase is considered 100% growth.
            return currentValue > 0 ? 100 : 0;
        }
        // Formula: ((current - previous) / previous) * 100
        return (int) (((double) (currentValue - previousValue) / previousValue) * 100);
    }
    
    @Cacheable(value = "dgmEmployees")
    public List<GenericDropdownDTO> getAllDgmEmployees() {
        return dgmRepository.findAllDgmEmployees();
    }

        public List<MetricCardDTO> getMetricCards() {

            // Auto-detect latest academic year
            Integer currentYearId = appStatusTrackRepository.findLatestYearId();
            Integer previousYearId = currentYearId - 1;

            // Value (for ALL YEARS)
            Object[] overall = appStatusTrackRepository.getOverallTotals().get(0);
            Long overallWithPro = appStatusTrackRepository.getOverallWithPro(); // total_app where type = 4

            // Percentage calculations (YEAR-WISE ONLY)
            Object[] curr = appStatusTrackRepository.getTotalsByYear(currentYearId).get(0);
            Object[] prev = appStatusTrackRepository.getTotalsByYear(previousYearId).get(0);

            Long currProObj = appStatusTrackRepository.getWithProByYear(currentYearId);
            Long prevProObj = appStatusTrackRepository.getWithProByYear(previousYearId);

            // Convert values safely
            int totalApp      = toInt(overall[0]);
            int sold          = toInt(overall[1]);
            int confirmed     = toInt(overall[2]);
            int available     = toInt(overall[3]);
            int issued        = toInt(overall[4]);
            int damaged       = toInt(overall[5]);
            int unavailable   = toInt(overall[6]);
            int withProValue  = toInt(overallWithPro);

            // Year-wise values
            int currTotalApp     = toInt(curr[0]);
            int currSold         = toInt(curr[1]);
            int currConfirmed    = toInt(curr[2]);
            int currAvailable    = toInt(curr[3]);
            int currIssued       = toInt(curr[4]);
            int currDamaged      = toInt(curr[5]);
            int currUnavailable  = toInt(curr[6]);
            int currPro          = toInt(currProObj);

            int prevTotalApp     = toInt(prev[0]);
            int prevSold         = toInt(prev[1]);
            int prevConfirmed    = toInt(prev[2]);
            int prevAvailable    = toInt(prev[3]);
            int prevIssued       = toInt(prev[4]);
            int prevDamaged      = toInt(prev[5]);
            int prevUnavailable  = toInt(prev[6]);
            int prevPro          = toInt(prevProObj);

            // Prepare final metric card list
            List<MetricCardDTO> list = new ArrayList<>();

            list.add(new MetricCardDTO("Total Applications", totalApp, clampChange(prevTotalApp, currTotalApp), "total_applications"));
            list.add(new MetricCardDTO("Sold", sold, clampChange(prevSold, currSold), "sold"));
            list.add(new MetricCardDTO("Confirmed", confirmed, clampChange(prevConfirmed, currConfirmed), "confirmed"));
            list.add(new MetricCardDTO("Available", available, clampChange(prevAvailable, currAvailable), "available"));
            list.add(new MetricCardDTO("Issued", issued, clampChange(prevIssued, currIssued), "issued"));
            list.add(new MetricCardDTO("Damaged", damaged, clampChange(prevDamaged, currDamaged), "damaged"));
            list.add(new MetricCardDTO("Unavailable", unavailable, clampChange(prevUnavailable, currUnavailable), "unavailable"));

            // WITH PRO — using totalApp WHERE type = 4 (correct logic)
            list.add(new MetricCardDTO("With PRO", withProValue, clampChange(prevPro, currPro), "with_pro"));

            return list;
        }

        // Safe conversion
        private int toInt(Object o) {
            return o == null ? 0 : ((Number) o).intValue();
        }

        // Calculate percent and clamp to [-100, 100]
        private int clampChange(int prev, int curr) {
            if (prev == 0) return 100; // starting year, assume +100%
            double raw = ((double) (curr - prev) / prev) * 100;
            return clamp(raw);
        }

        // Bound percent result for dashboard friendliness
        private int clamp(double value) {
            if (value > 100) return 100;
            if (value < -100) return -100;
            return (int) Math.round(value);
        }

}
package com.application.service;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.dto.CombinedAnalyticsDTO;
import com.application.dto.GraphDTO;
import com.application.dto.GraphSoldSummaryDTO;
import com.application.dto.MetricDTO;
import com.application.dto.MetricsAggregateDTO;
import com.application.dto.MetricsDataDTO;
import com.application.dto.YearlyGraphPointDTO;
import com.application.entity.SCEmployeeEntity;
import com.application.repository.AcademicYearRepository;
import com.application.repository.AppStatusTrackRepository;
import com.application.repository.SCEmployeeRepository;
import com.application.repository.UserAppSoldRepository;
 
@Service
public class ApplicationAnalyticsService {
 
    @Autowired
    private UserAppSoldRepository userAppSoldRepository;
 
    @Autowired
    private AppStatusTrackRepository appStatusTrackRepository;
 
    @Autowired
    private AcademicYearRepository academicYearRepository;
    
    @Autowired
    private SCEmployeeRepository scEmployeeRepository;
 
    // --- YEARS UPDATED ---
    private static final List<Integer> GRAPH_YEARS = Arrays.asList(2024, 2025, 2026);
    private static final int METRICS_CURRENT_YEAR = 2026;
    private static final int METRICS_PREVIOUS_YEAR = 2025;
    // --- END OF UPDATE ---
 
    // --- Main Public Methods ---
    
public CombinedAnalyticsDTO getAnalyticsForEmployee(Integer empId) {
        
        Optional<SCEmployeeEntity> employeeOpt = scEmployeeRepository.findById(empId);
        
        if (employeeOpt.isEmpty()) {
            System.err.println("No employee found with ID: " + empId);
            return createEmptyAnalytics("Invalid Employee", empId, "Employee not found");
        }
        
        SCEmployeeEntity employee = employeeOpt.get();
        String role = employee.getEmpStudApplicationRole();
        
        if (role == null) {
             System.err.println("Employee " + empId + " has a null role.");
             return createEmptyAnalytics("Null Role", empId, "Employee has no role");
        }
        
        CombinedAnalyticsDTO analytics;
        
        switch (role) {
            case "DGM":
                System.out.println("Routing to DGM Analytics for empId: " + empId);
                analytics = getDgmAnalytics(empId);
                analytics.setRole("DGM");
                analytics.setEntityName(employee.getFirstName() + " " + employee.getLastName());
                analytics.setEntityId(empId);
                return analytics;
                
            case "Zonal Account":
                int zoneId = employee.getZoneId();
                System.out.println("Routing to Zone Analytics for zoneId: " + zoneId);
                analytics = getZoneAnalytics((long) zoneId);
                analytics.setRole("Zonal Account");
                analytics.setEntityName(employee.getZoneName());
                analytics.setEntityId(zoneId);
                return analytics;
                
            case "PRO":
                int campusId = employee.getEmpCampusId();
                System.out.println("Routing to Campus Analytics for campusId: " + campusId);
                analytics = getCampusAnalytics((long) campusId);
                analytics.setRole("PRO");
                analytics.setEntityName(employee.getCampusName());
                analytics.setEntityId(campusId);
                return analytics;
                
            default:
                System.err.println("Unrecognized role '" + role + "' for empId: " + empId);
                return createEmptyAnalytics(role, empId, "Unrecognized role");
        }
    }
 
    /**
     * Helper to create a default empty DTO with role info.
     */
    private CombinedAnalyticsDTO createEmptyAnalytics(String role, Integer id, String name) {
        CombinedAnalyticsDTO analytics = new CombinedAnalyticsDTO();
        analytics.setRole(role);
        analytics.setEntityId(id);
        analytics.setEntityName(name);
        return analytics;
    }
 
    public CombinedAnalyticsDTO getZoneAnalytics(Long zoneId) {
        CombinedAnalyticsDTO analytics = new CombinedAnalyticsDTO();
        
        // 1. Graph from UserAppSold
        analytics.setGraphData(getGraphData(
            (yearId) -> userAppSoldRepository.getSalesSummaryByZone(zoneId.intValue(), yearId)
        ));
        
        // 2. Metrics from AppStatusTrack + 'With PRO' from UserAppSold
        analytics.setMetricsData(getMetricsData(
            (yearId) -> appStatusTrackRepository.getMetricsByZoneAndYear(zoneId, yearId),
            (yearId) -> userAppSoldRepository.getProMetricByZone(zoneId.intValue(), yearId) // New PRO fetcher
        ));
        
        return analytics;
    }
 
    public CombinedAnalyticsDTO getDgmAnalytics(Integer dgmEmpId) {
        CombinedAnalyticsDTO analytics = new CombinedAnalyticsDTO();
        
        // 1. Graph from UserAppSold
        analytics.setGraphData(getGraphData(
            (yearId) -> userAppSoldRepository.getSalesSummaryByDgm(dgmEmpId, yearId)
        ));
        
        // 2. Metrics from AppStatusTrack + 'With PRO' from UserAppSold
        analytics.setMetricsData(getMetricsData(
            (yearId) -> appStatusTrackRepository.getMetricsByEmployeeAndYear(dgmEmpId, yearId),
            (yearId) -> userAppSoldRepository.getProMetricByDgm(dgmEmpId, yearId) // New PRO fetcher
        ));
        
        return analytics;
    }
 
    public CombinedAnalyticsDTO getCampusAnalytics(Long campusId) {
        CombinedAnalyticsDTO analytics = new CombinedAnalyticsDTO();
        
        // 1. Graph from UserAppSold
        analytics.setGraphData(getGraphData(
            (yearId) -> userAppSoldRepository.getSalesSummaryByCampus(campusId.intValue(), yearId)
        ));
        
        // 2. Metrics from AppStatusTrack + 'With PRO' from UserAppSold
        analytics.setMetricsData(getMetricsData(
            (yearId) -> appStatusTrackRepository.getMetricsByCampusAndYear(campusId, yearId),
            (yearId) -> userAppSoldRepository.getProMetricByCampus(campusId.intValue(), yearId) // New PRO fetcher
        ));
        
        return analytics;
    }
 
    // --- Private Graph Data Helper (uses UserAppSold) ---
 
    private GraphDTO getGraphData(Function<Integer, Optional<GraphSoldSummaryDTO>> dataFetcher) {
        GraphDTO graphData = new GraphDTO();
        List<YearlyGraphPointDTO> yearlyDataList = new ArrayList<>();
        
        for (int year : GRAPH_YEARS) { // Will use [2024, 2025, 2026]
            try {
                int acdcYearId = getAcdcYearId(year);
                String yearName = String.valueOf(year); // Using int year to avoid 'yearName' crash
 
                // Get the data from UserAppSold
                GraphSoldSummaryDTO summary = dataFetcher.apply(acdcYearId)
                    .orElse(new GraphSoldSummaryDTO());
 
                long totalAppCount = summary.totalApplications();
                long soldCount = summary.totalSold();
                
                double totalAppPercent = 100.0; // Bar is always 100%
                double soldPercent = calculatePercentage(soldCount, totalAppCount); // (sold / total) * 100
 
                yearlyDataList.add(new YearlyGraphPointDTO(
                    yearName, totalAppPercent, soldPercent, totalAppCount, soldCount
                ));
 
            } catch (Exception e) {
                System.err.println("Error fetching graph data for year " + year + ": " + e.getMessage());
                yearlyDataList.add(new YearlyGraphPointDTO(String.valueOf(year), 0, 0, 0, 0));
            }
        }
        graphData.setYearlyData(yearlyDataList);
        return graphData;
    }
 
    // --- Private Metrics Data Helper (UPDATED to accept 'With PRO' fetcher) ---
 
    private MetricsDataDTO getMetricsData(
            Function<Integer, Optional<MetricsAggregateDTO>> appTrackFetcher,
            Function<Integer, Optional<Long>> proMetricFetcher) {
        
        MetricsDataDTO metricsData = new MetricsDataDTO();
        metricsData.setCurrentYear(METRICS_CURRENT_YEAR);
        metricsData.setPreviousYear(METRICS_PREVIOUS_YEAR);
        
        try {
            int currentYearId = getAcdcYearId(METRICS_CURRENT_YEAR);
            int previousYearId = getAcdcYearId(METRICS_PREVIOUS_YEAR);
 
            // 1. Get main metrics from AppStatusTrack
            MetricsAggregateDTO currentMetrics = appTrackFetcher.apply(currentYearId)
                .orElse(new MetricsAggregateDTO());
            MetricsAggregateDTO previousMetrics = appTrackFetcher.apply(previousYearId)
                .orElse(new MetricsAggregateDTO());
            
            // 2. Get 'With PRO' metric from UserAppSold
            long proCurrent = proMetricFetcher.apply(currentYearId).orElse(0L);
            long proPrevious = proMetricFetcher.apply(previousYearId).orElse(0L);
 
            // 3. Build the list, now passing the PRO metric
            metricsData.setMetrics(buildMetricsList(currentMetrics, previousMetrics, proCurrent, proPrevious));
        } catch (Exception e) {
            System.err.println("Error fetching metrics data: " + e.getMessage());
            metricsData.setMetrics(new ArrayList<>());
        }
        return metricsData;
    }
 
    /**
     * Builds the metrics list.
     * UPDATED to include 'proCurrent' and 'proPrevious'
     */
    private List<MetricDTO> buildMetricsList(
            MetricsAggregateDTO current, MetricsAggregateDTO previous,
            long proCurrent, long proPrevious) {
        
        List<MetricDTO> metrics = new ArrayList<>();
 
        metrics.add(createMetric("Total Applications",
            current.totalApp(), previous.totalApp()));
 
        double soldPercentCurrent = calculatePercentage(current.appSold(), current.totalApp());
        double soldPercentPrevious = calculatePercentage(previous.appSold(), previous.totalApp());
        metrics.add(createMetricWithPercentage("Sold",
            current.appSold(), soldPercentCurrent, soldPercentPrevious));
 
        double confirmedPercentCurrent = calculatePercentage(current.appConfirmed(), current.totalApp());
        double confirmedPercentPrevious = calculatePercentage(previous.appConfirmed(), previous.totalApp());
        metrics.add(createMetricWithPercentage("Confirmed",
            current.appConfirmed(), confirmedPercentCurrent, confirmedPercentPrevious));
        
        metrics.add(createMetric("Available",
            current.appAvailable(), previous.appAvailable()));
 
        long validIssuedCurrent = Math.max(0, current.appIssued());
        long validIssuedPrevious = Math.max(0, previous.appIssued());
        double issuedPercentCurrent = calculatePercentage(validIssuedCurrent, current.totalApp());
        double issuedPercentPrevious = calculatePercentage(validIssuedPrevious, previous.totalApp());
        metrics.add(createMetricWithPercentage("Issued",
            current.appIssued(), issuedPercentCurrent, issuedPercentPrevious));
 
        metrics.add(createMetric("Damaged",
            current.appDamaged(), previous.appDamaged()));
            
        metrics.add(createMetric("Unavailable",
            current.appUnavailable(), previous.appUnavailable()));
        
        // --- ADDED 'WITH PRO' CARD ---
        metrics.add(createMetric("With PRO",
            proCurrent, proPrevious));
        // --- END OF ADDITION ---
 
        return metrics;
    }
 
    // --- UTILITY METHODS ---
 
    private MetricDTO createMetric(String title, long currentValue, long previousValue) {
        double change = calculatePercentageChange(currentValue, previousValue);
        return new MetricDTO(title, currentValue, change, getChangeDirection(change));
    }
 
    private MetricDTO createMetricWithPercentage(String title, long currentValue, double currentPercent, double previousPercent) {
        double change = calculatePercentageChange(currentPercent, previousPercent);
        return new MetricDTO(title, currentValue, change, getChangeDirection(change));
    }
 
    private double calculatePercentage(long numerator, long denominator) {
        if (denominator == 0) return 0.0;
        return (double) Math.max(0, numerator) * 100.0 / denominator;
    }
 
    private double calculatePercentageChange(double current, double previous) {
        if (previous == 0) return (current > 0) ? 100.0 : 0.0;
        return ((current - previous) / previous) * 100.0;
    }
    
    private String getChangeDirection(double change) {
        if (change > 0) return "up";
        if (change < 0) return "down";
        return "neutral";
    }
 
    private int getAcdcYearId(int year) {
        return academicYearRepository.findByYear(year)
            .orElseThrow(() -> new RuntimeException("Academic year not found for: " + year))
            .getAcdcYearId();
    }
}
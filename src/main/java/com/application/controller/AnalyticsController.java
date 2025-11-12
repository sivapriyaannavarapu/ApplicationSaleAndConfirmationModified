package com.application.controller;
 
import com.application.dto.CombinedAnalyticsDTO;

import com.application.service.ApplicationAnalyticsService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
 
@RestController

@RequestMapping("/api/analytics")

public class AnalyticsController {
 
    @Autowired

    private ApplicationAnalyticsService analyticsService;
 
    @GetMapping("/zone/{id}")

    public ResponseEntity<CombinedAnalyticsDTO> getZoneAnalytics(@PathVariable Long id) {

        try {

            CombinedAnalyticsDTO data = analyticsService.getZoneAnalytics(id);

            return ResponseEntity.ok(data);

        } catch (Exception e) {

            // Log the exception

            return ResponseEntity.internalServerError().build();

        }

    }
 
    @GetMapping("/dgm/{id}")

    public ResponseEntity<CombinedAnalyticsDTO> getDgmAnalytics(@PathVariable Integer id) {

        try {

            CombinedAnalyticsDTO data = analyticsService.getDgmAnalytics(id);

            return ResponseEntity.ok(data);

        } catch (Exception e) {

            // Log the exception

            return ResponseEntity.internalServerError().build();

        }

    }
 
    @GetMapping("/campus/{id}")

    public ResponseEntity<CombinedAnalyticsDTO> getCampusAnalytics(@PathVariable Long id) {

        try {

            CombinedAnalyticsDTO data = analyticsService.getCampusAnalytics(id);

            return ResponseEntity.ok(data);

        } catch (Exception e) {

            // Log the exception

            return ResponseEntity.internalServerError().build();

        }

    }
    
    
    @GetMapping("/{empId}")
    public ResponseEntity<CombinedAnalyticsDTO> getAnalyticsByEmployeeId(
            @PathVariable("empId") Integer empId) {
        
        try {
            // Call the new "router" method in the service
            CombinedAnalyticsDTO analytics = analyticsService.getAnalyticsForEmployee(empId);
 
            // Check if the service returned empty data (e.g., employee not found or no role)
            if (analytics.getGraphData() == null && analytics.getMetricsData() == null) {
                // You can customize this response
                // 404 Not Found is good if the employee ID itself was invalid
                // 400 Bad Request is good if the employee was found but had no valid role
                return ResponseEntity.badRequest().body(new CombinedAnalyticsDTO());
            }
 
            // Return the populated analytics object
            return ResponseEntity.ok(analytics);
            
        } catch (Exception e) {
            // Catch any unexpected errors (e.g., database connection issues)
            System.err.println("Error in AnalyticsController: " + e.getMessage());
            // Return a 500 Internal Server Error
            return ResponseEntity.internalServerError().body(null);
        }
    }

}
 
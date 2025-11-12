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

}
 
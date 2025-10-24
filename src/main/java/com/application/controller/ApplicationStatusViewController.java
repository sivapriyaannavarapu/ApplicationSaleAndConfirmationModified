package com.application.controller;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.entity.AppStatusTrackView;
import com.application.service.ApplicationStatusViewService;
 
@RestController
@RequestMapping("/api/application-status")
public class ApplicationStatusViewController {
 
    @Autowired

    private ApplicationStatusViewService applicationStatusViewService;
 
    @GetMapping("/getview/{cmpsId}")
    public ResponseEntity<List<AppStatusTrackView>> getStatusByCampusId(@PathVariable int cmpsId) {
        List<AppStatusTrackView> statusRecords = applicationStatusViewService.getApplicationStatusByCampus(cmpsId);

        if (statusRecords.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(statusRecords);
    }
    
    @GetMapping("/getview/employee-campus/{empId}")
    public ResponseEntity<List<AppStatusTrackView>> getStatusByEmployeeCampusId(@PathVariable int empId) {
        try {
            List<AppStatusTrackView> statusRecords = applicationStatusViewService.getApplicationStatusByEmployeeCampus(empId);
            return ResponseEntity.ok(statusRecords);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}

 
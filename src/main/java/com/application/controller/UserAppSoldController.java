package com.application.controller;
 
import com.application.dto.PerformanceDTO;
import com.application.dto.UserAppSoldDTO;
import com.application.service.UserAppSoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import java.util.List;
 
@RestController
// NOTE: I've updated the base path to be more general for all performance analytics
@RequestMapping("/api/performance")
public class UserAppSoldController {
 
	@Autowired
	private UserAppSoldService userAppSoldService;
 
	// --- OVERALL ZONE ENDPOINTS ---
 
	@GetMapping("/zones/top-rated")
	public List<PerformanceDTO> getTopRatedZones() {
		return userAppSoldService.getTopRatedZones();
	}
 
	@GetMapping("/zones/drop-rated")
	public List<PerformanceDTO> getDropRatedZones() {
		return userAppSoldService.getDropRatedZones();
	}
 
	// --- OVERALL CAMPUS ENDPOINTS ---
 
	@GetMapping("/employee/{employeeId}/campus/top-rated")
	public List<PerformanceDTO> getTopRatedCampuses(@PathVariable Integer employeeId) {
		return userAppSoldService.getTopRatedCampusesForEmployee(employeeId);
	}
 
	 @GetMapping("/employee/{employeeId}/campus/drop-rated")
	    public List<PerformanceDTO> getDropRatedCampusesForEmployee(@PathVariable Integer employeeId) {
	        return userAppSoldService.getDropRatedCampusesForEmployee(employeeId);
	    }
	
	// --- NEW: DGM RANKINGS FILTERED BY EMPLOYEE ---
 
    @GetMapping("/employee/{employeeId}/dgms/top-rated")
    public List<PerformanceDTO> getTopRatedDgmsForEmployee(@PathVariable Integer employeeId) {
        return userAppSoldService.getTopRatedDgmsForEmployee(employeeId);
    }
 
    @GetMapping("/employee/{employeeId}/dgms/drop-rated")
    public List<PerformanceDTO> getDropRatedDgmsForEmployee(@PathVariable Integer employeeId) {
        return userAppSoldService.getDropRatedDgmsForEmployee(employeeId);
    }
 
	// --- DETAILED ANALYTICS ENDPOINT ---
 
	@GetMapping("/entity/{entityId}")
	public List<UserAppSoldDTO> getAnalyticsByEntityId(@PathVariable Integer entityId) {
		return userAppSoldService.getAnalyticsByEntityId(entityId);
	}
}

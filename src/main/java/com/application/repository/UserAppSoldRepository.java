package com.application.repository;

import com.application.entity.UserAppSold;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAppSoldRepository extends JpaRepository<UserAppSold, Long> {

    List<UserAppSold> findByEntityId(Integer entityId);

    // --- OVERALL ZONE RANKINGS ---
    @Query(value = "SELECT z.zone_name, (CAST(SUM(s.sold) AS DECIMAL) / SUM(s.total_app_count)) * 100 AS performance " +
                   "FROM sce_application.sce_user_app_sold s " +
                   "JOIN sce_employee.sce_emp e ON s.emp_id = e.emp_id " +
                   "JOIN sce_campus.sce_cmps c ON e.cmps_id = c.cmps_id " +
                   "JOIN sce_locations.sce_zone z ON c.zone_id = z.zone_id " +
                   "WHERE s.is_active = 1 " +
                   "GROUP BY z.zone_id, z.zone_name ORDER BY performance DESC", nativeQuery = true)
    List<Object[]> findTopRatedZones(Pageable pageable);

    @Query(value = "SELECT z.zone_name, (CAST(SUM(s.sold) AS DECIMAL) / SUM(s.total_app_count)) * 100 AS performance " +
                   "FROM sce_application.sce_user_app_sold s " +
                   "JOIN sce_employee.sce_emp e ON s.emp_id = e.emp_id " +
                   "JOIN sce_campus.sce_cmps c ON e.cmps_id = c.cmps_id " +
                   "JOIN sce_locations.sce_zone z ON c.zone_id = z.zone_id " +
                   "WHERE s.is_active = 1 " +
                   "GROUP BY z.zone_id, z.zone_name ORDER BY performance ASC", nativeQuery = true)
    List<Object[]> findDropRatedZones(Pageable pageable);

    // --- DGM RANKINGS FILTERED BY ZONE EMPLOYEE ID ---
    @Query(value = "SELECT dgm_manager.first_name || ' ' || dgm_manager.last_name AS full_name, " +
                   "(CAST(SUM(s.sold) AS DECIMAL) / SUM(s.total_app_count)) * 100 AS performance " +
                   "FROM sce_application.sce_user_app_sold s " +
                   "JOIN sce_employee.sce_emp cashier ON s.emp_id = cashier.emp_id " +
                   "JOIN sce_campus.sce_cmps c ON cashier.cmps_id = c.cmps_id " +
                   "JOIN sce_application.sce_dgm d ON c.cmps_id = d.cmps_id " +
                   "JOIN sce_employee.sce_emp dgm_manager ON d.emp_id = dgm_manager.emp_id " +
                   "WHERE s.is_active = 1 AND d.zone_id = (SELECT cmps.zone_id FROM sce_employee.sce_emp e " +
                   "JOIN sce_campus.sce_cmps cmps ON e.cmps_id = cmps.cmps_id WHERE e.emp_id = :employeeId) " +
                   "GROUP BY d.dgm_id, dgm_manager.first_name, dgm_manager.last_name ORDER BY performance DESC", nativeQuery = true)
    List<Object[]> findTopRatedDgmsByManager(@Param("employeeId") Integer employeeId, Pageable pageable);

    @Query(value = "SELECT dgm_manager.first_name || ' ' || dgm_manager.last_name AS full_name, " +
                   "(CAST(SUM(s.sold) AS DECIMAL) / SUM(s.total_app_count)) * 100 AS performance " +
                   "FROM sce_application.sce_user_app_sold s " +
                   "JOIN sce_employee.sce_emp cashier ON s.emp_id = cashier.emp_id " +
                   "JOIN sce_campus.sce_cmps c ON cashier.cmps_id = c.cmps_id " +
                   "JOIN sce_application.sce_dgm d ON c.cmps_id = d.cmps_id " +
                   "JOIN sce_employee.sce_emp dgm_manager ON d.emp_id = dgm_manager.emp_id " +
                   "WHERE s.is_active = 1 AND d.zone_id = (SELECT cmps.zone_id FROM sce_employee.sce_emp e " +
                   "JOIN sce_campus.sce_cmps cmps ON e.cmps_id = cmps.cmps_id WHERE e.emp_id = :employeeId) " +
                   "GROUP BY d.dgm_id, dgm_manager.first_name, dgm_manager.last_name ORDER BY performance ASC", nativeQuery = true)
    List<Object[]> findDropRatedDgmsByManager(@Param("employeeId") Integer employeeId, Pageable pageable);

    // --- OVERALL CAMPUS RANKINGS ---
    @Query(value = "SELECT c.cmps_name, (CAST(SUM(s.sold) AS DECIMAL) / SUM(s.total_app_count)) * 100 AS performance " +
                   "FROM sce_application.sce_user_app_sold s " +
                   "JOIN sce_employee.sce_emp cashier ON s.emp_id = cashier.emp_id " +
                   "JOIN sce_campus.sce_cmps c ON cashier.cmps_id = c.cmps_id " +
                   "WHERE s.is_active = 1 AND c.zone_id = (SELECT cmps.zone_id FROM sce_employee.sce_emp e " +
                   "JOIN sce_campus.sce_cmps cmps ON e.cmps_id = cmps.cmps_id WHERE e.emp_id = :employeeId) " +
                   "GROUP BY c.cmps_id, c.cmps_name ORDER BY performance DESC", nativeQuery = true)
    List<Object[]> findTopRatedCampusesByManager(@Param("employeeId") Integer employeeId, Pageable pageable);

    @Query(value = "SELECT c.cmps_name, (CAST(SUM(s.sold) AS DECIMAL) / SUM(s.total_app_count)) * 100 AS performance " +
                   "FROM sce_application.sce_user_app_sold s " +
                   "JOIN sce_employee.sce_emp cashier ON s.emp_id = cashier.emp_id " +
                   "JOIN sce_campus.sce_cmps c ON cashier.cmps_id = c.cmps_id " +
                   "WHERE s.is_active = 1 AND c.zone_id = (SELECT cmps.zone_id FROM sce_employee.sce_emp e " +
                   "JOIN sce_campus.sce_cmps cmps ON e.cmps_id = cmps.cmps_id WHERE e.emp_id = :employeeId) " +
                   "GROUP BY c.cmps_id, c.cmps_name ORDER BY performance ASC", nativeQuery = true)
    List<Object[]> findDropRatedCampusesByManager(@Param("employeeId") Integer employeeId, Pageable pageable);
    
    @Query("SELECT a.year, SUM(u.totalAppCount), SUM(u.sold) " +
    	       "FROM UserAppSold u JOIN AcademicYear a ON u.acdcYearId = a.acdcYearId " +
    	       "WHERE u.isActive = 1 AND u.entityId = :entityId " +
    	       "GROUP BY a.year ORDER BY a.year ASC")
    	List<Object[]> getYearWiseIssuedAndSoldByEntity(int entityId);

    	
    	@Query("SELECT u.zone.zoneName, SUM(u.totalAppCount), SUM(u.sold) " +
    		       "FROM UserAppSold u " +
    		       "WHERE u.isActive = 1 AND u.entityId = 2 " +
    		       "GROUP BY u.zone.zoneName")
    		List<Object[]> getZoneWiseRates();

    		@Query("SELECT u.campus.campusName, SUM(u.totalAppCount), SUM(u.sold) " +
    			       "FROM UserAppSold u " +
    			       "WHERE u.isActive = 1 AND u.entityId = 4 " +
    			       "GROUP BY u.campus.campusName")
    			List<Object[]> getCampusWiseRates();

    			@Query("SELECT CONCAT(e.first_name, ' ', e.last_name), SUM(u.totalAppCount), SUM(u.sold) " +
    				       "FROM UserAppSold u JOIN Employee e ON u.empId = e.emp_id " +
    				       "WHERE u.isActive = 1 AND u.entityId = 3 " +
    				       "GROUP BY e.first_name, e.last_name")
    				List<Object[]> getDgmWiseRates();



}

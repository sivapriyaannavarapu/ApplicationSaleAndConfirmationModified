package com.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.dto.GenericDropdownDTO;
import com.application.entity.Dgm;
import com.application.entity.Employee;

@Repository
public interface DgmRepository extends JpaRepository<Dgm, Integer> {
  
	@Query("SELECT d FROM Dgm d WHERE d.dgm_id = :dgmId")
    List<Dgm> findByDgmId(@Param("dgmId") int dgmId);
	
	 @Query("SELECT d FROM Dgm d WHERE d.zone.zoneId = :zoneId")
	    List<Dgm> findByZoneId(@Param("zoneId") int zoneId);
	 
	    @Query("SELECT d FROM Dgm d WHERE d.campus.campusId = :campusId")
	    List<Dgm> findByCampusId(@Param("campusId") int campusId);
	    
	    @Query("SELECT d.employee FROM Dgm d")
	    List<Employee> findAllDgmEmployees();
	    
	    @Query("SELECT DISTINCT new com.application.dto.GenericDropdownDTO(e.emp_id, CONCAT(e.first_name, ' ', e.last_name)) "
	            + "FROM Dgm d "
	            + "JOIN d.employee e "
	            + "WHERE d.zone.zoneId = :zoneId "
	            + "AND e.isActive = 1") 
	       List<GenericDropdownDTO> findDistinctActiveEmployeesByZoneId(@Param("zoneId") int zoneId);
	    
	    @Query("SELECT DISTINCT new com.application.dto.GenericDropdownDTO(c.campusId, c.campusName) " +
	            "FROM Dgm d " +
	            "JOIN d.campus c " +
	            "WHERE c.isActive = 1")
	     List<GenericDropdownDTO> findDistinctActiveCampusesByDgm();
	    
	    @Query("SELECT d FROM Dgm d WHERE d.employee.emp_id = :empId")
	    List<Dgm> findByEmpId(@Param("empId") Integer empId);
	    
	    
	    @Query("SELECT DISTINCT d.campus.campusId FROM Dgm d WHERE d.zone.zoneId IN :zoneIds")
	    List<Integer> findCampusIdsByZoneIds(List<Integer> zoneIds);


}
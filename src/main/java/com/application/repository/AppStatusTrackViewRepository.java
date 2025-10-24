package com.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.entity.AppStatusTrackView;

@Repository
public interface AppStatusTrackViewRepository extends JpaRepository<AppStatusTrackView, Integer>{
	
	Optional<AppStatusTrackView> findByNum(int num);
	@Query("SELECT a FROM AppStatusTrackView a WHERE a.cmps_id = :cmpsId")
    List<AppStatusTrackView> findByCmps_id(@Param("cmpsId") int cmpsId);
	
	@Query("SELECT a FROM AppStatusTrackView a WHERE a.cmps_id = " +
	           "(SELECT e.campus.campusId FROM Employee e WHERE e.emp_id = :empId)")
	    List<AppStatusTrackView> findByEmployeeCampus(@Param("empId") int empId);
}

package com.application.repository;
 

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.dto.ApplicationStartEndDto;
import com.application.entity.StateApp;
 
@Repository
public interface StateAppRepository extends JpaRepository<StateApp, Integer> {
 
    @Query("SELECT s FROM StateApp s WHERE s.state.stateId = :stateId AND s.created_by = :userId AND s.academicYear.acdcYearId = :academicYearId")
    Optional<StateApp> findStartNumber(@Param("stateId") int stateId, @Param("userId") int userId, @Param("academicYearId") int academicYearId);

    @Query("SELECT s.app_end_no FROM StateApp s WHERE s.state.stateId = :stateId AND s.created_by = :userId")
    Integer findAppEndNoByStateAndUser(@Param("stateId") int stateId, @Param("userId") int userId);
    
 // Revised findAppNumberRanges method in StateAppRepository
    @Query("SELECT new com.application.dto.ApplicationStartEndDto(s.app_start_no, s.app_end_no) " +
            "FROM StateApp s " +
            "WHERE s.academicYear.acdcYearId = :academicYearId " +
            "AND s.state.stateId = :stateId")
     Optional<ApplicationStartEndDto> findAppNumberRanges(
         @Param("academicYearId") int academicYearId,
         @Param("stateId") int stateId);
    
//    @Query("SELECT new com.application.dto.ApplicationStartEndDto(s.app_start_no, s.app_end_no) " +
//            "FROM StateApp s " +
//            "WHERE s.academicYear.acdcYearId = :academicYearId " +
//            "AND s.state.stateId = :stateId")
//     Optional<ApplicationStartEndDto> findRangeByAcademicYearAndState( // New method name
//         @Param("academicYearId") int academicYearId,
//         @Param("stateId") int stateId);
    
    @Query("SELECT new com.application.dto.ApplicationStartEndDto(s.app_start_no, s.app_end_no) " +
	           "FROM StateApp s " +
	           "WHERE s.academicYear.acdcYearId = :academicYearId " +
	           "AND s.state.stateId = :stateId")
	    Optional<ApplicationStartEndDto> findRangeByAcademicYearAndState( // New method name
	        @Param("academicYearId") int academicYearId,
	        @Param("stateId") int stateId);
	
    
    @Query("SELECT s FROM StateApp s WHERE :admissionNo BETWEEN s.app_start_no AND s.app_end_no")
    List<StateApp> findAllByAdmissionNoBetweenRange(@Param("admissionNo") int admissionNo);
    
    @Query("SELECT s FROM StateApp s WHERE :admissionNo BETWEEN s.app_start_no AND s.app_end_no")
    Optional<StateApp> findByAdmissionNoInRange(@Param("admissionNo") Long admissionNo);
    
 // In StateAppRepository.java

 // In StateAppRepository.java

    @Query("SELECT sa FROM StateApp sa WHERE :appNo BETWEEN sa.app_start_no AND sa.app_end_no AND sa.academicYear.acdcYearId = :academicYearId")
    Optional<StateApp> findStateAppByAppNoRangeAndAcademicYear(
            @Param("appNo") Long appNo,
            @Param("academicYearId") int academicYearId);




    
    

}
// src/main/java/com/application/repository/BalanceTrackRepository.java
package com.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.dto.AppFromDTO;
import com.application.entity.AcademicYear;
import com.application.entity.BalanceTrack;

@Repository
public interface BalanceTrackRepository extends JpaRepository<BalanceTrack, Integer> {
    
    // Existing query
    @Query("SELECT b FROM BalanceTrack b WHERE b.academicYear.acdcYearId = :academicYearId AND b.employee.emp_id = :employeeId")
    Optional<BalanceTrack> findBalanceTrack(
        @Param("academicYearId") int academicYearId,
        @Param("employeeId") int employeeId
    );
    
    // New query to fetch all available application number ranges for a user
    @Query("SELECT b FROM BalanceTrack b WHERE b.academicYear.acdcYearId = :academicYearId AND b.employee.emp_id = :employeeId")
    List<BalanceTrack> findAppNumberRanges(
        @Param("academicYearId") int academicYearId, 
        @Param("employeeId") int employeeId
    );
    
    Optional<BalanceTrack> findByCreatedByAndAcademicYear(int createdBy, AcademicYear academicYear);
    
    @Query("SELECT new com.application.dto.AppFromDTO(b.appFrom, b.appBalanceTrkId) FROM BalanceTrack b " +
            "WHERE b.employee.id = :employeeId AND b.academicYear.id = :academicYearId")
     Optional<AppFromDTO> getAppFromByEmployeeAndAcademicYear(
            @Param("employeeId") int employeeId, 
            @Param("academicYearId") int academicYearId);

}
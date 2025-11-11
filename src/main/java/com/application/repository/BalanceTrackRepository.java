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

    @Query("SELECT b FROM BalanceTrack b WHERE b.academicYear.acdcYearId = :academicYearId AND b.employee.id = :employeeId AND b.isActive = 1")
    Optional<BalanceTrack> findBalanceTrack(@Param("academicYearId") int academicYearId,
                                            @Param("employeeId") int employeeId);

    @Query("SELECT b FROM BalanceTrack b WHERE b.academicYear.acdcYearId = :academicYearId AND b.employee.id = :employeeId AND b.isActive = 1")
    List<BalanceTrack> findAppNumberRanges(@Param("academicYearId") int academicYearId,
                                           @Param("employeeId") int employeeId);

    @Query("SELECT b FROM BalanceTrack b WHERE b.createdBy = :createdBy AND b.academicYear = :academicYear AND b.isActive = 1")
    Optional<BalanceTrack> findByCreatedByAndAcademicYear(@Param("createdBy") int createdBy,
                                                          @Param("academicYear") AcademicYear academicYear);

    @Query("SELECT new com.application.dto.AppFromDTO(b.appFrom, b.appBalanceTrkId) FROM BalanceTrack b WHERE b.employee.id = :employeeId AND b.academicYear.id = :academicYearId AND b.isActive = 1")
    Optional<AppFromDTO> getAppFromByEmployeeAndAcademicYear(@Param("employeeId") int employeeId,
                                                             @Param("academicYearId") int academicYearId);

    @Query("SELECT bt FROM BalanceTrack bt WHERE :appNo BETWEEN bt.appFrom AND bt.appTo AND bt.isActive = 1 AND bt.issuedByType.appIssuedId = 4")
    Optional<BalanceTrack> findActiveBalanceTrackByAppNoRange(@Param("appNo") Long appNo);

}

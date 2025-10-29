//package com.application.repository;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import com.application.entity.Distribution;
//
//@Repository
//public interface DistributionRepository extends JpaRepository<Distribution, Integer> {
//
//    @Query(value = "SELECT MAX(d.app_end_no) FROM sce_application.sce_app_distrubution d WHERE d.state_id = :stateId AND d.created_by = :userId AND d.acdc_year_id = :academicYearId", nativeQuery = true)
//    Integer findMaxAppEndNo(@Param("stateId") int stateId, @Param("userId") int userId, @Param("academicYearId") int academicYearId);
//    
//    @Query(value = "SELECT * FROM sce_application.sce_app_distrubution d WHERE d.created_by = :empId", nativeQuery = true)
//    List<Distribution> findByCreatedBy(@Param("empId") int empId); 
//    
// // In DistributionRepository.java
////    Optional<Integer> findMaxAppEndNoByIssuedToEmpIdAndAcademicYearId(int empId, int acdYearId);
//    
//    @Query("SELECT MAX(d.appEndNo) FROM Distribution d WHERE d.issued_to_emp_id = :empId AND d.academicYear.acdcYearId = :acdYearId AND d.isActive = 1")
//    Optional<Integer> findMaxAppEndNoByIssuedToEmpIdAndAcademicYearId(@Param("empId") int empId, @Param("acdYearId") int acdYearId);
//    
//    @Query("SELECT MIN(d.appStartNo) FROM Distribution d WHERE d.issued_to_emp_id = :empId AND d.academicYear.acdcYearId = :acdYearId AND d.isActive = 1")
//    Optional<Integer> findMinAppStartNoByIssuedToEmpIdAndAcademicYearId(@Param("empId") int empId, @Param("acdYearId") int acdYearId);
//}
package com.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.dto.AppDistributionDTO;
import com.application.dto.ApplicationStartEndDto;
import com.application.entity.Distribution;

@Repository
public interface DistributionRepository extends JpaRepository<Distribution, Integer> {

	// FIX: Added AND d.is_active = 1
	@Query(value = "SELECT MAX(d.app_end_no) FROM sce_application.sce_app_distrubution d WHERE d.state_id = :stateId AND d.created_by = :userId AND d.acdc_year_id = :academicYearId AND d.is_active = 1", nativeQuery = true)
	Integer findMaxAppEndNo(@Param("stateId") int stateId, @Param("userId") int userId,
			@Param("academicYearId") int academicYearId);

	@Query(value = "SELECT * FROM sce_application.sce_app_distrubution d WHERE d.created_by = :empId", nativeQuery = true)
	List<Distribution> findByCreatedBy(@Param("empId") int empId);

	// --- EXISTING METHODS (ALREADY CORRECT) ---
	@Query("SELECT MAX(d.appEndNo) FROM Distribution d WHERE d.issued_to_emp_id = :empId AND d.academicYear.acdcYearId = :acdYearId AND d.isActive = 1")
	Optional<Integer> findMaxAppEndNoByIssuedToEmpIdAndAcademicYearId(@Param("empId") int empId,
			@Param("acdYearId") int acdYearId);

	@Query("SELECT MIN(d.appStartNo) FROM Distribution d WHERE d.issued_to_emp_id = :empId AND d.academicYear.acdcYearId = :acdYearId AND d.isActive = 1")
	Optional<Integer> findMinAppStartNoByIssuedToEmpIdAndAcademicYearId(@Param("empId") int empId,
			@Param("acdYearId") int acdYearId);

	// --- REQUIRED METHODS (FIX: Added AND d.isActive = 1) ---
	@Query("SELECT SUM(d.totalAppCount) FROM Distribution d WHERE d.issued_to_emp_id = :employeeId AND d.academicYear.acdcYearId = :academicYearId AND d.isActive = 1")
	Optional<Integer> sumTotalAppCountByIssuedToEmpId(@Param("employeeId") int employeeId,
			@Param("academicYearId") int academicYearId);

	@Query("SELECT SUM(d.totalAppCount) FROM Distribution d WHERE d.created_by = :employeeId AND d.academicYear.acdcYearId = :academicYearId AND d.isActive = 1")
	Optional<Integer> sumTotalAppCountByCreatedBy(@Param("employeeId") int employeeId,
			@Param("academicYearId") int academicYearId);

	// FIX: Added AND d.isActive = 1
	@Query("SELECT d FROM Distribution d " + "WHERE d.academicYear.acdcYearId = :academicYearId "
			+ "AND d.appStartNo <= :endNo AND d.appEndNo >= :startNo AND d.isActive = 1")
	List<Distribution> findOverlappingDistributions(@Param("academicYearId") int academicYearId,
			@Param("startNo") int startNo, @Param("endNo") int endNo);

	@Query("SELECT d FROM Distribution d WHERE :admissionNo >= d.appStartNo AND :admissionNo <= d.appEndNo AND d.issuedToType.appIssuedId = 4 AND d.isActive = 1")
	Optional<Distribution> findProDistributionForAdmissionNumber(@Param("admissionNo") long admissionNo);

	@Query("SELECT MAX(d.appEndNo) FROM Distribution d WHERE d.created_by = :employeeId AND d.academicYear.acdcYearId = :academicYearId AND d.isActive = 1")
	Optional<Integer> findMaxAppEndNoByCreatedByAndAcademicYearId(@Param("employeeId") int employeeId,
			@Param("academicYearId") int academicYearId);

	@Query("SELECT new com.application.dto.AppDistributionDTO(d.appStartNo, d.appEndNo) " + "FROM Distribution d "
			+ "WHERE d.issued_to_emp_id = :issuedToEmpId " + "AND d.academicYear.acdcYearId = :academicYearId "
			+ "AND d.isActive = 1")
	Optional<AppDistributionDTO> findActiveAppRangeByEmployeeAndAcademicYear(@Param("issuedToEmpId") int issuedToEmpId,
			@Param("academicYearId") int academicYearId);

	@Query("SELECT new com.application.dto.AppDistributionDTO(d.appStartNo, d.appEndNo) " + "FROM Distribution d "
			+ "WHERE d.issued_to_emp_id = :issuedToEmpId " + "AND d.academicYear.acdcYearId = :academicYearId "
			+ "AND d.isActive = 1 " + "AND (:cityId IS NULL OR d.city.id = :cityId)") // Added conditional cityId filter
	Optional<AppDistributionDTO> findActiveAppRangeByEmployeeAndAcademicYear(@Param("issuedToEmpId") int issuedToEmpId,
			@Param("academicYearId") int academicYearId, @Param("cityId") Integer cityId); // Added cityId parameter

	@Query("SELECT MAX(d.appEndNo) + 1 FROM Distribution d " + "WHERE d.academicYear.acdcYearId = :academicYearId "
			+ "AND d.state.stateId = :stateId " + "AND d.issuedByType.appIssuedId = 1 " + // Issued By Type ID must be 1
			"AND d.isActive = 1")
	Optional<Integer> findNextStartNumberByStateAndIssuerType(@Param("academicYearId") int academicYearId,
			@Param("stateId") int stateId);

	// RENAMED METHOD: Finds the maximum App End No in a state's active
	// distributions,
	// regardless of the issuer type (since the restrictive filter was removed).
//	    @Query("SELECT MAX(d.appEndNo) + 1 FROM Distribution d " +
//	           "WHERE d.academicYear.acdcYearId = :academicYearId " +
//	           "AND d.state.stateId = :stateId " + 
//	           "AND d.isActive = 1")
//	    Optional<Integer> findMaxAppEndNoByAcademicYearAndState( // New method name
//	        @Param("academicYearId") int academicYearId,
//	        @Param("stateId") int stateId);

	@Query("SELECT MAX(d.appEndNo) + 1 FROM Distribution d " + "WHERE d.academicYear.acdcYearId = :academicYearId "
			+ "AND d.state.stateId = :stateId " + "AND d.issuedByType.appIssuedId IN (1, 2, 3) " + "AND d.isActive = 1")
	Optional<Integer> findMaxAppEndNoByAcademicYearAndState(@Param("academicYearId") int academicYearId,
			@Param("stateId") int stateId);

	@Query("SELECT d FROM Distribution d " + "WHERE :admissionNo BETWEEN d.appStartNo AND d.appEndNo "
			+ "AND d.issuedToType.appIssuedId = :issuedToTypeId " + "AND d.isActive = 1")
	List<Distribution> findByAdmissionNoRangeAndIssuedToType(@Param("admissionNo") int admissionNo,
			@Param("issuedToTypeId") int issuedToTypeId);

	@Query("SELECT d FROM Distribution d WHERE :applicationNo BETWEEN d.appStartNo AND d.appEndNo AND d.isActive = 1")
	Optional<Distribution> findActiveByApplicationNoInRange(@Param("applicationNo") int applicationNo);

}
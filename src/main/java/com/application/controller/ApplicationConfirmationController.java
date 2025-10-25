package com.application.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.dto.ApiResponse;
import com.application.dto.BatchDTO;
import com.application.dto.CampusDropdownDTO;
import com.application.dto.OccupationSectorDropdownDTO;
import com.application.dto.OrientationBatchDetailsDTO;
import com.application.dto.OrientationDropdownDTO;
import com.application.dto.StudentConfirmationDTO;
import com.application.entity.BloodGroup;
import com.application.entity.ConcessionReason;
import com.application.entity.District;
import com.application.entity.FoodType;
import com.application.entity.Gender;
import com.application.entity.State;
import com.application.entity.StudentAcademicDetails;
import com.application.entity.StudentClass;
import com.application.entity.StudentRelation;
import com.application.entity.StudentType;
//import com.application.service.ApplicationConfirmationService;
import com.application.service.ApplicationNewConfirmationService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/application-confirmation")
//@CrossOrigin(origins = "*")
public class ApplicationConfirmationController {
    
//    @Autowired
//    private ApplicationConfirmationService service;
    
    @Autowired ApplicationNewConfirmationService confirmationService;
//    
//    @PostMapping("/save")
//    public ResponseEntity<String> saveAdmission(@RequestBody ApplicationConfirmationDto dto) {
//        try {
//            service.saveOrUpdateAdmission(dto);
//            return ResponseEntity.ok("Admission details saved successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("Error: " + e.getMessage());
//        }
//    }
    
//    @GetMapping("/join-years")
//    public ResponseEntity<List<AcademicYear>> getJoinYears() {
//        try {
//            return ResponseEntity.ok(service.getJoinYears());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//    
////    @GetMapping("/batch/{batchId}/dates")
////    public ResponseEntity<BatchDatesResponse> getBatchDates(@PathVariable Integer batchId) {
////        BatchDatesResponse response = service.getCourseBatchDetails(batchId);
////        return ResponseEntity.ok(response);
////    }
//
//    // 2. Get Course Fee by CourseTrackId
////    @GetMapping("/{admissionNo}/campus-zone")
////    public ResponseEntity<CampusAndZoneDTO> getCampusAndZoneByAdmissionNo(@PathVariable String admissionNo) {
////        CampusAndZoneDTO campusAndZone = service.getCampusAndZoneByAdmissionNo(admissionNo);
////
////        if (campusAndZone != null) {
////            return ResponseEntity.ok(campusAndZone);
////        } else {
////            return ResponseEntity.notFound().build();
////        }
////    }
//    
//    @GetMapping("/course-fee/campus/{cmpsId}/track/{courseTrackId}/batch/{courseBatchId}")
//    public ResponseEntity<Float> getCourseFee(
//            @PathVariable int cmpsId,
//            @PathVariable int courseTrackId,
//            @PathVariable int courseBatchId) {
//
//        Optional<Float> courseFee = service.getCourseFeeByDetails(cmpsId, courseTrackId, courseBatchId);
//
//        return courseFee.map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//    
////    @GetMapping("/details/{admissionNo}")
////    public ResponseEntity<StudentDetailsDTO> getStudentDetailsByAdmission(@PathVariable String admissionNo) {
////        return service.getStudentDetailsByAdmissionNo(admissionNo)
////                .map(ResponseEntity::ok)
////                .orElse(ResponseEntity.notFound().build());
////    }
////    
////    
//    @GetMapping("/streams")
//    public ResponseEntity<List<Stream>> getStreams() {
//        try {
//            return ResponseEntity.ok(service.getStreams());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//    
//    @GetMapping("/programs")
//    public ResponseEntity<List<ProgramName>> getPrograms() {
//        try {
//            return ResponseEntity.ok(service.getPrograms());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//    
//    @GetMapping("/examprograms")
//    public List<ExamProgram> getAllExamPrograms() {
//        return service.getAllExamPrograms();
//    }
//    
//    @GetMapping("/course-tracks")
//    public ResponseEntity<List<Orientation>> getCourseTracks() {
//        try {
//            return ResponseEntity.ok(service.getCourseTracks());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//    
//    @GetMapping("/course-batches")
//    public ResponseEntity<List<OrientationBatch>> getCourseBatches() {
//        try {
//            return ResponseEntity.ok(service.getCourseBatches());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//    
//    @GetMapping("/sections")
//    public ResponseEntity<List<Section>> getSections() {
//        try {
//            return ResponseEntity.ok(service.getSections());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//    
//    @GetMapping("/concession-reasons")
//    public ResponseEntity<List<ConcessionReason>> getConcessionReasons() {
//        try {
//            return ResponseEntity.ok(service.getConcessionReasons());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//    
////    @GetMapping("/employee-details")
////    public ResponseEntity<EmployeeDetailsDTO> getEmployeeDetails(@RequestParam String admissionNo) {
////        Optional<EmployeeDetailsDTO> employeeDetails = service.getEmployeeDetailsByAdmissionNo(admissionNo);
////        return employeeDetails.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
////                              .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
////    }
////    
////    @GetMapping("/getProgramsByStream")
////    public List<ProgramName> getProgramsByStream(@RequestParam int streamId) {
////        return service.getProgramsByStream(streamId);
////    }
//    
////    @GetMapping("/by-course-track/id/{courseTrackId}")
////    public List<Stream> getStreamsByCourseTrackId(@PathVariable int courseTrackId) {
////        return service.getStreamsByCourseTrackId(courseTrackId);
////    }
//    
////    @GetMapping("/getbatches/{courseTrackId}")
////    public List<CourseBatch> getCourseBatchesByCourseTrackId(@PathVariable int courseTrackId) {
////        return service.getCourseBatchesByCourseTrackId(courseTrackId);
////    }
//    
//    @GetMapping("/examPrograms/{programId}")
//    public List<ExamProgramDTO> getExamProgramsByProgram(@PathVariable int programId) {
//        return service.getExamProgramsByProgramId(programId);
//    }
//    
//    @GetMapping("/dropdownforjoinyear")
//    public Map<String, Object> getAcademicYears() {
//        return service.getDropdownAcademicYears();
//    }
//    
//    @GetMapping("/getalllanguages")
//    public List<Language> getAllLanguages()
//    {
//    	return service.getAllLanguages();
//    }
//    
//    @GetMapping("/getallfoodtypes")
//    public List<FoodType> getAllFoodTypes()
//    {
//    	return service.getAllFoodTypes();
//    }
//    
//    @GetMapping("/campus/{campusId}/getorientation")
//    public List<OrientationDTO> getOrientations(@PathVariable int campusId) {
//        return service.getOrientationsByCampusId(campusId);
//    }
//
//    @GetMapping("/orientation/{orientationId}/getstreams")
//    public List<StreamDTO> getStreams(@PathVariable int orientationId) {
//        return service.getStreamsByOrientationId(orientationId);
//    }
//    
//    @GetMapping("/orientation/{orientationId}/programs")
//    public List<ProgramDTO> getProgramsByOrientationId(@PathVariable int orientationId) {
//        return service.getProgramsByOrientationId(orientationId);
//    }
//
//    // Endpoint to get exam programs by a specific program ID
//    @GetMapping("/program/{programId}/exam-programs")
//    public List<ExamProgramDTO> getExamProgramsByProgramId(@PathVariable int programId) {
//        return service.getExamProgramsByProgramId(programId);
//    }
//    
//    @GetMapping("/orientation/{orientationId}/batches")
//    public List<BatchDTO> getBatchesByOrientationId(@PathVariable int orientationId) {
//        return service.getBatchesByOrientationId(orientationId);
//    }
//
//    // Endpoint to get detailed information about a single batch by its ID
//    @GetMapping("/details/by-orientation/{orientationId}/by-batch/{orientationBatchId}")
//    public ResponseEntity<List<BatchDetailsDTO>> getBatchDetails(
//            @PathVariable int orientationId,
//            @PathVariable int orientationBatchId) {
//        
//        List<BatchDetailsDTO> batchDetails = service.getBatchDetails(orientationId, orientationBatchId);
//
//        if (batchDetails.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok(batchDetails);
//    }
//
//
//    // Endpoint to get sections associated with a batch name
//    @GetMapping("/by-id/{batchId}/sections")
//    public List<SectionDTO> getSectionsByBatchId(@PathVariable int batchId) {
//        return service.getSectionsByBatchId(batchId);
//    }
//    
//    @GetMapping("/getprogram/{streamId}")
//    public ResponseEntity<List<ProgramDTO>> getProgramsByStreamId(@PathVariable int streamId) {
//        
//        List<ProgramDTO> programs = service.getProgramsByStreamId(streamId);
//
//        if (programs.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok(programs);
//    }
    
    @GetMapping("/dropdown/relation/type")
    public ResponseEntity<ApiResponse<List<StudentRelation>>> getStudentRelations() {
        try {
            List<StudentRelation> relations = confirmationService.getActiveStudentRelations();
            return ResponseEntity.ok(
                ApiResponse.success(relations, "Relations fetched successfully.")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch relations: " + e.getMessage()));
        }
    }
    
    @GetMapping("/dropdown/classes")
    public ResponseEntity<ApiResponse<List<StudentClass>>> getClasses() {
        try {
            List<StudentClass> classes = confirmationService.getActiveClasses();
            return ResponseEntity.ok(
                ApiResponse.success(classes, "Classes fetched successfully.")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch classes: " + e.getMessage()));
        }
    }
    
    @GetMapping("/dropdown/genders")
    public ResponseEntity<ApiResponse<List<Gender>>> getGenders() {
        try {
            List<Gender> genders = confirmationService.getActiveGenders();
            return ResponseEntity.ok(
                ApiResponse.success(genders, "Genders fetched successfully.")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch genders: " + e.getMessage()));
        }
    }
    
    @GetMapping("/dropdown/batches")
    public ResponseEntity<ApiResponse<List<BatchDTO>>> getOrientationBatches(
            @RequestParam Integer orientationId) {
        try {
            if (orientationId == null) {
                 return ResponseEntity.badRequest()
                    .body(ApiResponse.error("orientationId parameter is required."));
            }
            List<BatchDTO> batches = confirmationService.getBatchesByOrientation(orientationId);
            return ResponseEntity.ok(
                ApiResponse.success(batches, "Batches fetched successfully for orientation ID: " + orientationId)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch batches: " + e.getMessage()));
        }
    }
    
    @GetMapping("/dropdown/states")
    public ResponseEntity<ApiResponse<List<State>>> getStates() {
        try {
            List<State> states = confirmationService.getActiveStates();
            return ResponseEntity.ok(
                ApiResponse.success(states, "States fetched successfully.")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch states: " + e.getMessage()));
        }
    }
    
    @GetMapping("/dropdown/districts")
    public ResponseEntity<ApiResponse<List<District>>> getDistricts(
            @RequestParam Integer stateId) { // Use @RequestParam to get ID from URL
        try {
            if (stateId == null) {
                 return ResponseEntity.badRequest()
                    .body(ApiResponse.error("stateId parameter is required."));
            }
            List<District> districts = confirmationService.getActiveDistrictsByState(stateId);
            return ResponseEntity.ok(
                ApiResponse.success(districts, "Districts fetched successfully for state ID: " + stateId)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch districts: " + e.getMessage()));
        }
    }
    
    @GetMapping("/dropdown/studytypes")
    public ResponseEntity<ApiResponse<List<StudentType>>> getStudyTypes() {
        try {
            List<StudentType> studyTypes = confirmationService.getActiveStudyTypes();
            return ResponseEntity.ok(
                ApiResponse.success(studyTypes, "Study types fetched successfully.")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch study types: " + e.getMessage()));
        }
    }
    
    @GetMapping("/dropdown/foodtypes")
    public ResponseEntity<ApiResponse<List<FoodType>>> getFoodTypes() {
        try {
            List<FoodType> studyTypes = confirmationService.getFoodTypes();
            return ResponseEntity.ok(
                ApiResponse.success(studyTypes, "Study types fetched successfully.")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch study types: " + e.getMessage()));
        }
    }
    
    @GetMapping("/dropdown/bloodGrouptypes")
    public ResponseEntity<ApiResponse<List<BloodGroup>>> getBloodGroupTypes() {
        try {
            List<BloodGroup> studyTypes = confirmationService.getBloodGroupTypes();
            return ResponseEntity.ok(
                ApiResponse.success(studyTypes, "Study types fetched successfully.")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch study types: " + e.getMessage()));
        }
    }
    
    @GetMapping("/dropdown/concessionReasontypes")
    public ResponseEntity<ApiResponse<List<ConcessionReason>>> getConcessionReasonTypes() {
        try {
            List<ConcessionReason> studyTypes = confirmationService.getConcessionReasonTypes();
            return ResponseEntity.ok(
                ApiResponse.success(studyTypes, "Study types fetched successfully.")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch study types: " + e.getMessage()));
        }
    }
    
    @GetMapping("/orientation/batch/combo/details")
    public ResponseEntity<ApiResponse<OrientationBatchDetailsDTO>> getOrientationBatchComboDetails(
            @RequestParam Integer orientationId,
            @RequestParam Integer batchId) {
        try {
            if (orientationId == null || batchId == null) {
                 return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Both orientationId and batchId parameters are required."));
            }
            // Call the new service method
            Optional<OrientationBatchDetailsDTO> detailsOpt =
                confirmationService.getDetailsByOrientationAndBatch(orientationId, batchId);

            if (detailsOpt.isPresent()) {
                 return ResponseEntity.ok(
                    ApiResponse.success(detailsOpt.get(), "Details fetched successfully for orientation/batch combo.")
                 );
            } else {
                 return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Details not found for Orientation ID: " + orientationId + " and Batch ID: " + batchId));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch orientation/batch details: " + e.getMessage()));
        }
    }
    
    @GetMapping("/dropdown/occupations")
    public ResponseEntity<ApiResponse<List<OccupationSectorDropdownDTO>>> getOccupations() {
        try {
            List<OccupationSectorDropdownDTO> occupations = confirmationService.getUniqueOccupations();
            return ResponseEntity.ok(
                ApiResponse.success(occupations, "Occupations fetched successfully.")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch occupations: " + e.getMessage()));
        }
    }

    // --- UPDATE RESPONSE TYPE ---
    @GetMapping("/dropdown/sectors")
    public ResponseEntity<ApiResponse<List<OccupationSectorDropdownDTO>>> getSectors() {
        try {
            List<OccupationSectorDropdownDTO> sectors = confirmationService.getUniqueSectors();
            return ResponseEntity.ok(
                ApiResponse.success(sectors, "Sectors fetched successfully.")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch sectors: " + e.getMessage()));
        }
    }
    
    @GetMapping("/dropdown/orientations")
    public ResponseEntity<ApiResponse<List<OrientationDropdownDTO>>> getOrientations(
            @RequestParam Integer campusId, // Keep campusId
            @RequestParam Integer classId) {  // <-- ADD classId parameter
        try {
            // Check both parameters
            if (campusId == null || classId == null) {
                 return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Both campusId and classId parameters are required."));
            }
            // --- MODIFIED: Pass both IDs to service ---
            List<OrientationDropdownDTO> orientations =
                confirmationService.getOrientationsByCampusAndClass(campusId, classId);
 
            return ResponseEntity.ok(
                ApiResponse.success(orientations, "Orientations fetched successfully for campus ID: " + campusId + " and class ID: " + classId)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch orientations: " + e.getMessage()));
        }
    }
    
    @GetMapping("/dropdown/campuses")
    public ResponseEntity<ApiResponse<List<CampusDropdownDTO>>> getCampusesByType(
            @RequestParam String businessType) { // Parameter name matching the frontend
        try {
            if (businessType == null || businessType.isBlank()) {
                 return ResponseEntity.badRequest()
                    .body(ApiResponse.error("businessType parameter is required."));
            }
            List<CampusDropdownDTO> campuses = confirmationService.getCampusesByBusinessType(businessType.toUpperCase()); // Convert to uppercase to be safe
            return ResponseEntity.ok(
                ApiResponse.success(campuses, "Campuses fetched successfully for type: " + businessType)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch campuses: " + e.getMessage()));
        }
    }
                 
    
    @PostMapping("/confirm")
    public ResponseEntity<ApiResponse<?>> saveStudentConfirmation(
            @RequestBody StudentConfirmationDTO dto) {
        
        try {
            // This is the service method we built
            StudentAcademicDetails savedStudent = confirmationService.saveOrUpdateConfirmation(dto);
            
            // Send a 200 OK response with the student data
            return ResponseEntity.ok(
                ApiResponse.success(savedStudent, "Student confirmation saved successfully.")
            );
            
        } catch (EntityNotFoundException e) {
            // This catches errors like "Student not found", "Status not found", etc.
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
                    
        } catch (DataIntegrityViolationException e) {
            // This will catch any future "NOT NULL" constraint errors
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Database error: " + e.getMostSpecificCause().getMessage()));

        } catch (Exception e) {
            // This catches all other unexpected errors
            e.printStackTrace(); // Log the full error for debugging
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("An unexpected error occurred: " + e.getMessage()));
        }
    }

}
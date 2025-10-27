package com.application.service;
 
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.dto.AddressDetailsDTO;
import com.application.dto.BankDetailsDTO;
import com.application.dto.CampusAndZoneDTO;
import com.application.dto.CampusDetailsDTO;
import com.application.dto.ClassDTO;
import com.application.dto.ConcessionTypeDTO;
import com.application.dto.CourseFeeDTO;
import com.application.dto.GenericDropdownDTO;
import com.application.dto.OrientationBatchDetailsDTO;
import com.application.dto.OrientationDTO;
import com.application.dto.OrientationResponseDTO;
import com.application.dto.PaymentDetailsDTO;
import com.application.dto.PinCodeLocationDTO;
import com.application.dto.StudentAdmissionDTO;
import com.application.dto.StudentSaleDTO;
import com.application.entity.Campus;
import com.application.entity.CmpsOrientation;
import com.application.entity.CmpsOrientationBatchFeeView;
import com.application.entity.Distribution;
import com.application.entity.Employee;
import com.application.entity.OrganizationBankDetails;
import com.application.entity.ParentDetails;
import com.application.entity.PaymentDetails;
import com.application.entity.StateApp;
import com.application.entity.Status;
import com.application.entity.StudentAcademicDetails;
import com.application.entity.StudentAddress;
import com.application.entity.StudentClass;
import com.application.entity.StudentOrientationDetails;
import com.application.entity.StudentPersonalDetails;
import com.application.entity.StudentRelation;
import com.application.entity.StudyType;
import com.application.repository.AcademicYearRepository;
import com.application.repository.AdmissionTypeRepository;
import com.application.repository.BloodGroupRepository;
import com.application.repository.CampusDetailsRepository;
import com.application.repository.CampusRepository;
import com.application.repository.CampusSchoolTypeRepository;
import com.application.repository.CasteRepository;
import com.application.repository.CityRepository;
import com.application.repository.CmpsOrientationBatchFeeViewRepository;
import com.application.repository.CmpsOrientationProgramViewRepository;
import com.application.repository.CmpsOrientationRepository;
import com.application.repository.CmpsOrientationStreamViewRepository;
import com.application.repository.ConcessionReasonRepository;
import com.application.repository.ConcessionTypeRepository;
import com.application.repository.DistributionRepository;
import com.application.repository.DistrictRepository;
import com.application.repository.EmployeeRepository;
import com.application.repository.ExamProgramRepository;
import com.application.repository.GenderRepository;
import com.application.repository.MandalRepository;
import com.application.repository.OrgBankBranchRepository;
import com.application.repository.OrgBankRepository;
import com.application.repository.OrganizationBankDetailsRepository;
import com.application.repository.OrganizationRepository;
import com.application.repository.OrientationBatchRepository;
import com.application.repository.OrientationRepository;
import com.application.repository.ParentDetailsRepository;
import com.application.repository.PaymentDetailsRepository;
import com.application.repository.PaymentModeRepository;
import com.application.repository.PinCodeRepository;
import com.application.repository.ProConcessionRepository;
import com.application.repository.ProgramNameRepository;
import com.application.repository.QuotaRepository;
import com.application.repository.ReligionRepository;
import com.application.repository.SchoolDetailsRepository;
import com.application.repository.SectionRepository;
import com.application.repository.SiblingRepository;
import com.application.repository.StateAppRepository;
import com.application.repository.StateRepository;
import com.application.repository.StatusRepository;
import com.application.repository.StreamRepository;
import com.application.repository.StudentAcademicDetailsRepository;
import com.application.repository.StudentAddressRepository;
import com.application.repository.StudentApplicationTransactionRepository;
import com.application.repository.StudentClassRepository;
import com.application.repository.StudentConcessionTypeRepository;
import com.application.repository.StudentOrientationDetailsRepository;
import com.application.repository.StudentPersonalDetailsRepository;
import com.application.repository.StudentRelationRepository;
import com.application.repository.StudentTypeRepository;
import com.application.repository.StudyTypeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentAdmissionService {

    private final CampusDetailsRepository campusDetailsRepository;
 
    private static final Logger logger = LoggerFactory.getLogger(StudentAdmissionService.class);
 
    // region Repositories
    @Autowired private StudentAcademicDetailsRepository academicDetailsRepo;
    @Autowired private StudentPersonalDetailsRepository personalDetailsRepo;
    @Autowired private StudentAddressRepository addressRepo;
    @Autowired private SiblingRepository siblingRepo;
    @Autowired private AdmissionTypeRepository admissionTypeRepo;
    @Autowired private StudentTypeRepository studentTypeRepo;
    @Autowired private StudyTypeRepository studyTypeRepo;
    @Autowired private GenderRepository genderRepo;
    @Autowired private CampusRepository campusRepo;
    @Autowired private StateRepository stateRepo;
    @Autowired private DistrictRepository districtRepo;
    @Autowired private CampusSchoolTypeRepository schoolTypeRepo;
    @Autowired private QuotaRepository quotaRepo;
    @Autowired private StudentRelationRepository relationTypeRepo;
    @Autowired private StudentClassRepository classRepo;
    @Autowired private OrientationRepository orientationRepo;
    @Autowired private MandalRepository mandalRepo;
    @Autowired private CityRepository cityRepo;
    @Autowired private AcademicYearRepository academicYearRepository;
    @Autowired private SectionRepository sectionRepo;
    @Autowired private StatusRepository statusRepo;
    @Autowired private EmployeeRepository employeeRepo;
    @Autowired private PaymentDetailsRepository paymentDetailsRepo;
    @Autowired private PaymentModeRepository paymentModeRepo;
    @Autowired private StudentConcessionTypeRepository studentConcessionTypeRepo;
    @Autowired private ConcessionTypeRepository concessionTypeRepo;
    @Autowired private ConcessionReasonRepository concessionReasonRepo;
    @Autowired private StudentApplicationTransactionRepository appTransactionRepo;
    @Autowired private OrganizationRepository orgRepo;
    @Autowired private OrgBankRepository orgBankRepo;
    @Autowired private OrgBankBranchRepository orgBankBranchRepo;
    @Autowired private SchoolDetailsRepository schoolDetailsRepo;
    @Autowired private ReligionRepository religionRepo;
    @Autowired private CasteRepository casteRepo;
    @Autowired private ParentDetailsRepository parentDetailsRepo;
    @Autowired private ProConcessionRepository proConcessionRepo;
    @Autowired private StudentOrientationDetailsRepository studentOrientationDetailsRepo;
    @Autowired private OrientationBatchRepository orientationBatchRepo;
    @Autowired private CmpsOrientationRepository cmpsOrientationRepo;
    @Autowired private CmpsOrientationStreamViewRepository cmpsOrientationStreamViewRepo;
    @Autowired private CmpsOrientationProgramViewRepository cmpsOrientationProgramViewRepo;
    @Autowired private CmpsOrientationBatchFeeViewRepository cmpsOrientationBatchFeeViewRepo;
    @Autowired private BloodGroupRepository bloodGroupRepo;
    @Autowired private StreamRepository streamRepo;
    @Autowired private ProgramNameRepository programNameRepo;
    @Autowired private ExamProgramRepository examProgramRepo;
    @Autowired private CmpsOrientationBatchFeeViewRepository repository;
    @Autowired private BloodGroupRepository bloodGroupRepository;
    @Autowired private OrganizationRepository organizationRepo;
    @Autowired private OrganizationBankDetailsRepository orgBankDetailsRepo;
    @Autowired private CampusSchoolTypeRepository campusSchoolTypeReposirtory;
    @Autowired private StudentRelationRepository studentRelationRepo;
//    @Autowired private ApplicationCouponRepository applicationCouponRepository;
//    @Autowired private EmployeeCouponRepository employeeCouponRepository;
//    @Autowired private StudentCouponRepository studentCouponRepository;
    @Autowired private DistributionRepository distributionRepo; // Injected the new repository
    @Autowired private PinCodeRepository pinCodeRepository;
    @Autowired private CampusRepository campusRepository;
    @Autowired private StateAppRepository stateAppRepository;
    @Autowired private DistributionRepository distributionRepository;


    StudentAdmissionService(CampusDetailsRepository campusDetailsRepository) {
        this.campusDetailsRepository = campusDetailsRepository;
    }
 
 
//    @Cacheable("religions")
    public List<GenericDropdownDTO> getAllReligions() {
        return religionRepo.findAll().stream()
                .map(r -> new GenericDropdownDTO(r.getReligion_id(), r.getReligion_type()))
                .collect(Collectors.toList());
    }
 
//    @Cacheable("castes")
    public List<GenericDropdownDTO> getAllCastes() {
        return casteRepo.findAll().stream()
                .map(c -> new GenericDropdownDTO(c.getCaste_id(), c.getCaste_type()))
                .collect(Collectors.toList());
    }
    
//    @Cacheable("admissionTypes")
    public List<GenericDropdownDTO> getAllAdmissionTypes() {
        return admissionTypeRepo.findAll().stream()
                .map(t -> new GenericDropdownDTO(t.getAdms_type_id(), t.getAdms_type_name()))
                .collect(Collectors.toList());
    }
 
//    @Cacheable("genders")
    public List<GenericDropdownDTO> getAllGenders() {
        return genderRepo.findAll().stream().map(g -> new GenericDropdownDTO(g.getGender_id(), g.getGenderName()))
                .collect(Collectors.toList());
    }
 
//    @Cacheable("campuses")
    public List<GenericDropdownDTO> getAllCampuses() {
        return campusRepo.findAll().stream().map(c -> new GenericDropdownDTO(c.getCampusId(), c.getCampusName()))
                .collect(Collectors.toList());
    }
    
//    @Cacheable("studentClasses")
    public List<GenericDropdownDTO> getAllStudentclass() {
        return classRepo.findAll().stream()
                .map(studentClass -> new GenericDropdownDTO(studentClass.getClassId(), studentClass.getClassName()))
                .collect(Collectors.toList());
    }
    
//    @Cacheable(value = "studyTypes", key = "#id")
    public StudyType getStudyTypeById(int id) {
        Optional<StudyType> studyTypeOptional = studyTypeRepo.findById(id);
        return studyTypeOptional.orElse(null);
    }
    
//    @Cacheable("quotas")
    public List<GenericDropdownDTO> getAllQuotas() {
        return quotaRepo.findAll().stream()
                .map(quota -> new GenericDropdownDTO(quota.getQuota_id(), quota.getQuota_name()))
                .collect(Collectors.toList());
    }
    
//    @Cacheable("employees")
    public List<GenericDropdownDTO> getAllEmployees() {
        return employeeRepo.findAll().stream()
                .map(employee -> new GenericDropdownDTO(employee.getEmp_id(), employee.getFirst_name() + " " + employee.getLast_name()))
                .collect(Collectors.toList());
    }
    
//    @Cacheable("schoolTypes")
    public List<GenericDropdownDTO> getAllSchoolTypes()
    {         return campusSchoolTypeReposirtory.findAll().stream()            
    		.map(schoolType -> new GenericDropdownDTO(schoolType.getSchool_type_id(), schoolType.getSchool_type_name())) .collect(Collectors.toList()); }
    
//    @Cacheable("concessionReasons")
    public List<GenericDropdownDTO> getAllConcessionReasons() {
        return concessionReasonRepo.findAll().stream()
                .map(reason -> new GenericDropdownDTO(reason.getConc_reason_id(), reason.getConc_reason()))
                .collect(Collectors.toList());
    }
    
//    @Cacheable("bloodGroups")
    public List<GenericDropdownDTO> getAllBloodGroups() {
        return bloodGroupRepository.findAll().stream()
                .map(group -> new GenericDropdownDTO(group.getBlood_group_id(), group.getBlood_group_name()))
                .collect(Collectors.toList());
    }
    
//    @Cacheable("paymentModes")
    public List<GenericDropdownDTO> getAllPaymentModes() {
        return paymentModeRepo.findAll().stream()
                .map(mode -> new GenericDropdownDTO(mode.getPayment_mode_id(), mode.getPayment_type()))
                .collect(Collectors.toList());
    }
    
//    @Cacheable(value = "orientationsByClass", key = "#classId")
    public List<OrientationDTO> getOrientationsByClassId(int classId) {
        return cmpsOrientationBatchFeeViewRepo.findOrientationsByClassId(classId);
    }
    
//    @Cacheable(value = "classesByCampus", key = "#campusId")
    public List<ClassDTO> getClassesByCampusId(int campusId) {
        return cmpsOrientationBatchFeeViewRepo.findClassesByCampusId(campusId);
    }
    
//    @Cacheable("studentTypes")
    public List<GenericDropdownDTO> getAllStudentTypes() {
        return studentTypeRepo.findAll().stream()
                .map(t -> new GenericDropdownDTO(t.getStud_type_id(), t.getStud_type())).collect(Collectors.toList());
    }
 
//    @Cacheable("studentRelations")
    public List<GenericDropdownDTO> getAllStudentRelations()
    {         return studentRelationRepo.findAll().stream().map(relation -> new GenericDropdownDTO(relation.getStudentRelationId(), relation.getStudentRelationType())).collect(Collectors.toList()); }
    
    
//    @Cacheable(value = "studyTypesByCampusAndClass", key = "{#cmpsId, #classId}")
    public List<GenericDropdownDTO> getStudyTypesByCampusAndClass(int cmpsId, int classId) {
        return cmpsOrientationBatchFeeViewRepo.findDistinctStudyTypesByCmpsIdAndClassId(cmpsId, classId);
    }
 
//    @Cacheable(value = "orientationsByCampusClassStudyType", key = "{#cmpsId, #classId, #studyTypeId}")
    public List<GenericDropdownDTO> getOrientationsByCampusClassAndStudyType(int cmpsId, int classId, int studyTypeId) {
        return cmpsOrientationBatchFeeViewRepo.findDistinctOrientationsByCmpsIdAndClassIdAndStudyTypeId(cmpsId, classId, studyTypeId);
    }
 
//    @Cacheable(value = "orientationBatchesByCriteria", key = "{#cmpsId, #classId, #studyTypeId, #orientationId}")
    public List<GenericDropdownDTO> getOrientationBatchesByAllCriteria(int cmpsId, int classId, int studyTypeId, int orientationId) {
        return cmpsOrientationBatchFeeViewRepo.findDistinctOrientationBatchesByCmpsIdAndClassIdAndStudyTypeIdAndOrientationId(cmpsId, classId, studyTypeId, orientationId);
    }
 
//    @Cacheable(value = "batchDetails", key = "{#cmpsId, #classId, #studyTypeId, #orientationId, #orientationBatchId}")
    public Optional<OrientationBatchDetailsDTO> getBatchDetails(int cmpsId, int classId, int studyTypeId, int orientationId, int orientationBatchId) {
        return cmpsOrientationBatchFeeViewRepo.findBatchDetailsByAllCriteria(cmpsId, classId, studyTypeId, orientationId, orientationBatchId);
    }
 
//    @Cacheable(value = "orientationsByCampus", key = "#campusId")
    public List<OrientationResponseDTO> getOrientationsByCampus(int campusId) {
        List<CmpsOrientationBatchFeeView> orientations = cmpsOrientationBatchFeeViewRepo.findByCmpsId(campusId);
        return orientations.stream()
                .filter(entity -> entity != null)
                .map(this::convertToOrientationResponseDto)
                .collect(Collectors.toList());
    }
 
    private OrientationResponseDTO convertToOrientationResponseDto(CmpsOrientationBatchFeeView entity) {
        return new OrientationResponseDTO(
            entity.getCmpsId(),
            entity.getCmpsName(),
            entity.getOrientationId(),
            entity.getOrientationName(),
            entity.getOrientationBatchId(),
            entity.getOrientationBatchName(),
            entity.getOrientationStartDate(),
            entity.getOrientationEndDate(),
            entity.getOrientationFee(),
            entity.getSectionName(),
            entity.getSectionId()
        );
    }
    
//    @Cacheable(value = "districtsByState", key = "#stateId")
    public List<GenericDropdownDTO> getDistrictsByState(int stateId) {
        return districtRepo.findByStateStateId(stateId).stream()
                .map(d -> new GenericDropdownDTO(d.getDistrictId(), d.getDistrictName()))
                .collect(Collectors.toList());
    }
 
//    @Cacheable(value = "mandalsByDistrict", key = "#districtId")
    public List<GenericDropdownDTO> getMandalsByDistrict(int districtId) {
        return mandalRepo.findByDistrictDistrictId(districtId).stream()
                .map(m -> new GenericDropdownDTO(m.getMandal_id(), m.getMandal_name()))
                .collect(Collectors.toList());
    }
    
//    @Cacheable(value = "citiesByDistrict", key = "#districtId")
    public List<GenericDropdownDTO> getCitiesByDistrict(int districtId) {
    	
    	final int ACTIVE_STATUS = 1;
        return cityRepo.findByDistrictDistrictIdAndStatus(districtId,ACTIVE_STATUS).stream()
                .map(c -> new GenericDropdownDTO(c.getCityId(), c.getCityName()))
                .collect(Collectors.toList());
    }
 
//    @Cacheable(value = "orientationFee", key = "{#campusId, #orientationId}")
    public CourseFeeDTO getOrientationFee(int campusId, int orientationId) {
        List<CmpsOrientation> orientations = cmpsOrientationRepo
                .findByCmpsIdAndOrientationOrientationId(campusId, orientationId);
 
        if (orientations.isEmpty()) {
            throw new EntityNotFoundException(
                "Fee not found for Campus ID: " + campusId + " and Orientation ID: " + orientationId
            );
        }
        float fee = orientations.get(0).getOrientation_fee();
        return new CourseFeeDTO(fee);
    }
    
//    @Cacheable("organizations")
    public List<GenericDropdownDTO> getAllOrganizations() {
        return organizationRepo.findAll().stream()
                .map(org -> new GenericDropdownDTO(org.getOrgId(), org.getOrg_name()))
                .collect(Collectors.toList());
    }
 
//    @Cacheable(value = "banksByOrganization", key = "#orgId")
    public List<GenericDropdownDTO> getBanksByOrganization(int orgId) {
        return orgBankDetailsRepo.findDistinctBanksByOrganizationId(orgId).stream()
                .map(bank -> new GenericDropdownDTO(bank.getOrg_bank_id(), bank.getBank_name()))
                .collect(Collectors.toList());
    }
    
//    @Cacheable(value = "bankDetails", key = "{#orgId, #bankId, #branchId}")
    public BankDetailsDTO getBankDetails(int orgId, int bankId, int branchId) {
        List<OrganizationBankDetails> detailsList = orgBankDetailsRepo.findDetailsByAllIds(orgId, bankId, branchId);
 
        if (detailsList.isEmpty()) {
            throw new EntityNotFoundException("Details not found for the given combination");
        }
        
        OrganizationBankDetails details = detailsList.get(0);
        
        return new BankDetailsDTO(details.getIfsc_code());
    }
 
//    @Cacheable(value = "branchesByOrgAndBank", key = "{#orgId, #bankId}")
    public List<GenericDropdownDTO> getBranchesByOrganizationAndBank(int orgId, int bankId) {
        return orgBankDetailsRepo.findDistinctBranchesByOrganizationAndBankId(orgId, bankId).stream()
                .map(branch -> new GenericDropdownDTO(branch.getOrg_bank_branch_id(), branch.getBranch_name()))
                .collect(Collectors.toList());
    }
    
    public PinCodeLocationDTO getLocationByPinCode(int pinCode) {
        return pinCodeRepository.findStateAndDistrictByPinCode(pinCode)
                .orElseThrow(() -> new RuntimeException("No data found for pin code: " + pinCode));
    }
    
    public CampusDetailsDTO getCampusDetails(int campusId) {
        return campusRepository.findCampusDetailsById(campusId)
                .orElseThrow(() -> new RuntimeException("Campus not found for ID: " + campusId));
    }
    
 // College / General Admission
    public CampusAndZoneDTO getCampusZoneYearAndFeeByAdmissionNo(Long admissionNo) {
        return academicDetailsRepo.findByStudAdmsNo(admissionNo).map(student -> {
            CampusAndZoneDTO dto = new CampusAndZoneDTO();

            // Campus & Zone
            if (student.getCampus() != null) {
                dto.setCampusId(student.getCampus().getCampusId());
                dto.setCampusName(student.getCampus().getCampusName());
                if (student.getCampus().getZone() != null) {
                    dto.setZoneId(student.getCampus().getZone().getZoneId());
                    dto.setZoneName(student.getCampus().getZone().getZoneName());
                }
            }

            // Academic Year from StudentAcademicDetails
            if (student.getAcademicYear() != null) {
                dto.setAcademicYearId(student.getAcademicYear().getAcdcYearId());
                dto.setAcademicYear(student.getAcademicYear().getAcademicYear());
            }

            // Application Fee from StateApp (if needed)
            List<StateApp> stateApps = stateAppRepository.findAllByAdmissionNoBetweenRange(
                    student.getStudAdmsNo().intValue()
            );
            if (!stateApps.isEmpty()) {
                dto.setApplicationFee(stateApps.get(0).getAmount());
            }

            return dto;
        }).orElse(null);
    }

    // School
    public CampusAndZoneDTO getCampusZoneYearAndFeeForSchool(Long admissionNo) {
        return academicDetailsRepo.findByStudAdmsNo(admissionNo).map(student -> {
            CampusAndZoneDTO dto = new CampusAndZoneDTO();

            // 1️⃣ Campus & Zone
            if (student.getCampus() != null) {
                dto.setCampusId(student.getCampus().getCampusId());
                dto.setCampusName(student.getCampus().getCampusName());
                if (student.getCampus().getZone() != null) {
                    dto.setZoneId(student.getCampus().getZone().getZoneId());
                    dto.setZoneName(student.getCampus().getZone().getZoneName());
                }
            }

            // 2️⃣ Academic Year from StudentAcademicDetails
            if (student.getAcademicYear() != null) {
                dto.setAcademicYearId(student.getAcademicYear().getAcdcYearId());
                dto.setAcademicYear(student.getAcademicYear().getAcademicYear());
            }

            // 3️⃣ Application Fee from Distribution
            List<Distribution> distributions = distributionRepository
                .findByAdmissionNoRangeAndIssuedToType(student.getStudAdmsNo().intValue(), 4);

            if (!distributions.isEmpty()) {
                Distribution dist = distributions.get(0);

                // Match campusId in CampusDetails to get app_fee
                if (dist.getCampus() != null) {
                    int campusId = dist.getCampus().getCampusId();
                    campusDetailsRepository.findById(campusId)
                        .ifPresent(campusDetails -> dto.setApplicationFee(campusDetails.getApp_fee()));
                }
            }

            return dto;
        }).orElse(null);
    }
    
 
    public List<ConcessionTypeDTO> getConcessionTypesByNames(List<String> concTypes) {
        return concessionTypeRepo.findConcessionTypesByNames(concTypes);
    }

    
    @Transactional
	public StudentAcademicDetails createAdmissionAndSale(StudentAdmissionDTO formData) {
 
		// ==============================================================
		// PART 1: CREATE THE STUDENT
		// ==============================================================
    	
    	Long admissionNumberNumeric = formData.getStudAdmsNo();

    	if (admissionNumberNumeric == null) {
    	    throw new IllegalArgumentException("Admission Number must be provided.");
    	}

    	Distribution distribution = distributionRepo.findProDistributionForAdmissionNumber(admissionNumberNumeric)
    	        .orElseThrow(() -> new EntityNotFoundException(
    	            "No PRO has been assigned for Admission Number: " + admissionNumberNumeric
    	        ));

    	Employee pro = distribution.getIssuedToEmployee();
    	if (pro == null) {
    	     throw new EntityNotFoundException("A PRO has not been linked to the distribution for Admission Number: " + admissionNumberNumeric);
    	}
		// --- 1. Save Academic Details ---
		StudentAcademicDetails academicDetails = new StudentAcademicDetails();
		
		academicDetails.setIs_active(1);
 
		// ... (all the code from Part 1 is correct) ...
 
		if (formData.getAcademicYearId() != null) {
			academicYearRepository.findById(formData.getAcademicYearId()).ifPresent(academicDetails::setAcademicYear);
		}
 
		academicDetails.setStudAdmsNo(formData.getStudAdmsNo());
		if (formData.getProReceiptNo() != null) {
			academicDetails.setPro_receipt_no(formData.getProReceiptNo().intValue());
		}
		academicDetails.setFirst_name(formData.getFirstName());
		academicDetails.setLast_name(formData.getLastName());
		academicDetails.setApaar_no(formData.getApaarNo());
		academicDetails.setAdms_date(LocalDate.now());
		academicDetails.setApp_sale_date(formData.getAppSaleDate());
		
		if (formData.getProId() != null) {
		    // Convert the Integer Employee ID from the DTO to a String
		    String referredByEmployeeId = String.valueOf(formData.getProId());
		    
		    // Set the String value in the admission_referred_by column
		    academicDetails.setAdmission_referred_by(referredByEmployeeId);
		}
 
		if (formData.getGenderId() != null)
			genderRepo.findById(formData.getGenderId()).ifPresent(academicDetails::setGender);
		if (formData.getAppTypeId() != null)
			admissionTypeRepo.findById(formData.getAppTypeId()).ifPresent(academicDetails::setAdmissionType);
		if (formData.getStudentTypeId() != null)
			studentTypeRepo.findById(formData.getStudentTypeId()).ifPresent(academicDetails::setStudentType);
		if (formData.getQuotaId() != null)
			quotaRepo.findById(formData.getQuotaId()).ifPresent(academicDetails::setQuota);
//		if (formData.getBranchTypeId() != null) {
//			schoolTypeRepo.findById(formData.getBranchTypeId()).ifPresent(academicDetails::setCampusSchoolType);
//		}
		
		StudentClass studentClass = classRepo.findById(formData.getClassId())
				.orElseThrow(() -> new EntityNotFoundException("Invalid Class ID: " + formData.getClassId()));
		academicDetails.setStudentClass(studentClass);
 
		Campus campus = campusRepo.findById(formData.getBranchId())
				.orElseThrow(() -> new EntityNotFoundException("Invalid Branch ID: " + formData.getBranchId()));
		academicDetails.setCampus(campus);
 
		if (formData.getProId() != null && formData.getProId() > 0) {
			employeeRepo.findById(formData.getProId()).ifPresent(academicDetails::setEmployee);
		}
 
		academicDetails.setCreated_by(formData.getCreatedBy());
		academicDetails.setEmployee(pro);
 
		// --- FIX 1 (from previous step) ---
		StudyType defaultStudyType = studyTypeRepo.findById(1)
				.orElseThrow(() -> new EntityNotFoundException("Default StudyType (ID: 1) not found"));
		academicDetails.setStudyType(defaultStudyType);
 
		Status defaultStatus = statusRepo.findById(2)
				.orElseThrow(() -> new EntityNotFoundException("Default Status (ID: 1) not found"));
		academicDetails.setStatus(defaultStatus);
 
		StudentAcademicDetails savedAcademicDetails = academicDetailsRepo.save(academicDetails);
 
		// --- 2. Save Personal Details ---
		StudentPersonalDetails personalDetails = new StudentPersonalDetails();
		personalDetails.setStudentAcademicDetails(savedAcademicDetails);
		personalDetails.setStud_aadhaar_no(formData.getAadharCardNo());
		personalDetails.setDob(formData.getDob());
		personalDetails.setCreated_by(formData.getCreatedBy());
 
		// these setted to 1 again these will be updated in the confirmation form
		personalDetails.setCaste_id(3); // Or use personalDetails.setCaste_id(1) if you don't have the mapping
		personalDetails.setReligion_id(3); // Or use personalDetails.setReligion_id(1)
 
		// TODO: Check if blood_group_id is also NOT NULL and add a default if needed
		// BloodGroup defaultBloodGroup = bloodGroupRepo.findById(1)
		// .orElseThrow(() -> new EntityNotFoundException("Default BloodGroup (ID: 1)
		// not found"));
		// personalDetails.setBloodGroup(defaultBloodGroup);
		// --- END FIX 2 ---
 
		personalDetailsRepo.save(personalDetails);
 
		// --- 3. Save Student Orientation Details ---
		StudentOrientationDetails orientationDetails = new StudentOrientationDetails();
		orientationDetails.setStudentAcademicDetails(savedAcademicDetails);
		if (formData.getOrientationId() != null)
			orientationRepo.findById(formData.getOrientationId()).ifPresent(orientationDetails::setOrientation);
		studentOrientationDetailsRepo.save(orientationDetails);
 
		// --- 4. Save Parent Details ---
		if (formData.getFatherName() != null || formData.getFatherMobileNo() != null) {
 
			StudentRelation fatherRelation = relationTypeRepo.findById(1) // Assuming 1 = Father
					.orElseThrow(() -> new EntityNotFoundException("StudentRelation 'Father' (ID: 1) not found"));
 
			ParentDetails parent = new ParentDetails();
			parent.setStudentAcademicDetails(savedAcademicDetails);
			parent.setName(formData.getFatherName());
			parent.setMobileNo(formData.getFatherMobileNo());
			parent.setCreated_by(formData.getCreatedBy());
			parent.setStudentRelation(fatherRelation);
			// these setted to 1 again these will be updated in the confirmation form
			parent.setOccupation("Not Provided");
			parent.setEmail("not provided");
			
			
			parentDetailsRepo.save(parent);
		}
 
		// --- 5. Save Address Details ---
		if (formData.getAddressDetails() != null) {
            AddressDetailsDTO addressDTO = formData.getAddressDetails();
            StudentAddress address = new StudentAddress();
            address.setStudentAcademicDetails(savedAcademicDetails);
            address.setHouse_no(addressDTO.getDoorNo());
            address.setStreet(addressDTO.getStreet());
            address.setLandmark(addressDTO.getLandmark());
            address.setArea(addressDTO.getArea());
            if (addressDTO.getPincode() != null) address.setPostalCode(addressDTO.getPincode());
            if (addressDTO.getStateId()!= null) stateRepo.findById(addressDTO.getStateId()).ifPresent(address::setState);
            if (addressDTO.getCityId() != null) cityRepo.findById(addressDTO.getCityId()).ifPresent(address::setCity);
            if (addressDTO.getMandalId() != null) mandalRepo.findById(addressDTO.getMandalId()).ifPresent(address::setMandal);
            if (addressDTO.getDistrictId() != null) districtRepo.findById(addressDTO.getDistrictId()).ifPresent(address::setDistrict);
            
            // --- FIX 1: Use the 'createdBy' from the AddressDetailsDTO ---
            address.setCreated_by(addressDTO.getCreatedBy());
            
            // --- FIX 2: SET DEFAULT VALUE FOR 'NOT NULL' STATE FIELD ---
            // TODO: Confirm the default State ID (e.g., 1) from your 'state' table.
//            State defaultState = stateRepo.findById(1) // Assuming 1 = "Not Applicable" or a default state
//                    .orElseThrow(() -> new EntityNotFoundException("Default State (ID: 1) not found"));
//            address.setState(defaultState);
            // --- END FIX ---
            
            addressRepo.save(address);
		}
 
		// ==============================================================
		// PART 2: CREATE THE PAYMENT
		// ==============================================================
 
		PaymentDetailsDTO paymentDTO = formData.getPaymentDetails();
 
		if (paymentDTO != null && paymentDTO.getAmount() != null) {
			PaymentDetails paymentDetails = new PaymentDetails();
 
			paymentDetails.setStudentAcademicDetails(savedAcademicDetails);
			paymentDetails.setApplication_fee_pay_date(paymentDTO.getPaymentDate());
			paymentDetails.setPre_print_receipt_no(paymentDTO.getPrePrintedReceiptNo());
			paymentDetails.setRemarks(paymentDTO.getRemarks());
			paymentDetails.setCreated_by(paymentDTO.getCreatedBy());
			paymentDetails.setApp_fee(paymentDTO.getAmount());
			paymentDetails.setPaid_amount(paymentDTO.getAmount());
 
			paymentDetails.setAcedemicYear(savedAcademicDetails.getAcademicYear());
			paymentDetails.setStudentClass(savedAcademicDetails.getStudentClass());
 
			if (paymentDTO.getPaymentModeId() != null) {
				paymentModeRepo.findById(paymentDTO.getPaymentModeId()).ifPresent(paymentDetails::setPaymenMode);
			}
 

			paymentDetails.setStatus(defaultStatus);
 
			paymentDetailsRepo.save(paymentDetails);
		}
 
		return savedAcademicDetails;
	}
	
	@Transactional
	public StudentAcademicDetails createAdmission(StudentSaleDTO formData) {
 
		// ==============================================================
		// PART 1: CREATE THE STUDENT
		// ==============================================================
		
		Long admissionNumberNumeric = formData.getStudAdmsNo();

    	if (admissionNumberNumeric == null) {
    	    throw new IllegalArgumentException("Admission Number must be provided.");
    	}

    	Distribution distribution = distributionRepo.findProDistributionForAdmissionNumber(admissionNumberNumeric)
    	        .orElseThrow(() -> new EntityNotFoundException(
    	            "No PRO has been assigned for Admission Number: " + admissionNumberNumeric
    	        ));

    	Employee pro = distribution.getIssuedToEmployee();
    	if (pro == null) {
    	     throw new EntityNotFoundException("A PRO has not been linked to the distribution for Admission Number: " + admissionNumberNumeric);
    	}
		// --- 1. Save Academic Details ---
		StudentAcademicDetails academicDetails = new StudentAcademicDetails();
 
		// ... (all the code from Part 1 is correct) ...
		
		academicDetails.setIs_active(0);
		if (formData.getAcademicYearId() != null) {
			academicYearRepository.findById(formData.getAcademicYearId()).ifPresent(academicDetails::setAcademicYear);
		}
 
		academicDetails.setStudAdmsNo(formData.getStudAdmsNo());
		if (formData.getProReceiptNo() != null) {
			academicDetails.setPro_receipt_no(formData.getProReceiptNo().intValue());
		}
		academicDetails.setFirst_name(formData.getFirstName());
		academicDetails.setLast_name(formData.getLastName());
		academicDetails.setApaar_no(formData.getApaarNo());
		academicDetails.setAdms_date(LocalDate.now());
		academicDetails.setApp_sale_date(formData.getAppSaleDate());
		
		if (formData.getProId() != null) {
		    // Convert the Integer Employee ID from the DTO to a String
		    String referredByEmployeeId = String.valueOf(formData.getProId());
		    
		    // Set the String value in the admission_referred_by column
		    academicDetails.setAdmission_referred_by(referredByEmployeeId);
		}
 
		if (formData.getGenderId() != null)
			genderRepo.findById(formData.getGenderId()).ifPresent(academicDetails::setGender);
		if (formData.getAppTypeId() != null)
			admissionTypeRepo.findById(formData.getAppTypeId()).ifPresent(academicDetails::setAdmissionType);
		if (formData.getStudentTypeId() != null)
			studentTypeRepo.findById(formData.getStudentTypeId()).ifPresent(academicDetails::setStudentType);
		if (formData.getQuotaId() != null)
			quotaRepo.findById(formData.getQuotaId()).ifPresent(academicDetails::setQuota);
 
		StudentClass studentClass = classRepo.findById(formData.getClassId())
				.orElseThrow(() -> new EntityNotFoundException("Invalid Class ID: " + formData.getClassId()));
		academicDetails.setStudentClass(studentClass);
 
		Campus campus = campusRepo.findById(formData.getBranchId())
				.orElseThrow(() -> new EntityNotFoundException("Invalid Branch ID: " + formData.getBranchId()));
		academicDetails.setCampus(campus);
 
		if (formData.getProId() != null && formData.getProId() > 0) {
			employeeRepo.findById(formData.getProId()).ifPresent(academicDetails::setEmployee);
		}
 
		academicDetails.setCreated_by(formData.getCreatedBy());
		academicDetails.setEmployee(pro);
 
		// --- FIX 1 (from previous step) ---
		StudyType defaultStudyType = studyTypeRepo.findById(1)
				.orElseThrow(() -> new EntityNotFoundException("Default StudyType (ID: 1) not found"));
		academicDetails.setStudyType(defaultStudyType);
 
		Status defaultStatus = statusRepo.findById(8)
				.orElseThrow(() -> new EntityNotFoundException("Default Status (ID: 1) not found"));
		academicDetails.setStatus(defaultStatus);
 
		StudentAcademicDetails savedAcademicDetails = academicDetailsRepo.save(academicDetails);
 
		// --- 2. Save Personal Details ---
		StudentPersonalDetails personalDetails = new StudentPersonalDetails();
		personalDetails.setStudentAcademicDetails(savedAcademicDetails);
		personalDetails.setStud_aadhaar_no(formData.getAadharCardNo());
		personalDetails.setDob(formData.getDob());
		personalDetails.setCreated_by(formData.getCreatedBy());
 
		// these setted to 1 again these will be updated in the confirmation form
		personalDetails.setCaste_id(3); // Or use personalDetails.setCaste_id(1) if you don't have the mapping
		personalDetails.setReligion_id(3); // Or use personalDetails.setReligion_id(1)
 
		// TODO: Check if blood_group_id is also NOT NULL and add a default if needed
		// BloodGroup defaultBloodGroup = bloodGroupRepo.findById(1)
		// .orElseThrow(() -> new EntityNotFoundException("Default BloodGroup (ID: 1)
		// not found"));
		// personalDetails.setBloodGroup(defaultBloodGroup);
		// --- END FIX 2 ---
 
		personalDetailsRepo.save(personalDetails);
 
		// --- 3. Save Student Orientation Details ---
		StudentOrientationDetails orientationDetails = new StudentOrientationDetails();
		orientationDetails.setStudentAcademicDetails(savedAcademicDetails);
		if (formData.getOrientationId() != null)
			orientationRepo.findById(formData.getOrientationId()).ifPresent(orientationDetails::setOrientation);
		studentOrientationDetailsRepo.save(orientationDetails);
 
		// --- 4. Save Parent Details ---
		if (formData.getFatherName() != null || formData.getFatherMobileNo() != null) {
 
			StudentRelation fatherRelation = relationTypeRepo.findById(1) // Assuming 1 = Father
					.orElseThrow(() -> new EntityNotFoundException("StudentRelation 'Father' (ID: 1) not found"));
 
			ParentDetails parent = new ParentDetails();
			parent.setStudentAcademicDetails(savedAcademicDetails);
			parent.setName(formData.getFatherName());
			parent.setMobileNo(formData.getFatherMobileNo());
			parent.setCreated_by(formData.getCreatedBy());
			parent.setStudentRelation(fatherRelation);
			// these setted to 1 again these will be updated in the confirmation form
			parent.setOccupation("Not Provided");
			parent.setEmail("not provided");
			
			
			parentDetailsRepo.save(parent);
		}
 
		// --- 5. Save Address Details ---
		if (formData.getAddressDetails() != null) {
            AddressDetailsDTO addressDTO = formData.getAddressDetails();
            StudentAddress address = new StudentAddress();
            address.setStudentAcademicDetails(savedAcademicDetails);
            address.setHouse_no(addressDTO.getDoorNo());
            address.setStreet(addressDTO.getStreet());
            address.setLandmark(addressDTO.getLandmark());
            address.setArea(addressDTO.getArea());
            if (addressDTO.getPincode() != null) address.setPostalCode(addressDTO.getPincode());
            if (addressDTO.getStateId()!= null) stateRepo.findById(addressDTO.getStateId()).ifPresent(address::setState);
            if (addressDTO.getCityId() != null) cityRepo.findById(addressDTO.getCityId()).ifPresent(address::setCity);
            if (addressDTO.getMandalId() != null) mandalRepo.findById(addressDTO.getMandalId()).ifPresent(address::setMandal);
            if (addressDTO.getDistrictId() != null) districtRepo.findById(addressDTO.getDistrictId()).ifPresent(address::setDistrict);
            
            // --- FIX 1: Use the 'createdBy' from the AddressDetailsDTO ---
            address.setCreated_by(addressDTO.getCreatedBy());
            
            // --- FIX 2: SET DEFAULT VALUE FOR 'NOT NULL' STATE FIELD ---
            // TODO: Confirm the default State ID (e.g., 1) from your 'state' table.
//            State defaultState = stateRepo.findById(1) // Assuming 1 = "Not Applicable" or a default state
//                    .orElseThrow(() -> new EntityNotFoundException("Default State (ID: 1) not found"));
//            address.setState(defaultState);
            // --- END FIX ---
            
            addressRepo.save(address);
		}
		return savedAcademicDetails;
	}
}
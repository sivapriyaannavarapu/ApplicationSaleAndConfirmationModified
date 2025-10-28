package com.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.dto.GenericDropdownDTO;
import com.application.entity.Campus;
import com.application.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	@Query("SELECT e.primary_mobile_no FROM Employee e WHERE e.emp_id = :empId")
	String findMobileNoByEmpId(int empId);

	@Query("SELECT e FROM Employee e WHERE e.first_name = :firstName")
	Optional<Employee> findByFirst_name(@Param("firstName") String firstName);

	List<Employee> findByCampus(Campus campus);

	@Query("SELECT new com.application.dto.GenericDropdownDTO( " + "    e.emp_id, "
			+ "    CONCAT(e.first_name, ' ', e.last_name) " + ") " + "FROM Employee e "
			+ "WHERE e.emp_id IN :employeeIds")
	List<GenericDropdownDTO> findEmployeesByIdsAsDropdown(@Param("employeeIds") List<Integer> employeeIds);
}
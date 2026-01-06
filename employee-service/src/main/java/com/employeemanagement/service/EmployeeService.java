package employeemanagement.service;

import java.util.List;
import java.util.Optional;

import employeemanagement.model.Employee;

public interface EmployeeService {

	// Get all employees
	List<Employee> getAllEmployees();

	// Get employee by ID
	Optional<Employee> getEmployeeById(long id);

	// Create new employee
	Employee createEmployee(Employee employee);

	// Update existing employee
	Employee updateEmployee(long id, Employee employee);

	// Partial update employee
	Employee partialUpdateEmployee(long id, Employee employee);

	// Delete employee by ID
	void deleteEmployeeById(long id);

	// Check if employee exists
	boolean existsById(long id);

	// Search employees
	List<Employee> searchEmployees(String firstName, String lastName);

	// Search by email
	Optional<Employee> findByEmail(String email);

}
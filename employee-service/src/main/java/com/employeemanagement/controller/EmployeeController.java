package employeemanagement.controller;

import employeemanagement.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import employeemanagement.service.EmployeeService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*") // Configure this based on your frontend URL
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	// GET all employees
	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> employees = employeeService.getAllEmployees();
		return ResponseEntity.ok(employees);
	}

	// GET employee by ID
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		return employeeService.getEmployeeById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	// POST - Create new employee
	@PostMapping
	public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
		Employee savedEmployee = employeeService.createEmployee(employee);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
	}

	// PUT - Update existing employee
	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(
			@PathVariable Long id,
			@Valid @RequestBody Employee employeeDetails) {
		Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
		return ResponseEntity.ok(updatedEmployee);
	}

	// DELETE - Delete employee
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployeeById(id);
		return ResponseEntity.noContent().build();
	}

	// PATCH - Partial update
	@PatchMapping("/{id}")
	public ResponseEntity<Employee> partialUpdateEmployee(
			@PathVariable Long id,
			@RequestBody Employee employeeDetails) {
		Employee updatedEmployee = employeeService.partialUpdateEmployee(id, employeeDetails);
		return ResponseEntity.ok(updatedEmployee);
	}

	// GET employee by email
	@GetMapping("/email/{email}")
	public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email) {
		return employeeService.findByEmail(email)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	// Search/Filter employees (example)
	@GetMapping("/search")
	public ResponseEntity<List<Employee>> searchEmployees(
			@RequestParam(required = false) String firstName,
			@RequestParam(required = false) String lastName) {
		// Implement search logic in service
		List<Employee> employees = employeeService.searchEmployees(firstName, lastName);
		return ResponseEntity.ok(employees);
	}
}
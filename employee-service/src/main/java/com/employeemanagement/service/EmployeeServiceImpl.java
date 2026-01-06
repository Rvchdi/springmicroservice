package employeemanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import employeemanagement.model.Employee;
import employeemanagement.repository.EmployeeRepository;
import employeemanagement.exception.ResourceNotFoundException;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Optional<Employee> getEmployeeById(long id) {
		return employeeRepository.findById(id);
	}

	@Override
	public Employee createEmployee(Employee employee) {
		if (employee.getEmail() != null &&
				employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
			throw new IllegalArgumentException("Email already exists");
		}
		return employeeRepository.save(employee);
	}

	@Override
	public Employee updateEmployee(long id, Employee employeeDetails) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

		// Update all fields
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmail(employeeDetails.getEmail());
		employee.setDepartment(employeeDetails.getDepartment());
		employee.setSalary(employeeDetails.getSalary());
		// Add other fields as needed

		return employeeRepository.save(employee);
	}

	@Override
	public Employee partialUpdateEmployee(long id, Employee employeeDetails) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

		// Update only non-null fields
		if (employeeDetails.getFirstName() != null) {
			employee.setFirstName(employeeDetails.getFirstName());
		}
		if (employeeDetails.getLastName() != null) {
			employee.setLastName(employeeDetails.getLastName());
		}
		if (employeeDetails.getEmail() != null) {
			employee.setEmail(employeeDetails.getEmail());
		}
		if (employeeDetails.getDepartment() != null) {
			employee.setDepartment(employeeDetails.getDepartment());
		}
		if (employeeDetails.getSalary() != null) {
			employee.setSalary(employeeDetails.getSalary());
		}

		return employeeRepository.save(employee);
	}

	@Override
	public void deleteEmployeeById(long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
		employeeRepository.delete(employee);
	}

	@Override
	public boolean existsById(long id) {
		return employeeRepository.existsById(id);
	}

	@Override
	public List<Employee> searchEmployees(String firstName, String lastName) {
		List<Employee> allEmployees = employeeRepository.findAll();

		return allEmployees.stream()
				.filter(emp -> {
					boolean matches = true;
					if (firstName != null && !firstName.isEmpty()) {
						matches = emp.getFirstName().toLowerCase()
								.contains(firstName.toLowerCase());
					}
					if (lastName != null && !lastName.isEmpty()) {
						matches = matches && emp.getLastName().toLowerCase()
								.contains(lastName.toLowerCase());
					}
					return matches;
				})
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Employee> findByEmail(String email) {
		return employeeRepository.findByEmail(email);
	}
}
package employeemanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import employeemanagement.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Find by email
    Optional<Employee> findByEmail(String email);

    // Find by first name (case-insensitive)
    List<Employee> findByFirstNameIgnoreCase(String firstName);

    // Find by last name (case-insensitive)
    List<Employee> findByLastNameIgnoreCase(String lastName);

    // Find by department
    List<Employee> findByDepartment(String department);

    // Find employees with salary greater than
    List<Employee> findBySalaryGreaterThan(Double salary);

    // Count by department
    long countByDepartment(String department);
}
package kg.itschool.crm.data.service;

import kg.itschool.crm.data.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
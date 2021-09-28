package kg.itschool.crm.data.service;

import kg.itschool.crm.data.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
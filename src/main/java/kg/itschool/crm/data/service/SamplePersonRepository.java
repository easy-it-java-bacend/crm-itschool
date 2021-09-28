package kg.itschool.crm.data.service;

import kg.itschool.crm.data.entity.SamplePerson;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface SamplePersonRepository extends JpaRepository<SamplePerson, Integer> {

}
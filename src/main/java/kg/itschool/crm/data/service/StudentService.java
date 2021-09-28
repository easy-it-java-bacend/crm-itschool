package kg.itschool.crm.data.service;

import kg.itschool.crm.data.entity.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import java.time.LocalDate;

@Service
public class StudentService extends CrudService<Student, Integer> {

    private StudentRepository repository;

    public StudentService(@Autowired StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    protected StudentRepository getRepository() {
        return repository;
    }

}

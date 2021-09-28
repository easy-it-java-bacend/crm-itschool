package kg.itschool.crm.data.generator;

import com.vaadin.flow.spring.annotation.SpringComponent;

import kg.itschool.crm.data.service.UserRepository;
import kg.itschool.crm.data.entity.User;
import java.util.Collections;
import org.springframework.security.crypto.password.PasswordEncoder;
import kg.itschool.crm.data.Role;
import kg.itschool.crm.data.service.StudentRepository;
import kg.itschool.crm.data.entity.Student;
import kg.itschool.crm.data.service.EmployeeRepository;
import kg.itschool.crm.data.entity.Employee;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(PasswordEncoder passwordEncoder, UserRepository userRepository,
            StudentRepository studentRepository, EmployeeRepository employeeRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (userRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            logger.info("... generating 2 User entities...");
            User user = new User();
            user.setName("John Normal");
            user.setUsername("user");
            user.setHashedPassword(passwordEncoder.encode("user"));
            user.setProfilePictureUrl(
                    "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);
            User admin = new User();
            admin.setName("John Normal");
            admin.setUsername("admin");
            admin.setHashedPassword(passwordEncoder.encode("admin"));
            admin.setProfilePictureUrl(
                    "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
            admin.setRoles(Collections.singleton(Role.ADMIN));
            userRepository.save(admin);
            logger.info("... generating 100 Student entities...");
            ExampleDataGenerator<Student> studentRepositoryGenerator = new ExampleDataGenerator<>(Student.class,
                    LocalDateTime.of(2021, 9, 28, 0, 0, 0));
            studentRepositoryGenerator.setData(Student::setId, DataType.ID);
            studentRepositoryGenerator.setData(Student::setStudentID, DataType.NUMBER_UP_TO_1000);
            studentRepositoryGenerator.setData(Student::setFirstName, DataType.FIRST_NAME);
            studentRepositoryGenerator.setData(Student::setLastName, DataType.LAST_NAME);
            studentRepositoryGenerator.setData(Student::setDebt, DataType.NUMBER_UP_TO_1000);
            studentRepositoryGenerator.setData(Student::setStatus, DataType.WORD);
            studentRepositoryGenerator.setData(Student::setPhone, DataType.PHONE_NUMBER);
            studentRepositoryGenerator.setData(Student::setGroup, DataType.TWO_WORDS);
            studentRepositoryGenerator.setData(Student::setEmail, DataType.EMAIL);
            studentRepositoryGenerator.setData(Student::setDateOfBirth, DataType.DATE_LAST_10_YEARS);
            studentRepositoryGenerator.setData(Student::setGender, DataType.WORD);
            studentRepository.saveAll(studentRepositoryGenerator.create(100, seed));

            logger.info("... generating 100 Employee entities...");
            ExampleDataGenerator<Employee> employeeRepositoryGenerator = new ExampleDataGenerator<>(Employee.class,
                    LocalDateTime.of(2021, 9, 28, 0, 0, 0));
            employeeRepositoryGenerator.setData(Employee::setId, DataType.ID);
            employeeRepositoryGenerator.setData(Employee::setFirstName, DataType.FIRST_NAME);
            employeeRepositoryGenerator.setData(Employee::setLastName, DataType.LAST_NAME);
            employeeRepositoryGenerator.setData(Employee::setEmail, DataType.EMAIL);
            employeeRepositoryGenerator.setData(Employee::setPhone, DataType.PHONE_NUMBER);
            employeeRepositoryGenerator.setData(Employee::setDateOfBirth, DataType.DATE_LAST_10_YEARS);
            employeeRepositoryGenerator.setData(Employee::setPosition, DataType.OCCUPATION);
            employeeRepositoryGenerator.setData(Employee::setSalary, DataType.NUMBER_UP_TO_1000);
            employeeRepository.saveAll(employeeRepositoryGenerator.create(100, seed));

            logger.info("Generated demo data");
        };
    }

}
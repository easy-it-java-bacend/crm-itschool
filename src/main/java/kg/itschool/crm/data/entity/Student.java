package kg.itschool.crm.data.entity;

import javax.persistence.Entity;

import kg.itschool.crm.data.AbstractEntity;
import java.time.LocalDate;

@Entity
public class Student extends AbstractEntity {

    private Integer studentID;
    private String firstName;
    private String lastName;
    private Integer debt;
    private String status;
    private String phone;
    private String group;
    private String email;
    private LocalDate dateOfBirth;
    private String gender;

    public Integer getStudentID() {
        return studentID;
    }
    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Integer getDebt() {
        return debt;
    }
    public void setDebt(Integer debt) {
        this.debt = debt;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getGroup() {
        return group;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

}

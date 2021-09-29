package kg.itschool.crm.data.entity;

import javax.persistence.*;

import kg.itschool.crm.data.AbstractEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_student")
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@SequenceGenerator(name = "SEQ_ID", sequenceName = "SEQ_STUDENT")
public class Student extends BaseEntity {

    @Column(name = "student_id", nullable = false)
    Integer studentID;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name", nullable = false)
    String lastName;

    @Column(name = "debt", nullable = false)
    Integer debt;

    @Column(name = "status", nullable = false)
    String status;

    @Column(name = "phone", nullable = false)
    String phone;

    @Column(name = "group_id", nullable = false)
    String group;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "date_of_birth", nullable = false)
    LocalDate dateOfBirth;

    @Column(name = "gender", nullable = false)
    String gender;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    User user;
}

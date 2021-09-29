package kg.itschool.crm.data.entity;

import javax.persistence.*;

import kg.itschool.crm.data.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@SequenceGenerator(name = "SEQ_ID", sequenceName = "SEQ_USER")
public class User extends BaseEntity {

    String username;
    @JsonIgnore
    String hashedPassword;
    @ElementCollection(fetch = FetchType.EAGER)
    Set<Role> roles;
    @Lob
    String profilePictureUrl;


}

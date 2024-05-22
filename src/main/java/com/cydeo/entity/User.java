package com.cydeo.entity;

import com.cydeo.enums.Gender;
import lombok.*;
import org.hibernate.annotations.Where;
import org.hibernate.sql.ast.Clause;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Where(clause="is_deleted=false")
public class User extends BaseEntity {


    private String firstName;
    private String lastName;
    private String userName;
    private String passWord;
    private boolean enabled;
    private String phone;

    @ManyToOne  //many users to one role, create foreign key,many always get owner
    private Role role;
    @Enumerated(EnumType.STRING)
    private Gender gender;

}

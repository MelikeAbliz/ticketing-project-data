package com.cydeo.entity;

import com.cydeo.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
//whatever repository used task entity,all this queries belongs to that repository automatically will concatenate this (@where) statement right the way
@Where(clause = "is_deleted=false")//ignore all deleted data in the database
public class Task extends BaseEntity{
    //for mapper, field naming is important, not order.based the name getter setter

    private String taskSubject;
    private String taskDetail;
    @Enumerated(EnumType.STRING)
    private Status taskStatus;

    @Column(columnDefinition = "Date")
    private LocalDate assignedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_employee_id")//change column name match data sql column name
    private User assignedEmployee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")//not mandatory write this line
    private Project project;//this is project_id

}

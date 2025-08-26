package com.iassure.incident.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@Entity
@Table(name = "TaskMaster")
public class TaskMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TaskID")
    private Integer taskId;

    @Column(name = "DepartmentID")
    private Integer departmentId;


    @Column(name = "TaskName")
    private String taskName;
}

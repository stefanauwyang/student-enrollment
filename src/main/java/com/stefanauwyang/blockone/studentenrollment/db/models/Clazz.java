package com.stefanauwyang.blockone.studentenrollment.db.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Clazz {
    @Id
    private String name;
    @Column
    private Integer credit;
    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;
}

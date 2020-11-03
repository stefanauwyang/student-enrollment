package com.stefanauwyang.blockone.studentenrollment.db.models;

import lombok.Data;

import javax.persistence.*;

/**
 * Represents a class which linked to a semester.
 *
 */
@Entity(name = "class")
@Data
public class Course {

    @Id
    private String name;
    @Column
    private Integer credit;
    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;

}

package com.stefanauwyang.blockone.studentenrollment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(EnrollmentId.class)
@Data
public class Enrollment implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "semester_id")
    @JsonProperty("class")
    private Semester semester;
    @Id
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
    @Id
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}

package com.stefanauwyang.blockone.studentenrollment.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class EnrollmentId implements Serializable {

    private Student student;
    private Semester semester;
    private Subject subject;

}

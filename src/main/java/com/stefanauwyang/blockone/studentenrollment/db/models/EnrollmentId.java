package com.stefanauwyang.blockone.studentenrollment.db.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class EnrollmentId implements Serializable {

    private Student student;
    private Clazz clazz;

}

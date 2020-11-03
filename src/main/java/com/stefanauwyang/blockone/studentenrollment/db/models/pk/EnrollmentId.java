package com.stefanauwyang.blockone.studentenrollment.db.models.pk;

import com.stefanauwyang.blockone.studentenrollment.db.models.Clazz;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class EnrollmentId implements Serializable {

    private Student student;
    private Clazz clazz;

}

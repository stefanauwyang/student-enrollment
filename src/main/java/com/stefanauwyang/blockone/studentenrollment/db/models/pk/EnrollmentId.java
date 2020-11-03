package com.stefanauwyang.blockone.studentenrollment.db.models.pk;

import com.stefanauwyang.blockone.studentenrollment.db.models.Course;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Composite key for enrollment table.
 *
 */
@Embeddable
public class EnrollmentId implements Serializable {

    private Student student;
    private Course course;

}

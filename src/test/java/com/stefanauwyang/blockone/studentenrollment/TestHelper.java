package com.stefanauwyang.blockone.studentenrollment;

import com.stefanauwyang.blockone.studentenrollment.db.models.Course;
import com.stefanauwyang.blockone.studentenrollment.db.models.Semester;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;

public class TestHelper {

    public static final String NEW_STUDENT_FIRST_NAME = "John";
    public static final String NEW_STUDENT_FIRST_NAME_MODIFY = "Johnny";
    public static final String NEW_STUDENT_LAST_NAME = "Neo";
    public static final String NEW_STUDENT_NATIONALITY = "Singapore";

    public static final String NEW_SEMESTER_NAME = "2999-1";
    public static final String NEW_SEMESTER_STATUS = "OPEN";

    public static final String NEW_COURSE_NAME = "Test course name";
    public static final Integer NEW_COURSE_CREDIT = 4;

    /**
     * Create new student test object and return it.
     *
     * @return new student test object
     */
    public static Student newStudent() {
        return Student.builder()
                .firstName(NEW_STUDENT_FIRST_NAME)
                .lastName(NEW_STUDENT_LAST_NAME)
                .nationality(NEW_STUDENT_NATIONALITY)
                .build();
    }

    /**
     * Modify given student to update with test first name.
     *
     * @param student first name will be modified
     */
    public static void modifyStudentFirstName(Student student) {
        student.setFirstName(NEW_STUDENT_FIRST_NAME_MODIFY); // Fist name changed
    }

    /**
     * Create new semester test object and return it.
     *
     * @return new semester test object
     */
    public static Semester newSemester() {
        return Semester.builder()
                .name(NEW_SEMESTER_NAME)
                .status(NEW_SEMESTER_STATUS)
                .build();
    }

    /**
     * Create new class test object and return it.
     *
     * @return new class test object
     */
    public static Course newCourse() {
        return Course.builder()
                .name(NEW_COURSE_NAME)
                .credit(NEW_COURSE_CREDIT)
                .build();
    }

}

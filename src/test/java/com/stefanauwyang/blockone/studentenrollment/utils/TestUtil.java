package com.stefanauwyang.blockone.studentenrollment.utils;

import com.stefanauwyang.blockone.studentenrollment.db.models.Student;

public class TestUtil {

    public static final String NEW_STUDENT_FIRST_NAME = "John";
    public static final String NEW_STUDENT_FIRST_NAME_MODIFY = "Johnny";
    public static final String NEW_STUDENT_LAST_NAME = "Neo";
    public static final String NEW_STUDENT_NATIONALITY = "Singapore";

    public static Student newStudent() {
        return Student.builder()
                .firstName(NEW_STUDENT_FIRST_NAME)
                .lastName(NEW_STUDENT_LAST_NAME)
                .nationality(NEW_STUDENT_NATIONALITY)
                .build();
    }

    public static void modifyStudentFirstName(Student student) {
        student.setFirstName(NEW_STUDENT_FIRST_NAME_MODIFY); // Fist name changed
    }

}

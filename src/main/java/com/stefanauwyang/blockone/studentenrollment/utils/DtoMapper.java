package com.stefanauwyang.blockone.studentenrollment.utils;

import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import com.stefanauwyang.blockone.studentenrollment.services.dto.StudentDto;

public class DtoMapper {

    public static Student asStudent(StudentDto dto) {
        Student student = Student.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .nationality(dto.getNationality())
                .build();

        return student;
    }

}

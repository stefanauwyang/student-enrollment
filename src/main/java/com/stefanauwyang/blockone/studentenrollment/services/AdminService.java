package com.stefanauwyang.blockone.studentenrollment.services;

import com.stefanauwyang.blockone.studentenrollment.db.models.Semester;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import com.stefanauwyang.blockone.studentenrollment.db.repos.SemesterRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Services for an administrator.
 *
 */
@Service
public class AdminService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    /**
     * API to add new or modify student by admin.
     *
     * @param student to add or modify
     * @return student from db
     */
    public Student addOrModifyStudent(Student student) {
        return studentRepository.save(student);
    }

    /**
     * API to create a semester by admin.
     *
     * @param semester to create
     * @return semester from db
     */
    public Semester createSemester(Semester semester) {
        return semesterRepository.save(semester);
    }

}

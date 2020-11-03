package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.db.models.Enrollment;
import com.stefanauwyang.blockone.studentenrollment.db.repos.ClassRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.EnrollmentRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST APIs for a student operations.
 *
 */
@RestController
@RequestMapping("/api/student")
public class StudentOpController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    /**
     * Enroll to a class by student.
     *
     * @param enrollment
     * @return
     */
    @PostMapping("/enrollments")
    public ResponseEntity enroll(@RequestBody Enrollment enrollment) {
        try {
            enrollment = enrollmentRepository.save(enrollment);
            return ResponseEntity.ok(enrollment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get current enrollments.
     *
     * @return
     */
    @GetMapping("/enrollments")
    public ResponseEntity enrollments() {
        Iterable<Enrollment> enrollments = enrollmentRepository.findAll();
        return ResponseEntity.ok(enrollments);
    }

}

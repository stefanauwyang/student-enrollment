package com.stefanauwyang.blockone.studentenrollment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stefanauwyang.blockone.studentenrollment.db.models.*;
import com.stefanauwyang.blockone.studentenrollment.db.repos.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/student")
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    @GetMapping("/enrollments")
    public ResponseEntity enrollments() {
        Iterable<Enrollment> enrollments = enrollmentRepository.findAll();
        return ResponseEntity.ok(enrollments);
    }

    @PostMapping("/enrollments")
    public ResponseEntity enroll(@RequestBody Enrollment enrollment) {
        enrollment = enrollmentRepository.save(enrollment);
        return ResponseEntity.ok(enrollment);
    }

}

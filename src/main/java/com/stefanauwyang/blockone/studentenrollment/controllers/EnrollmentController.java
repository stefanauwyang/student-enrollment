package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.model.Enrollment;
import com.stefanauwyang.blockone.studentenrollment.model.Student;
import com.stefanauwyang.blockone.studentenrollment.model.Subject;
import com.stefanauwyang.blockone.studentenrollment.repos.EnrollmentRepository;
import com.stefanauwyang.blockone.studentenrollment.repos.StudentRepository;
import com.stefanauwyang.blockone.studentenrollment.repos.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

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

    @GetMapping("/fetchStudents")
    public ResponseEntity fetchStudents(@RequestParam(value = "class", required = false) String className,
                                        @RequestParam(value = "id", required = false) Long studentId) {

        if (className != null && !className.isEmpty()) {
            Subject subject = subjectRepository.findByClassName(className);
            Iterable<Enrollment> enrollments = enrollmentRepository.findBySubject(subject);
            return ResponseEntity.ok(enrollments);
        } else if (studentId != null) {
            Iterable<Enrollment> enrollments = enrollmentRepository.findByStudent(Student.builder().id(studentId).build());
            return ResponseEntity.ok(enrollments);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

}

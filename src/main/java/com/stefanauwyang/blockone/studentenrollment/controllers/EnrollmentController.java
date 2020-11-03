package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.db.models.Clazz;
import com.stefanauwyang.blockone.studentenrollment.db.models.Enrollment;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import com.stefanauwyang.blockone.studentenrollment.db.repos.ClazzRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.EnrollmentRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClazzRepository clazzRepository;

    @GetMapping("/enrollments")
    public ResponseEntity enrollments() {
        Iterable<Enrollment> enrollments = enrollmentRepository.findAll();
        return ResponseEntity.ok(enrollments);
    }

    /**
     * Enroll to a class by student.
     *
     * @param enrollment to be created
     * @return enrollment from db
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

    @GetMapping("/enrollments/classes/{className}/students")
    public ResponseEntity fetchStudentsByEnrolledClass(@PathVariable("className") String className) {
        Optional<Clazz> clazz = clazzRepository.findByName(className);
        if (clazz.isPresent()) {
            Iterable<Enrollment> enrollments = enrollmentRepository.findByClazz(clazz.get());
            List<Student> students = StreamSupport.stream(enrollments.spliterator(), false)
                    .map(enrollment -> enrollment.getStudent())
                    .collect(Collectors.toList());
            return ResponseEntity.ok(students);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

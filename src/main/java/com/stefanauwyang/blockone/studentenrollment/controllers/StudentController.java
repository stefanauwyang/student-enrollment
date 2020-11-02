package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.model.Student;
import com.stefanauwyang.blockone.studentenrollment.repos.EnrollmentRepository;
import com.stefanauwyang.blockone.studentenrollment.repos.StudentRepository;
import com.stefanauwyang.blockone.studentenrollment.repos.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    /**
     * List all students.
     *
     * @return
     */
    @GetMapping("/students")
    public ResponseEntity students() {
        Iterable<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(students);
    }

    /**
     * Add new student.
     *
     * @param student
     * @return
     */
    @PostMapping("/students")
    public ResponseEntity addStudent(@RequestBody Student student) {
        Student db_student = studentRepository.save(student);
        return ResponseEntity.ok(db_student);
    }

    /**
     * Modify existing student.
     *
     * @param student
     * @return
     */
    @PutMapping("/students")
    public ResponseEntity modifyStudent(@RequestBody Student student) {
        Optional<Student> db_student = studentRepository.findById(student.getId());
        if (db_student.isPresent()) {
            student.setId(db_student.get().getId());
            student = studentRepository.save(student);
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

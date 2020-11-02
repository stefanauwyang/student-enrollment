package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.model.Student;
import com.stefanauwyang.blockone.studentenrollment.repos.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    private List<Student> students = new ArrayList<>();

    @GetMapping("/students")
    public ResponseEntity students() {
        Iterable<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(students);
    }

    @PostMapping("/students")
    public ResponseEntity create(@RequestBody Student student) {
        Student db_student = studentRepository.save(student);
        return ResponseEntity.ok(db_student);
    }

    @PutMapping("/students")
    public ResponseEntity modify(@RequestBody Student student) {
        Optional<Student> db_student = studentRepository.findById(student.getId());
        if (db_student.isPresent()) {
            student.setId(db_student.get().getId());
            student = studentRepository.save(student);
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/students")
    public ResponseEntity delete(@RequestBody Student student) {
        studentRepository.delete(student);
        return ResponseEntity.ok(student);
    }
}

package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.model.Student;
import com.stefanauwyang.blockone.studentenrollment.repos.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    
    private List<Student> students = new ArrayList<>();

    public StudentController() {
    }

    @GetMapping("/students")
    public ResponseEntity students() {
        Iterable<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(students);
    }

    @PostMapping("/students")
    public ResponseEntity create(@RequestBody Student student) {
        students.add(student);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/students")
    public ResponseEntity delete(@RequestBody Student student) {
        students = students.stream().filter(user -> !user.getId().equals(student.getId())).collect(Collectors.toList());
        return ResponseEntity.ok(student);
    }
}

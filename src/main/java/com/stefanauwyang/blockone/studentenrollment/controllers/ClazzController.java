package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.db.models.Clazz;
import com.stefanauwyang.blockone.studentenrollment.db.repos.ClazzRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ClazzController {

    @Autowired
    private ClazzRepository clazzRepository;

    @Autowired
    private StudentRepository studentRepository;

    /**
     * API to list all classes.
     *
     * @return all semesters
     */
    @GetMapping("/classes")
    public ResponseEntity classes() {
        Iterable<Clazz> classes = clazzRepository.findAll();
        return ResponseEntity.ok(classes);
    }

}

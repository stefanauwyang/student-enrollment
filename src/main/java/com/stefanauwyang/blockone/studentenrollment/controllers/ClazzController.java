package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.db.models.Course;
import com.stefanauwyang.blockone.studentenrollment.db.repos.CourseRepository;
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
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    /**
     * API to list all classes.
     *
     * @return all semesters
     */
    @GetMapping("/classes")
    public ResponseEntity classes() {
        Iterable<Course> classes = courseRepository.findAll();
        return ResponseEntity.ok(classes);
    }

}

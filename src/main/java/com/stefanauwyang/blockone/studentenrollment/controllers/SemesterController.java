package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.db.models.Semester;
import com.stefanauwyang.blockone.studentenrollment.db.repos.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SemesterController {

    @Autowired
    private SemesterRepository semesterRepository;

    @GetMapping("/semesters")
    public ResponseEntity semesters() {
        Iterable<Semester> semesters = semesterRepository.findAll();
        return ResponseEntity.ok(semesters);
    }

    /**
     * API to create a new semester by administrator.
     *
     * @param semester
     * @return
     */
    @PostMapping("/semesters")
    public ResponseEntity createSemester(@RequestBody Semester semester) {
        semester = semesterRepository.save(semester);
        return ResponseEntity.ok(semester);
    }

}

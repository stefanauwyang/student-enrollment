package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.db.models.Clazz;
import com.stefanauwyang.blockone.studentenrollment.db.models.Semester;
import com.stefanauwyang.blockone.studentenrollment.db.repos.ClazzRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
public class SemesterController {

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private ClazzRepository clazzRepository;

    /**
     * API to list all semesters.
     *
     * @return all semesters
     */
    @GetMapping("/semesters")
    public ResponseEntity semesters() {
        Iterable<Semester> semesters = semesterRepository.findAll();
        return ResponseEntity.ok(semesters);
    }

    /**
     * API to create a new semester.
     * This API should be used by an administrator.
     *
     * @param semester to be created
     * @return semester from db
     */
    @PostMapping("/semesters")
    public ResponseEntity createSemester(@RequestBody Semester semester) {
        semester = semesterRepository.save(semester);
        return ResponseEntity.ok(semester);
    }

    /**
     * API to list classes on a semester.
     *
     * @param semesterId as filter
     * @return list of classes
     */
    @GetMapping("/semesters/{semesterId}/classes")
    public ResponseEntity semesterClasses(@PathVariable Long semesterId) {
        Optional<Semester> semester = semesterRepository.findById(semesterId);
        if (semester.isPresent()) {
            Iterable<Clazz> classes = clazzRepository.findBySemester(semester);
            return ResponseEntity.ok(classes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

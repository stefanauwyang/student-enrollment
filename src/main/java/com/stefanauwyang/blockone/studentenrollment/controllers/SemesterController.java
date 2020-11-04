package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.db.models.Semester;
import com.stefanauwyang.blockone.studentenrollment.db.repos.CourseRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Group of APIs to serve semester related operations.
 */
@RestController
public class SemesterController {

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private CourseRepository courseRepository;

    /**
     * API to list all semesters.
     *
     * @param id     as optional filter
     * @param name   as optional filter
     * @param status as optional filter
     * @return all filtered semesters
     */
    @GetMapping("/semesters")
    public ResponseEntity semesters(@RequestParam("id") Optional<Long> id,
                                    @RequestParam("name") Optional<String> name,
                                    @RequestParam("status") Optional<String> status) {

        List<Semester> semesters;

        if (id.isPresent() || name.isPresent() || status.isPresent()) {
            semesters = semesterRepository.findAllByIdOrNameOrStatus(id, name, status);
        } else {
            semesters = semesterRepository.findAll();
        }

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

}

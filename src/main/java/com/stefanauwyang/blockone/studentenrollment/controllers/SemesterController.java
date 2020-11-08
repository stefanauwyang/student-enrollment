package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.db.models.Semester;
import com.stefanauwyang.blockone.studentenrollment.db.repos.CourseRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.SemesterRepository;
import com.stefanauwyang.blockone.studentenrollment.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(SemesterController.class);

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private CourseRepository courseRepository;

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
     * API to modify existing semesters.
     *
     * @param semesterId as id to be modified
     * @param semester   as data to be modified
     * @return semester from db
     */
    @PutMapping("/semesters/{semesterId}")
    public ResponseEntity modifySemester(@PathVariable("semesterId") Long semesterId,
                                         @RequestBody Semester semester) {
        Optional<Semester> db_semester = semesterRepository.findById(semesterId);
        if (db_semester.isPresent()) {
            semester.setId(semesterId);
            semester = semesterRepository.save(semester);
            return ResponseEntity.ok(semester);
        } else {
            throw new BadRequestException("Semester id does not exists");
        }
    }

    /**
     * API to delete existing semester.
     *
     * @param semesterId to be deleted
     * @return deleted semesterId
     */
    @DeleteMapping("/semesters/{semesterId}")
    public ResponseEntity deleteSemester(@PathVariable("semesterId") Long semesterId) {
        Optional<Semester> db_semester = semesterRepository.findById(semesterId);
        if (db_semester.isPresent()) {
            Semester semester = db_semester.get();
            semesterRepository.delete(semester);
            return ResponseEntity.ok(semesterId);
        } else {
            throw new BadRequestException("Semester id does not exists");
        }
    }

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
            semesters = semesterRepository.findAllByIdOrNameIgnoreCaseOrStatusIgnoreCase(id, name, status);
        } else {
            semesters = semesterRepository.findAll();
        }

        return ResponseEntity.ok(semesters);

    }

}

package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.db.models.Clazz;
import com.stefanauwyang.blockone.studentenrollment.db.models.Enrollment;
import com.stefanauwyang.blockone.studentenrollment.db.models.Semester;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import com.stefanauwyang.blockone.studentenrollment.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST APIs for an administrator operations.
 *
 */
@RestController
@RequestMapping("/api/admin")
public class AdminOpController {

    @Autowired
    private AdminService adminService;

    /**
     * Add new or modify existing student.
     *
     * @param student
     * @return
     */
    @RequestMapping(path = "/students", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity addOrModifyStudent(@RequestBody Student student) {
        Student db_student = adminService.addOrModifyStudent(student);
        return ResponseEntity.ok(db_student);
    }

    /**
     * Create new semester.
     *
     * @param semester
     * @return
     */
    @PostMapping("/semesters")
    public ResponseEntity createSemester(@RequestBody Semester semester) {
        Semester db_semester = adminService.createSemester(semester);
        return ResponseEntity.ok(db_semester);
    }

}

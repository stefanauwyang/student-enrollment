package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.db.models.Course;
import com.stefanauwyang.blockone.studentenrollment.db.models.Enrollment;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import com.stefanauwyang.blockone.studentenrollment.db.repos.CourseRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.EnrollmentRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.StudentRepository;
import com.stefanauwyang.blockone.studentenrollment.exceptions.BadRequestException;
import com.stefanauwyang.blockone.studentenrollment.exceptions.UnprocessableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Group of APIs to serve class related operations.
 */
@RestController
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    /**
     * API to create a new class (course).
     *
     * @param course to be created
     * @return course from db
     */
    @PostMapping("/classes")
    public ResponseEntity createClass(@RequestBody Course course) {
        Course db_course = courseRepository.save(course);
        return ResponseEntity.ok(db_course);
    }

    /**
     * API to modify existing class (course).
     *
     * @param classId as id to be modified
     * @param course  as data to be modified
     * @return course from db
     */
    @PutMapping("/classes/{classId}")
    public ResponseEntity modifyClass(@PathVariable("classId") Long classId,
                                      @RequestBody Course course) {
        Optional<Course> db_course = courseRepository.findById(classId);
        if (db_course.isPresent()) {
            course.setId(classId);
            course = courseRepository.save(course);
            return ResponseEntity.ok(course);
        } else {
            throw new BadRequestException("Class id does not exists");
        }
    }

    /**
     * API to delete existing class (course).
     *
     * @param classId to be deleted
     * @return deleted classId
     */
    @DeleteMapping("/classes/{classId}")
    public ResponseEntity deleteCourse(@PathVariable("classId") Long classId) {
        Optional<Course> db_course = courseRepository.findById(classId);
        if (db_course.isPresent()) {
            Course course = db_course.get();
            courseRepository.delete(course);
            return ResponseEntity.ok(classId);
        } else {
            throw new BadRequestException("Class id does not exists");
        }
    }

    /**
     * API to find existing class (course) by its id.
     *
     * @param classId as id to be retrieved
     * @return class from db
     */
    @GetMapping("/classes/{classId}")
    public ResponseEntity getClassById(@PathVariable("classId") Long classId) {
        Optional<Course> db_course = courseRepository.findById(classId);
        if (db_course.isPresent()) {
            return ResponseEntity.ok(db_course);
        } else {
            throw new BadRequestException("Class id does not exists");
        }
    }

    /**
     * API to list classes with or without filter.
     *
     * @param name   as optional filter
     * @param credit as optional filter
     * @return classes from db
     */
    @GetMapping("/classes")
    public ResponseEntity fetchClasses(@RequestParam(value = "name") Optional<String> name,
                                       @RequestParam(value = "credit") Optional<Integer> credit) {
        List<Course> courses;
        if (name.isPresent()
                || credit.isPresent()) {
            courses = courseRepository.findAllByNameContainingIgnoreCaseOrCredit(name, credit);
        } else {
            courses = courseRepository.findAll();
        }
        return ResponseEntity.ok(courses);
    }

    /**
     * Get list of students registered in a class.
     *
     * @param classId
     * @return list of enrolled students
     */
    @GetMapping("/classes/{classId}/students")
    public ResponseEntity studentsInClassId(@PathVariable("classId") Long classId) {

        // Find if class exists
        Optional<Course> db_course = courseRepository.findById(classId);

        // If class not exists, no need to continue
        if (!db_course.isPresent()) {
            throw new BadRequestException("Class id does not exists");
        }

        // Find enrollments and return the students
        List<Enrollment> enrollments = enrollmentRepository.findAllByCourse(db_course.get());
        List<Student> students = enrollments.stream()
                .map(Enrollment::getStudent)
                .collect(Collectors.toList());
        return ResponseEntity.ok(students);
    }

}

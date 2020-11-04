package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.db.models.Course;
import com.stefanauwyang.blockone.studentenrollment.db.models.Enrollment;
import com.stefanauwyang.blockone.studentenrollment.db.models.Semester;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import com.stefanauwyang.blockone.studentenrollment.db.repos.CourseRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.EnrollmentRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.StudentRepository;
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

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

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
            courses = courseRepository.findAllByNameOrCredit(name, credit);
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
    public ResponseEntity classStudents(@PathVariable("classId") Long classId) {

        // Find if class exists
        Optional<Course> db_course = courseRepository.findById(classId);

        // If class not exists, no need to continue
        if (!db_course.isPresent()) return ResponseEntity.notFound().build();

        // Find enrollments and return the students
        List<Enrollment> enrollments = enrollmentRepository.findAllByCourse(db_course.get());
        List<Student> students = enrollments.stream()
                .map(Enrollment::getStudent)
                .collect(Collectors.toList());
        return ResponseEntity.ok(students);
    }

    /**
     * API to create a new class (course).
     *
     * @param course to be created
     * @return course from db
     */
    @PostMapping("/classes")
    public ResponseEntity createClass(@RequestBody Course course) {
        course = courseRepository.save(course);
        return ResponseEntity.ok(course);
    }

}

package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.db.models.Course;
import com.stefanauwyang.blockone.studentenrollment.db.models.Enrollment;
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
import java.util.stream.StreamSupport;

/**
 * Group of APIs to serve enrollment related operations.
 *
 */
@RestController
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/enrollments")
    public ResponseEntity enrollments() {
        Iterable<Enrollment> enrollments = enrollmentRepository.findAll();
        return ResponseEntity.ok(enrollments);
    }

    /**
     * Enroll to a class by student.
     *
     * @param enrollment to be created
     * @return enrollment from db
     */
    @PostMapping("/enrollments")
    public ResponseEntity enroll(@RequestBody Enrollment enrollment) {
        try {
            enrollment = enrollmentRepository.save(enrollment);
            return ResponseEntity.ok(enrollment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Enroll student to a class.
     *
     * @param className to be enrolled by a studentId
     * @param studentId to be enrolled to a class
     * @return enrollment from db
     */
    @PostMapping("/enrollments/classes/{className}/students/{studentId}/enroll")
    public ResponseEntity enrollStudentToClass(@PathVariable("className") String className,
                                               @PathVariable("studentId") Long studentId) {

        Optional<Course> db_course = courseRepository.findByName(className);
        Optional<Student> db_student = studentRepository.findById(studentId);

        if (!db_course.isPresent() || !db_student.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {

            Optional<Enrollment> db_enrollment = enrollmentRepository.findByCourseAndStudent(db_course.get(), db_student.get());

            if (db_enrollment.isPresent()) {

                return ResponseEntity.unprocessableEntity().build();

            } else {

                Enrollment enrollment = Enrollment.builder()
                        .course(db_course.get())
                        .student(db_student.get())
                        .build();
                enrollment = enrollmentRepository.save(enrollment);
                return ResponseEntity.ok(enrollment);

            }

        }

    }

    /**
     * Get list of students enrolled to a class.
     *
     * @param className as filter
     * @return list of students enrolled to given class.
     */
    @GetMapping("/enrollments/classes/{className}/students")
    public ResponseEntity fetchStudentsByEnrolledClass(@PathVariable("className") String className) {
        Optional<Course> course = courseRepository.findByName(className);
        if (course.isPresent()) {
            Iterable<Enrollment> enrollments = enrollmentRepository.findAllByCourse(course.get());
            List<Student> students = StreamSupport.stream(enrollments.spliterator(), true)
                    .map(Enrollment::getStudent)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(students);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

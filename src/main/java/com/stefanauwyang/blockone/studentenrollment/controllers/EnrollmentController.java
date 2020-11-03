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

@RestController
@RequestMapping
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

    @PostMapping("/enrollments/classes/{className}/students/{studentId}/enroll")
    public ResponseEntity enrollStudentToClass(@PathVariable("courseName") String courseName,
                                               @PathVariable("studentId") Long studentId) {

        Optional<Course> db_course = courseRepository.findByName(courseName);
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

    @GetMapping("/enrollments/classes/{courseName}/students")
    public ResponseEntity fetchStudentsByEnrolledClass(@PathVariable("courseName") String courseName) {
        Optional<Course> course = courseRepository.findByName(courseName);
        if (course.isPresent()) {
            Iterable<Enrollment> enrollments = enrollmentRepository.finAllByCourse(course.get());
            List<Student> students = StreamSupport.stream(enrollments.spliterator(), false)
                    .map(enrollment -> enrollment.getStudent())
                    .collect(Collectors.toList());
            return ResponseEntity.ok(students);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

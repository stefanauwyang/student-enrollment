package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.db.models.Course;
import com.stefanauwyang.blockone.studentenrollment.db.models.Enrollment;
import com.stefanauwyang.blockone.studentenrollment.db.models.Semester;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import com.stefanauwyang.blockone.studentenrollment.db.repos.CourseRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.EnrollmentRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.SemesterRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Group of APIs to serve enrollment related operations.
 */
@RestController
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/enrollments")
    public ResponseEntity getEnrollments() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        return ResponseEntity.ok(enrollments);
    }

    /**
     * Create enrollment record.
     *
     * @param enrollment to be created
     * @return enrollment from db
     */
    @PostMapping("/enrollments")
    public ResponseEntity createEnrollment(@RequestBody Enrollment enrollment) {

        try {
            enrollment = enrollmentRepository.save(enrollment);
            return ResponseEntity.ok(enrollment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        
    }

    /**
     * Enroll student to a semester and class.
     *
     * @param semesterId to be enrolled to
     * @param classId    to be enrolled to
     * @param studentId  for enrollment
     * @return enrollment from db
     */
    @PostMapping("/enrollments/semester/{semesterId}/classes/{classId}/students/{studentId}/enroll")
    public ResponseEntity enrollStudentToClass(@PathVariable("semesterId") Long semesterId,
                                               @PathVariable("classId") Long classId,
                                               @PathVariable("studentId") Long studentId) {

        Optional<Semester> db_semester = semesterRepository.findById(classId);

        // If semester not present, no need to continue
        if (!db_semester.isPresent()) return ResponseEntity.notFound().build();

        // Check if semester is open for registration
        if (!"OPEN".equals(db_semester.get().getStatus())) return ResponseEntity.badRequest().build();

        Optional<Course> db_course = courseRepository.findById(classId);

        // If course not found, no need to continue
        if (!db_course.isPresent()) return ResponseEntity.notFound().build();

        Optional<Student> db_student = studentRepository.findById(studentId);

        // If student not found, no need to continue
        if (!db_student.isPresent()) return ResponseEntity.notFound().build();

        // Check if there is already existing enrollment
        Optional<Enrollment> db_enrollment = enrollmentRepository.findByStudentAndSemesterAndCourse(db_student.get(), db_semester.get(), db_course.get());

        // Check if enrollment already existing
        if (db_enrollment.isPresent()) return ResponseEntity.unprocessableEntity().build();

        // Enroll the student to course in semester
        Enrollment enrollment = Enrollment.builder()
                .semester(db_semester.get())
                .course(db_course.get())
                .student(db_student.get())
                .build();
        enrollment = enrollmentRepository.save(enrollment);
        return ResponseEntity.ok(enrollment);

    }

    /**
     * Get list of students enrolled to a class.
     *
     * @param classId as filter
     * @return list of students enrolled to given class.
     */
    @GetMapping("/enrollments/classes/{classId}/students")
    public ResponseEntity fetchStudentsByEnrolledClass(@PathVariable("classId") Long classId) {

        Optional<Course> course = courseRepository.findById(classId);

        // Check if course not found
        if (!course.isPresent()) return ResponseEntity.notFound().build();

        // Find all enrollments for a class from db
        List<Enrollment> enrollments = enrollmentRepository.findAllByCourse(course.get());
        List<Student> students = enrollments.stream()
                .map(Enrollment::getStudent)
                .collect(Collectors.toList());

        return ResponseEntity.ok(students);

    }

}

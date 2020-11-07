package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.db.models.Course;
import com.stefanauwyang.blockone.studentenrollment.db.models.Enrollment;
import com.stefanauwyang.blockone.studentenrollment.db.models.Semester;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import com.stefanauwyang.blockone.studentenrollment.db.repos.CourseRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.EnrollmentRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.SemesterRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.StudentRepository;
import com.stefanauwyang.blockone.studentenrollment.exceptions.CreditException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(EnrollmentController.class);

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private CourseRepository courseRepository;

    /**
     * Create enrollment record.
     *
     * @param enrollment to be created
     * @return enrollment from db
     */
    @PostMapping("/enrollments")
    public ResponseEntity createEnrollment(@RequestBody Enrollment enrollment) {
        return enrollStudentToClass(enrollment.getSemester().getId(),
                enrollment.getCourse().getId(),
                enrollment.getStudent().getId());
    }

    /**
     * Enroll student to a semester and class without sending request body in payload.
     *
     * @param semesterId to be enrolled to
     * @param classId    to be enrolled to
     * @param studentId  for enrollment
     * @return enrollment from db
     */
    @PostMapping("/enrollments/semesters/{semesterId}/classes/{classId}/students/{studentId}/enroll")
    public ResponseEntity enrollStudentToClass(@PathVariable("semesterId") Long semesterId,
                                               @PathVariable("classId") Long classId,
                                               @PathVariable("studentId") Long studentId) {

        Optional<Semester> db_semester = semesterRepository.findById(semesterId);

        // If semester not present, no need to continue
        if (!db_semester.isPresent()) {
            logger.info("Semester does not exists");
            return ResponseEntity.notFound().build();
        }

        // Check if semester is open for registration
        if (!Semester.OPEN.equals(db_semester.get().getStatus())) {
            logger.info("Semester status is not OPEN for registration, status: {}", db_semester.get().getStatus());
            return ResponseEntity.badRequest().build();
        }

        Optional<Course> db_course = courseRepository.findById(classId);

        // If course not found, no need to continue
        if (!db_course.isPresent()) {
            logger.info("Class id does not exists");
            return ResponseEntity.notFound().build();
        }

        Optional<Student> db_student = studentRepository.findById(studentId);

        // If student not found, no need to continue
        if (!db_student.isPresent()) {
            logger.info("Student id does not exists");
            return ResponseEntity.notFound().build();
        }

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

        // Validate credits. Fetch enrollments from db where student_id , and semester_id
        List<Enrollment> enrollments = enrollmentRepository.findAllByStudentIdAndSemesterId(enrollment.getStudent().getId(),
                enrollment.getSemester().getId());

        // Calculate credits if enrolled
        int sum = enrollments.stream()
                .mapToInt(e -> e.getCourse().getCredit())
                .sum();
        sum += db_course.get().getCredit();

        // If max credit reached, throw exception
        if (sum > 20) throw new CreditException("Credit exceed maximum enroll credits 20 per semester");

        enrollment = enrollmentRepository.save(enrollment);
        return ResponseEntity.ok(enrollment);

    }

    /**
     * API to delete enrollment.
     *
     * @param enrollmentId to delete
     * @return deleted enrollmentId
     */
    @DeleteMapping("/enrollments/{enrollmentId}")
    public ResponseEntity deleteEnrollment(@PathVariable("enrollmentId") Long enrollmentId) {
        Optional<Enrollment> db_enrollment = enrollmentRepository.findById(enrollmentId);
        if (db_enrollment.isPresent()) {
            Enrollment enrollment = db_enrollment.get();
            enrollmentRepository.delete(enrollment);
            return ResponseEntity.ok(enrollmentId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * API to list enrollments with filter.
     *
     * @param student_id  as optional filter
     * @param semester_id as optional filter
     * @param class_id    as optional filter
     * @return enrollments from db
     */
    @GetMapping("/enrollments")
    public ResponseEntity fetchStudents(@RequestParam(value = "student_id") Optional<Long> student_id,
                                        @RequestParam(value = "semester_id") Optional<Long> semester_id,
                                        @RequestParam(value = "class_id") Optional<Long> class_id) {

        List<Enrollment> enrollments;

        if (student_id.isPresent() || semester_id.isPresent() || class_id.isPresent()) {
            enrollments = enrollmentRepository.findAllByStudentIdOrSemesterIdOrCourseId(
                    student_id,
                    semester_id,
                    class_id);
        } else {
            enrollments = enrollmentRepository.findAll();
        }

        return ResponseEntity.ok(enrollments);

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

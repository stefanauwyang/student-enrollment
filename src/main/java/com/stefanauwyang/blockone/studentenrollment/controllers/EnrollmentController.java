package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.db.models.Course;
import com.stefanauwyang.blockone.studentenrollment.db.models.Enrollment;
import com.stefanauwyang.blockone.studentenrollment.db.models.Semester;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import com.stefanauwyang.blockone.studentenrollment.db.repos.CourseRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.EnrollmentRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.SemesterRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.StudentRepository;
import com.stefanauwyang.blockone.studentenrollment.exceptions.BadRequestException;
import com.stefanauwyang.blockone.studentenrollment.exceptions.UnprocessableException;
import com.stefanauwyang.blockone.studentenrollment.exceptions.ValidationException;
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
        logger.debug("Request contains body: " + enrollment);
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
        logger.debug("Request received to enroll student id " + studentId + " to class id " + classId

                + " on semester id " + semesterId);
        Optional<Semester> db_semester = semesterRepository.findById(semesterId);

        // If semester not present, no need to continue
        if (!db_semester.isPresent()) {
            logger.error("Semester id not found: " + semesterId);
            throw new BadRequestException("Semester does not exists");
        }

        // Check if semester is open for registration
        if (!Semester.OPEN.equals(db_semester.get().getStatus())) {
            logger.error("Semester id: " + semesterId + " status is not OPEN but " + db_semester.get().getStatus());
            throw new BadRequestException("Semester status is not OPEN for registration, status: " + db_semester.get().getStatus());
        }

        Optional<Course> db_course = courseRepository.findById(classId);

        // If course not found, no need to continue
        if (!db_course.isPresent()) {
            logger.error("Class id not found: " + classId);
            throw new BadRequestException("Class id does not exists");
        }

        Optional<Student> db_student = studentRepository.findById(studentId);

        // If student not found, no need to continue
        if (!db_student.isPresent()) {
            logger.error("Student id not found: " + studentId);
            throw new BadRequestException("Student id does not exists");
        }

        // Check if there is already existing enrollment
        Optional<Enrollment> db_enrollment = enrollmentRepository.findByStudentAndSemesterAndCourse(db_student.get(), db_semester.get(), db_course.get());

        // Check if enrollment already existing
        if (db_enrollment.isPresent()) {
            logger.error("Enrollment id already exists: " + studentId);
            throw new UnprocessableException("Enrollment already exists");
        }

        // Enroll the student to course in semester
        Enrollment enrollment = Enrollment.builder()
                .semester(db_semester.get())
                .course(db_course.get())
                .student(db_student.get())
                .build();

        logger.debug("Student id " + studentId + " enrolling for class id " + classId + " on "
                + semesterId + " semester.");

        // Validate credits. Fetch enrollments from db where student_id , and semester_id
        List<Enrollment> enrollments = enrollmentRepository.findAllByStudentIdAndSemesterId(enrollment.getStudent().getId(),
                enrollment.getSemester().getId());

        // Calculate credits if enrolled
        int sum = enrollments.stream()
                .mapToInt(e -> e.getCourse().getCredit())
                .sum();

        logger.debug("Total existing credits: " + sum + ", credits will be added: " + db_course.get().getCredit());
        sum += db_course.get().getCredit();

        // If max credit reached, throw exception
        if (sum > 20) {
            logger.error("Rejecting as total credits will be over 20 (" + sum + ") if enrolled student id " + studentId
                    + " to class id " + classId + " on semester id " + semesterId);
            throw new ValidationException("Credit exceed maximum enroll credits 20 per semester");
        }

        enrollment = enrollmentRepository.save(enrollment);

        logger.debug("Response contains body: " + enrollment);
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
        logger.debug("Request contains path enrollmentId: " + enrollmentId);
        Optional<Enrollment> db_enrollment = enrollmentRepository.findById(enrollmentId);
        if (db_enrollment.isPresent()) {
            Enrollment enrollment = db_enrollment.get();
            enrollmentRepository.delete(enrollment);
            logger.debug("Request contains body: " + enrollmentId);
            return ResponseEntity.ok(enrollmentId);
        } else {
            logger.error("Given enrollment id not found in database");
            throw new BadRequestException("Enrollment does not exists");
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
        logger.debug("Request contains path student_id: " + student_id);
        logger.debug("Request contains path semester_id: " + semester_id);
        logger.debug("Request contains path class_id: " + class_id);

        List<Enrollment> enrollments;

        if (student_id.isPresent() || semester_id.isPresent() || class_id.isPresent()) {
            logger.debug("Fetching with paths");
            enrollments = enrollmentRepository.findAllByStudentIdOrSemesterIdOrCourseId(
                    student_id,
                    semester_id,
                    class_id);
        } else {
            logger.debug("Fetching without paths");
            enrollments = enrollmentRepository.findAll();
        }

        logger.debug("Response contains body: " + enrollments);
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
        logger.debug("Request contains path classId: " + classId);

        Optional<Course> course = courseRepository.findById(classId);

        // Check if course not found
        if (!course.isPresent()) {
            logger.error("Class id not found in database");
            throw new BadRequestException("Class does not exists");
        }

        // Find all enrollments for a class from db
        List<Enrollment> enrollments = enrollmentRepository.findAllByCourse(course.get());
        List<Student> students = enrollments.stream()
                .map(Enrollment::getStudent)
                .collect(Collectors.toList());

        logger.debug("Response contains body: " + students);
        return ResponseEntity.ok(students);

    }

}

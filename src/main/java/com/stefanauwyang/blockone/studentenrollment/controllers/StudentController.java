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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Group of APIs to serve student related operations.
 */
@RestController
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    /**
     * API to add new student.
     *
     * @param student to be added
     * @return student from db
     */
    @PostMapping("/students")
    public ResponseEntity addStudent(@RequestBody Student student) {
        logger.debug("Request contains body: " + student);
        Student db_student = studentRepository.save(student);
        logger.debug("Response contains body: " + db_student);
        return ResponseEntity.ok(db_student);
    }

    /**
     * API to modify existing student.
     *
     * @param studentId as id to be modified
     * @param student   as data to be modified
     * @return student from db
     */
    @PutMapping("/students/{studentId}")
    public ResponseEntity modifyStudent(@PathVariable("studentId") Long studentId,
                                        @RequestBody Student student) {
        logger.debug("Request contains path studentId: " + studentId);
        logger.debug("Request contains body: " + student);

        Optional<Student> db_student = studentRepository.findById(studentId);
        if (db_student.isPresent()) {
            student.setId(studentId);
            student = studentRepository.save(student);
            logger.debug("Response contains body: " + student);
            return ResponseEntity.ok(student);
        } else {
            logger.debug("Given student id not found in database");
            throw new BadRequestException("Student id does not exists");
        }
    }

    /**
     * API to delete existing student.
     *
     * @param studentId to be deleted
     * @return deleted studentId
     */
    @DeleteMapping("/students/{studentId}")
    public ResponseEntity deleteStudent(@PathVariable("studentId") Long studentId) {
        logger.debug("Request contains path studentId: " + studentId);
        Optional<Student> db_student = studentRepository.findById(studentId);
        if (db_student.isPresent()) {
            Student student = db_student.get();
            studentRepository.delete(student);
            logger.debug("Response contains body: " + studentId);
            return ResponseEntity.ok(studentId);
        } else {
            logger.debug("Given student id not found in database");
            throw new BadRequestException("Student id does not exists");
        }
    }

    /**
     * API to get a student by student id.
     *
     * @param studentId of student
     * @return student from db
     */
    @GetMapping("/students/{studentId}")
    public ResponseEntity getStudent(@PathVariable("studentId") Long studentId) {
        logger.debug("Request contains path studentId: " + studentId);
        Optional<Student> db_student = studentRepository.findById(studentId);
        if (db_student.isPresent()) {
            logger.debug("Response contains body: " + db_student.get());
            return ResponseEntity.ok(db_student.get());
        } else {
            logger.debug("Given student id does not exist in database");
            throw new BadRequestException("Student id does not exists");
        }
    }

    /**
     * Get current enrollments of a student.
     *
     * @param studentId as id enrolled
     * @return enrollments for the student
     */
    @GetMapping("/students/{studentId}/enrollments")
    public ResponseEntity enrollmentsOfStudentId(@PathVariable("studentId") Long studentId) {
        logger.debug("Request contains path studentId: " + studentId);
        List<Enrollment> enrollments = enrollmentRepository.findAllByStudent(Student.builder().id(studentId).build());
        logger.debug("Response contains body: " + enrollments);
        return ResponseEntity.ok(enrollments);
    }

    /**
     * Get student enrolled courses.
     *
     * @param studentId as filter
     * @return courses enrolled for the studentId
     */
    @GetMapping("/students/{studentId}/classes")
    public ResponseEntity coursesOfStudentId(@PathVariable("studentId") Long studentId) {
        logger.debug("Request contains path studentId: " + studentId);
        List<Enrollment> enrollments = enrollmentRepository.findAllByStudent(Student.builder().id(studentId).build());
        List<Course> courses = StreamSupport.stream(enrollments.spliterator(), true)
                .map(enrollment -> courseRepository.findByName(enrollment.getCourse().getName()).orElse(null))
                .collect(Collectors.toList());
        logger.debug("Response contains body: " + courses);
        return ResponseEntity.ok(courses);
    }

    /**
     * Get student enrolled courses in a semester.
     *
     * @param studentId  as filter
     * @param semesterId as filter
     * @return courses for the studentId in a semesterId
     */
    @GetMapping("/students/{studentId}/semesters/{semesterId}/classes")
    public ResponseEntity classesOfStudentIdInSemesterId(@PathVariable("studentId") Long studentId,
                                                         @PathVariable("semesterId") Long semesterId) {
        logger.debug("Request contains path studentId: " + studentId);
        logger.debug("Request contains path semesterId: " + semesterId);
        List<Enrollment> enrollments = enrollmentRepository.findAllByStudentOrSemester(Student.builder().id(studentId).build(),
                Semester.builder().id(semesterId).build());
        List<Course> courses = enrollments.stream()
                .map(enrollment -> courseRepository.findById(enrollment.getCourse().getId()).orElse(null))
                .collect(Collectors.toList());
        logger.debug("Response contains body: " + courses);
        return ResponseEntity.ok(courses);
    }

    /**
     * API to list students with filter.
     *
     * @param id          as optional filter
     * @param firstName   as optional filter
     * @param lastName    as optional filter
     * @param nationality as optional filter
     * @return students from db
     */
    @GetMapping("/students")
    public ResponseEntity fetchStudents(@RequestParam(value = "id") Optional<Long> id,
                                        @RequestParam(value = "first_name") Optional<String> firstName,
                                        @RequestParam(value = "last_name") Optional<String> lastName,
                                        @RequestParam(value = "nationality") Optional<String> nationality) {
        logger.debug("Request contains param id: " + id);
        logger.debug("Request contains param first_name: " + firstName);
        logger.debug("Request contains param last_name: " + lastName);
        logger.debug("Request contains param nationality: " + nationality);

        List<Student> students;

        if (id.isPresent() || firstName.isPresent() || lastName.isPresent() || nationality.isPresent()) {
            logger.debug("Fetching with params");
            students = studentRepository.findAllByIdOrFirstNameIgnoreCaseOrLastNameIgnoreCaseOrNationalityIgnoreCase(id, firstName, lastName, nationality);
        } else {
            logger.debug("Fetching without params");
            students = studentRepository.findAll();
        }

        logger.debug("Response contains body: " + students);
        return ResponseEntity.ok(students);

    }


}

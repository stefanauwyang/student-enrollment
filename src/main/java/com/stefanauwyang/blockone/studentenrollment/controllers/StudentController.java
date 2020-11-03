package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.db.models.Enrollment;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import com.stefanauwyang.blockone.studentenrollment.db.repos.CourseRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.EnrollmentRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    /**
     * API to add new student.
     *
     * @param student to be added
     * @return student from db
     */
    @PostMapping("/students")
    public ResponseEntity addStudent(@RequestBody Student student) {
        Student db_student = studentRepository.save(student);
        return ResponseEntity.ok(db_student);
    }

    /**
     * API to modify existing student.
     *
     * @param student to be modified
     * @return student from db
     */
    @PutMapping("/students/{studentId}")
    public ResponseEntity modifyStudent(@PathVariable("studentId") Long studentId,
                                        @RequestBody Student student) {
        Optional<Student> db_student = studentRepository.findById(studentId);
        if (db_student.isPresent()) {
            student.setId(studentId);
            student = studentRepository.save(student);
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * API to delete existing student.
     *
     * @param studentId to be deleted
     * @return student deleted from db
     */
    @DeleteMapping("/students/{studentId}")
    public ResponseEntity deleteStudent(@PathVariable("studentId") Long studentId) {
        Optional<Student> db_student = studentRepository.findById(studentId);
        if (db_student.isPresent()) {
            Student student = db_student.get();
            studentRepository.delete(student);
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
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
        Optional<Student> db_student = studentRepository.findById(studentId);
        if (db_student.isPresent()) {
            return ResponseEntity.ok(db_student.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get student current enrollments.
     *
     * @param studentId
     * @return enrollments for the student
     */
    @GetMapping("/students/{studentId}/enrollments")
    public ResponseEntity enrollments(@PathVariable("studentId") Long studentId) {
        Iterable<Enrollment> enrollments = enrollmentRepository.findByStudent(Student.builder().id(studentId).build());
        return ResponseEntity.ok(enrollments);
    }

    /**
     * API to list students with filter.
     *
     * @param firstName as optional filter
     * @param lastName as optional filter
     * @param nationality as optional filter
     * @return students from db
     */
    @GetMapping("/students")
    public ResponseEntity fetchStudents(@RequestParam(value = "first_name") Optional<String> firstName,
                                        @RequestParam(value = "last_name") Optional<String> lastName,
                                        @RequestParam(value = "nationality") Optional<String> nationality) {
        if (firstName.isPresent()
                || lastName.isPresent()
                || nationality.isPresent()) {
            Iterable<Student> students = studentRepository.findAllByFirstNameOrLastNameOrNationality(firstName, lastName, nationality);
            return ResponseEntity.ok(students);
        } else {
            Iterable<Student> students = studentRepository.findAll();
            return ResponseEntity.ok(students);
        }
    }


}

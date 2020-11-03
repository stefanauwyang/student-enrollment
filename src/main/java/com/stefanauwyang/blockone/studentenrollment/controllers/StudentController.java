package com.stefanauwyang.blockone.studentenrollment.controllers;

import com.stefanauwyang.blockone.studentenrollment.db.models.Clazz;
import com.stefanauwyang.blockone.studentenrollment.db.models.Enrollment;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import com.stefanauwyang.blockone.studentenrollment.db.repos.EnrollmentRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.StudentRepository;
import com.stefanauwyang.blockone.studentenrollment.db.repos.ClassRepository;
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
    private ClassRepository classRepository;

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
    @PutMapping("/students")
    public ResponseEntity modifyStudent(@RequestBody Student student) {
        Optional<Student> db_student = studentRepository.findById(student.getId());
        if (db_student.isPresent()) {
            student.setId(db_student.get().getId());
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
     * Get current student enrollments.
     *
     * @return
     */
    @GetMapping("/students/{studentId}/enrollments")
    public ResponseEntity enrollments(@PathVariable("studentId") Long studentId) {
        Iterable<Enrollment> enrollments = enrollmentRepository.findByStudent(Student.builder().id(studentId).build());
        return ResponseEntity.ok(enrollments);
    }

    /**
     * API to list students with filter.
     *
     * @param className
     * @param studentId
     * @return
     */
    @GetMapping("/students")
    public ResponseEntity fetchStudents(@RequestParam(value = "class", required = false) String className,
                                        @RequestParam(value = "id", required = false) Long studentId) {

        if (className != null && !className.isEmpty()) {
            Optional<Clazz> clazz = classRepository.findById(className);
            if (clazz.isPresent()) {
                Iterable<Enrollment> enrollments = enrollmentRepository.findByClazz(clazz.get());
                return ResponseEntity.ok(enrollments);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else if (studentId != null) {
            Iterable<Enrollment> enrollments = enrollmentRepository.findByStudent(Student.builder().id(studentId).build());
            return ResponseEntity.ok(enrollments);
        } else {
            Iterable<Student> students = studentRepository.findAll();
            return ResponseEntity.ok(students);
        }

    }


}

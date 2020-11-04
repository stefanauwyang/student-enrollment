package com.stefanauwyang.blockone.studentenrollment.db.repos;

import com.stefanauwyang.blockone.studentenrollment.db.models.Course;
import com.stefanauwyang.blockone.studentenrollment.db.models.Enrollment;
import com.stefanauwyang.blockone.studentenrollment.db.models.Semester;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import com.stefanauwyang.blockone.studentenrollment.db.models.pk.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {

    List<Enrollment> findAllByCourse(Course course);

    List<Enrollment> findAllByStudent(Student student);

    List<Enrollment> findAllByStudentOrSemester(Student student, Semester semester);

    Optional<Enrollment> findByStudentAndSemesterAndCourse(Student student, Semester semester, Course course);

    Optional<Enrollment> findByCourseAndStudent(Course course, Student student);

}

package com.stefanauwyang.blockone.studentenrollment.db.repos;

import com.stefanauwyang.blockone.studentenrollment.db.models.Course;
import com.stefanauwyang.blockone.studentenrollment.db.models.Enrollment;
import com.stefanauwyang.blockone.studentenrollment.db.models.Semester;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Database repository access for enrollment table.
 */
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findAllByCourse(Course course);

    List<Enrollment> findAllByStudent(Student student);

    List<Enrollment> findAllByStudentOrSemester(Student student, Semester semester);

    Optional<Enrollment> findByStudentAndSemesterAndCourse(Student student, Semester semester, Course course);

    List<Enrollment> findAllByStudentIdOrSemesterIdOrCourseId(Optional<Long> studentId,
                                                              Optional<Long> semesterId,
                                                              Optional<Long> courseId);

    List<Enrollment> findAllByStudentIdAndSemesterId(Long studentId, Long semesterId);

}

package com.stefanauwyang.blockone.studentenrollment.db.repos;

import com.stefanauwyang.blockone.studentenrollment.db.models.Course;
import com.stefanauwyang.blockone.studentenrollment.db.models.Enrollment;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import com.stefanauwyang.blockone.studentenrollment.db.models.pk.EnrollmentId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends CrudRepository<Enrollment, EnrollmentId> {

    Iterable<Enrollment> findByCourse(Course course);
    Iterable<Enrollment> findByStudent(Student student);

}

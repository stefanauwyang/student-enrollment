package com.stefanauwyang.blockone.studentenrollment.repos;

import com.stefanauwyang.blockone.studentenrollment.model.Enrollment;
import com.stefanauwyang.blockone.studentenrollment.model.EnrollmentId;
import com.stefanauwyang.blockone.studentenrollment.model.Student;
import com.stefanauwyang.blockone.studentenrollment.model.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends CrudRepository<Enrollment, EnrollmentId> {

    Iterable<Enrollment> findBySubject(Subject subject);
    Iterable<Enrollment> findByStudent(Student student);

}

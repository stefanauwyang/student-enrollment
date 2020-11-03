package com.stefanauwyang.blockone.studentenrollment.db.repos;

import com.stefanauwyang.blockone.studentenrollment.db.models.Enrollment;
import com.stefanauwyang.blockone.studentenrollment.db.models.EnrollmentId;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import com.stefanauwyang.blockone.studentenrollment.db.models.Clazz;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends CrudRepository<Enrollment, EnrollmentId> {

    Iterable<Enrollment> findByClazz(Clazz clazz);
    Iterable<Enrollment> findByStudent(Student student);

}

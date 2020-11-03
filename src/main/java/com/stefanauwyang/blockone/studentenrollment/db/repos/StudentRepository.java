package com.stefanauwyang.blockone.studentenrollment.db.repos;

import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    Student findByFirstName(String firstName);

}

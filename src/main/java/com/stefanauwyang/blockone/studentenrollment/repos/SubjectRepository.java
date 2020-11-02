package com.stefanauwyang.blockone.studentenrollment.repos;

import com.stefanauwyang.blockone.studentenrollment.model.Student;
import com.stefanauwyang.blockone.studentenrollment.model.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, Long> {

    Subject findByClassName(String className);

}

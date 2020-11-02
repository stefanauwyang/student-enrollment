package com.stefanauwyang.blockone.studentenrollment.repos;

import com.stefanauwyang.blockone.studentenrollment.model.Semester;
import com.stefanauwyang.blockone.studentenrollment.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends CrudRepository<Semester, Long> {

}

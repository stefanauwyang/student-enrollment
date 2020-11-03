package com.stefanauwyang.blockone.studentenrollment.db.repos;

import com.stefanauwyang.blockone.studentenrollment.db.models.Semester;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends CrudRepository<Semester, Long> {

}

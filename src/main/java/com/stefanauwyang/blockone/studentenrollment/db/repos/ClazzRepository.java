package com.stefanauwyang.blockone.studentenrollment.db.repos;

import com.stefanauwyang.blockone.studentenrollment.db.models.Clazz;
import com.stefanauwyang.blockone.studentenrollment.db.models.Semester;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClazzRepository extends CrudRepository<Clazz, String> {

    Optional<Clazz> findByName(String name);

    Iterable<Clazz> findBySemester(Optional<Semester> semester);

}

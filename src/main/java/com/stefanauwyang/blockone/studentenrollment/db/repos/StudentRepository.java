package com.stefanauwyang.blockone.studentenrollment.db.repos;

import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    Iterable<Student> findByFirstName(String firstName);

    Iterable<Student> findByLastName(String lastName);

    Iterable<Student> findAllByFirstNameOrLastNameOrNationality(Optional<String> firstName,
                                                                Optional<String> lastName,
                                                                Optional<String> nationality);

}

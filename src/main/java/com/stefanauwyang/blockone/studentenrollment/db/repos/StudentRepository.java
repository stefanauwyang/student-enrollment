package com.stefanauwyang.blockone.studentenrollment.db.repos;

import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Database repository access for student table.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByFirstName(String firstName);

    List<Student> findByLastName(String lastName);

    List<Student> findAllByIdOrFirstNameIgnoreCaseOrLastNameIgnoreCaseOrNationalityIgnoreCase(Optional<Long> id,
                                                                Optional<String> firstName,
                                                                Optional<String> lastName,
                                                                Optional<String> nationality);

}

package com.stefanauwyang.blockone.studentenrollment.db.repos;

import com.stefanauwyang.blockone.studentenrollment.db.models.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Datbase repository access to semester table.
 */
@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {

    List<Semester> findAllByIdOrNameOrStatus(Optional<Long> id, Optional<String> name, Optional<String> status);

}

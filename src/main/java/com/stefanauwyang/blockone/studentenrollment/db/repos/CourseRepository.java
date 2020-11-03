package com.stefanauwyang.blockone.studentenrollment.db.repos;

import com.stefanauwyang.blockone.studentenrollment.db.models.Course;
import com.stefanauwyang.blockone.studentenrollment.db.models.Semester;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends CrudRepository<Course, String> {

    Optional<Course> findByName(String name);

    Iterable<Course> findBySemester(Optional<Semester> semester);

}

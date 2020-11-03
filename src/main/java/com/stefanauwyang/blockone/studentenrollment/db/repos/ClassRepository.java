package com.stefanauwyang.blockone.studentenrollment.db.repos;

import com.stefanauwyang.blockone.studentenrollment.db.models.Clazz;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends CrudRepository<Clazz, String> {

    Clazz findByName(String name);

}

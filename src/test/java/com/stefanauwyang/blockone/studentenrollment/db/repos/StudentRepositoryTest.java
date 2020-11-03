package com.stefanauwyang.blockone.studentenrollment.db.repos;

import com.stefanauwyang.blockone.studentenrollment.db.models.Clazz;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentRepositoryTest extends TestCase {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void testStudent() {
        Optional<Student> db_student = studentRepository.findById(1L);
        Student student = db_student.get();
        Set<Clazz> classes = student.getClasses();
        classes.stream().forEach(System.out::println);
    }

}
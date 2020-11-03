package com.stefanauwyang.blockone.studentenrollment;

import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentEnrollmentAppTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void studentsShouldReturnAllStudents() throws Exception {
        Student student = restTemplate.getForObject("http://localhost:" + port + "/students/1",
                Student.class);
        Assert.assertEquals("Student should have id: 1", new Long(1), student.getId());
        Assert.assertEquals("Michael", student.getFirstName());
    }

}
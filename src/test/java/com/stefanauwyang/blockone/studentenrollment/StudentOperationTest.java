package com.stefanauwyang.blockone.studentenrollment;

import com.stefanauwyang.blockone.studentenrollment.db.models.Enrollment;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import com.stefanauwyang.blockone.studentenrollment.utils.TestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentOperationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Student newlyCreatedStudent;

    @Before
    public void before() {
        // Populate student object to send to backend to create
        Student student = TestUtil.newStudent();

        // Sending student object to be created in backend
        newlyCreatedStudent = restTemplate.postForObject("http://localhost:" + port + "/students",
                student,
                Student.class);
    }

    /**
     * Students will be able to enroll themselves into classes before each term.
     *
     * @throws Exception
     */
    @Test
    public void studentWillBeAbleToEnrollThemselvesToClassesBeforeTerm() throws Exception {
        String url = "http://localhost:%d/enrollments/semester/%s/classes/%s/students/%s/enroll";
        String requestUrl = String.format(url, port, 1, 1, newlyCreatedStudent.getId());

        Enrollment enrollment = restTemplate.postForObject(requestUrl, null, Enrollment.class);

        System.out.println(enrollment);
    }

}
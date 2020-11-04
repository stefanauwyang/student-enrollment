package com.stefanauwyang.blockone.studentenrollment;

import com.stefanauwyang.blockone.studentenrollment.db.models.Course;
import com.stefanauwyang.blockone.studentenrollment.db.models.Enrollment;
import com.stefanauwyang.blockone.studentenrollment.db.models.Semester;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReqStudentOperationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Student student;
    private Semester semester;
    private Course course;

    @Before
    public void before() {

        // Populate student object to send to backend to create
        student = TestHelper.newStudent();

        // Sending student object to be created in backend
        student = restTemplate.postForObject("http://localhost:" + port + "/students",
                student,
                Student.class);

        // Populate semester object to send to backend to create
        semester = TestHelper.newSemester();

        // Sending semester object to be created in backend
        semester = restTemplate.postForObject("http://localhost:" + port + "/semesters", semester, Semester.class);

        // Populate class object to send to backend to create
        course = TestHelper.newCourse();

        // Sending semester object to be created in backend
        course = restTemplate.postForObject("http://localhost:" + port + "/classes", course, Course.class);

    }

    /**
     * Students will be able to enroll themselves into classes before each term.
     *
     * @throws Exception
     */
    @Test
    public void studentWillBeAbleToEnrollThemselvesToClassesBeforeTerm() throws Exception {
        String url = "http://localhost:%d/enrollments/semester/%s/classes/%s/students/%s/enroll";
        String requestUrl = String.format(url, port, semester.getId(), course.getId(), student.getId());

        Enrollment enrollment = restTemplate.postForObject(
                requestUrl,
                null,
                Enrollment.class);

        System.out.println(enrollment);

        assertNotNull("Enrollment should have id", enrollment.getId());

        assertEquals("Semester id should persisted correctly", semester.getId(), enrollment.getSemester().getId());
    }

}
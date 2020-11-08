package com.stefanauwyang.blockone.studentenrollment;

import com.stefanauwyang.blockone.studentenrollment.db.models.Course;
import com.stefanauwyang.blockone.studentenrollment.db.models.Enrollment;
import com.stefanauwyang.blockone.studentenrollment.db.models.Semester;
import com.stefanauwyang.blockone.studentenrollment.db.models.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReqStudentOperationTest {

    private static final Logger logger = LoggerFactory.getLogger(ReqStudentOperationTest.class);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Student student;
    private Semester closedSemester;
    private Semester openSemester;
    private Course course;

    private Enrollment enrollment;

    @Before
    public void before() {

        // Populate student object to send to backend to create
        student = TestHelper.newStudent();

        // Sending student object to be created in backend
        student = restTemplate.postForObject("http://localhost:" + port + "/students",
                student,
                Student.class);

        // Populate closed semester object to send to backend to create
        closedSemester = TestHelper.newClosedSemester();

        // Sending closed semester object to be created in backend
        closedSemester = restTemplate.postForObject("http://localhost:" + port + "/semesters", closedSemester, Semester.class);

        // Populate open semester object to send to backend to create
        openSemester = TestHelper.newOpenSemester();

        // Sending open semester object to be created in backend
        openSemester = restTemplate.postForObject("http://localhost:" + port + "/semesters", openSemester, Semester.class);

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

        String url = "http://localhost:%d/enrollments";
        String requestUrl = String.format(url, port);

        enrollment = Enrollment.builder()
                .course(course)
                .semester(openSemester)
                .student(student)
                .build();

        enrollment = restTemplate.postForObject(
                requestUrl,
                enrollment,
                Enrollment.class);

        assertNotNull("Enrollment should have id", enrollment.getId());

        assertEquals("Semester id should persisted correctly", openSemester.getId(), enrollment.getSemester().getId());

        assertEquals("Class id should persisted correctly", course.getId(), enrollment.getCourse().getId());

        assertEquals("Student id should persisted correctly", student.getId(), enrollment.getStudent().getId());

        restTemplate.delete("http://localhost:" + port + "/enrollments/" + enrollment.getId());

    }

    /**
     * Student will not be able to enroll to semester which has been closed.
     *
     * @throws Exception
     */
    @Test
    public void studentWillNotBeAbleToEnrollClosedSemester() throws Exception {

        String url = "http://localhost:%d/enrollments/semesters/%s/classes/%s/students/%s/enroll";
        String requestUrl = String.format(url, port, closedSemester.getId(), course.getId(), student.getId());

        assertEquals("Enrolling closed semester", Semester.CLOSED, closedSemester.getStatus());

        enrollment = restTemplate.postForObject(
                requestUrl,
                null,
                Enrollment.class);

        assertNull("Enrollment id should be null when enrolling closed semester", enrollment.getId());

    }

    @Test
    public void studentWillNotBeAbleToEnrollMoreThan20Credits() throws Exception {

        assertEquals("Enrolling closed semester", Semester.CLOSED, closedSemester.getStatus());

        // Start with class id 1 until credits maxed out
        int n = 1;

        // New student not yet registered to any credits, existing credits is 0
        Integer existingCredits = 0;
        while (true) {

            // Get the class to know how much credit it has
            Course class_n = restTemplate.getForObject("http://localhost:" + port + "/classes/" + n, Course.class);
            int expectedCreditsAfterEnroll = existingCredits + class_n.getCredit();

            enrollment = tryEnrollClass(class_n);
            existingCredits = expectedCreditsAfterEnroll;

            if (expectedCreditsAfterEnroll <= 20) {
                assertNotNull("Should be able to enroll because credits will be 20 or less", enrollment.getId());
            } else {
                assertNull("Should not be able to enroll because credits will be more than 20", enrollment.getId());
                break;
            }

            n++;
        }

    }

    private Enrollment tryEnrollClass(Course class_n) {
        logger.debug("Trying to register student id " + student.getId() + " to class id " + class_n.getId() + " on semester id " + openSemester.getId());

        String url = "http://localhost:%d/enrollments/semesters/%s/classes/%s/students/%s/enroll";
        String requestUrl = String.format(url, port, openSemester.getId(), class_n.getId(), student.getId());

        // After this call, total credits will be less or equals 20
        Enrollment enrollment = restTemplate.postForObject(
                requestUrl,
                null,
                Enrollment.class);

        logger.debug("After trying to register, this is the enrollment response: " + enrollment);

        return enrollment;
    }

    @After
    public void after() {

        restTemplate.delete("http://localhost:" + port + "/students/" + student.getId());
        restTemplate.delete("http://localhost:" + port + "/semesters/" + closedSemester.getId());
        restTemplate.delete("http://localhost:" + port + "/semesters/" + openSemester.getId());
        restTemplate.delete("http://localhost:" + port + "/classes/" + course.getId());

    }

}
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
public class ReqAdminOperationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * School administrators can create and modify student records but never delete them.
     * To allow API remain generic, to prevent deletion should be done in front end.
     *
     * @throws Exception
     */
    @Test
    public void schoolAdministratorCanCreateAndModifyStudentRecord() throws Exception {

        // Populate student object to send to backend to create
        Student student = TestHelper.newStudent();

        // Sending student object to be created in backend
        Student newlyCreatedStudent = restTemplate.postForObject("http://localhost:" + port + "/students",
                student,
                Student.class);

        // Capture the generated studentId
        Long studentId = newlyCreatedStudent.getId();

        Assert.assertNotNull("Student should have ID", studentId);

        Assert.assertEquals("Student firstName should same with created user firstName",
                TestHelper.NEW_STUDENT_FIRST_NAME, newlyCreatedStudent.getFirstName());

        Assert.assertEquals("Student lastName should same with created user lastName",
                TestHelper.NEW_STUDENT_LAST_NAME, newlyCreatedStudent.getLastName());

        Assert.assertEquals("Student nationality should same with created user nationality",
                TestHelper.NEW_STUDENT_NATIONALITY, newlyCreatedStudent.getNationality());

        // Modify newly created user firstName
        TestHelper.modifyStudentFirstName(newlyCreatedStudent);

        // Trigger modify the student first name
        restTemplate.put("http://localhost:" + port + "/students/" + studentId, newlyCreatedStudent);

        // Get the student from backend
        Student modifiedStudent = restTemplate.getForObject("http://localhost:" + port + "/students/" + studentId,
                Student.class);

        Assert.assertEquals("New firstName should be persisted",
                TestHelper.NEW_STUDENT_FIRST_NAME_MODIFY, modifiedStudent.getFirstName());

    }

}
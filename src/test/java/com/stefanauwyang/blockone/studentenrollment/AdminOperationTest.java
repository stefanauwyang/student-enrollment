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
public class AdminOperationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void schoolAdministratorCanCreateAndModifyStudentRecord() throws Exception {

        // First name to compare after creation
        String firstName = "FirstName";

        // Populate student object to send to backend to create
        Student student = Student.builder()
                .firstName(firstName)
                .lastName("LastName")
                .nationality("Nationality")
                .build();

        // Sending student object to be created in backend
        Student newlyCreatedStudent = restTemplate.postForObject("http://localhost:" + port + "/students",
                student,
                Student.class);

        // Capture the generated studentId
        Long studentId = newlyCreatedStudent.getId();

        Assert.assertNotNull("Student should have ID", studentId);
        Assert.assertEquals("Student firstName should same with created user firstName",
                firstName,
                newlyCreatedStudent.getFirstName());

        // Note the new firstName and set it for modification
        String newFirstName = "NewFirstName";
        newlyCreatedStudent.setFirstName(newFirstName);

        // Trigger modify the student first name
        restTemplate.put("http://localhost:" + port + "/students/" + studentId, newlyCreatedStudent);

        // Get the student from backend
        Student studentFromBackend = restTemplate.getForObject("http://localhost:" + port + "/students/" + studentId,
                Student.class);

        Assert.assertEquals("New first name should be persisted", newFirstName, studentFromBackend.getFirstName());

    }

}
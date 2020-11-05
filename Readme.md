# Student Enrollment REST API
REST API class enrollment system built using Javba, Spring Boot, H2 in memory data structure.

## Development requirement
1. JDK 1.8 or above
2. IntellJ/Eclipse editor with Lombok plug-in
![](misc/Dev-Env-Setup-Lombok.png)

## Important guidelines
1. This application developed using lombok to auto generate setter getter. You may need lombok plugin in your editor.
2. To build and run application: mvn spring-boot:run
3. To generate documentation run: mvn javadoc:javadoc
4. Test cases created to cover code align with requirement

## Database Structure
![](misc/entities.png)

## Requirement
1. The users of the system will consist of both school administrators and students.
2. School administrators will create student identities
3. Students will be able to enroll themselves into classes before each semester.

## Constraints:
1. School administrators can create and modify student records but never delete them.
API designed for generic use. Hence, access control and design decision not to allow deletion controlled in Front End.

### Create student
POST /students
```json
{
    "id": 1,
    "firstName": "Mike",
    "lastName": "Wong",
    "nationality": "Myanmar"
}
```

### Modify student
PUT /students
```json
{
    "id": 1,
    "firstName": "Michael",
    "lastName": "Wong",
    "nationality": "Malaysia"
}
```

APIs:
- API to add new students or modify them -> POST: /students
- API to create a new semester -> POST: /semesters
- API to enroll a student into a class for a particular semester -> POST: /students/1/class/1
- API to get the list of classes for a particular student for a semester, or the fully history of classes enrolled.
- API to get the list of students enrolled in a class for a particular semester.
- API to drop a student from a class.

Logic:
- When enrolled, it will calculate max 20 credits per semster.
- When enrolled, if the credits min 10, full_time flag become false.
- School administrator and Student will log in through front end app before allowed
  to access APIs.
- School administration have access to create student identities, but not allowed to call delete.
- Students will be able to enroll themselves into classes before each term

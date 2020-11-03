Development:
- This application developed using lombok to auto generate setter getter.
  You may need lombok plugin in your editor.
- To build and run application: mvn spring-boot:run
- To generate documentation run: mvn javadoc:javadoc

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
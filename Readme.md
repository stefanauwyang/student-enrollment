Development:
- This application developed using lombok to auto generate setter getter.
  You may need lombok plugin in your editor.

APIs:
- API to add new students or modify them -> POST: /students
- API to create a new semester -> POST: /semesters
- API to enroll a student into a class for a particular semester -> POST: /students/1/class/1
  
- API to get the list of classes for a particular student for a semester, or the fully history of classes enrolled.
- API to get the list of students enrolled in a class for a particular semester.
- API to drop a student from a class.

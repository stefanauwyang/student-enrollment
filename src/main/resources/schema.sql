--DROP TABLE IF EXISTS student;
--
--CREATE TABLE student (
--  id INT AUTO_INCREMENT PRIMARY KEY,
--  first_name VARCHAR(250) NOT NULL,
--  last_name VARCHAR(250) DEFAULT NULL,
--  nationality VARCHAR(100) DEFAULT NULL,
--  full_time BOOLEAN DEFAULT FALSE
--);

-------------------------------------------------------------------

--DROP TABLE IF EXISTS semester;
--
--CREATE TABLE semester (
--  id INT AUTO_INCREMENT PRIMARY KEY,
--  name VARCHAR(250) NOT NULL UNIQUE,
--  status VARCHAR(15) DEFAULT 'OPEN'
--);

-------------------------------------------------------------------

--DROP TABLE IF EXISTS class;
--
--CREATE TABLE class (
--  id INT PRIMARY KEY,
--  name VARCHAR(250) NOT NULL,
--  credit INT,
--  semester_id INT
--);

-------------------------------------------------------------------

--DROP TABLE IF EXISTS enrollment;
--
--CREATE TABLE enrollment (
--  student_id INT NOT NULL,
--  semester_id INT NOT NULL,
--  class_id INT NOT NULL
--);

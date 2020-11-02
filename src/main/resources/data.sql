DROP TABLE IF EXISTS student;

CREATE TABLE student (
  id INT PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) DEFAULT NULL,
  nationality VARCHAR(100) DEFAULT NULL
);

INSERT INTO student (id, first_name, last_name, nationality) VALUES
  (1, 'Michael', 'Wong', 'Singapore'),
  (2, 'Sylvia', 'Lim', 'Singapore'),
  (3, 'Anastasia', 'Potter', 'Indonesia'),
  (4, 'James', 'Bond', 'Indonesia');

DROP TABLE IF EXISTS semester;

CREATE TABLE semester (
  id INT PRIMARY KEY,
  name VARCHAR(250) NOT NULL
);

INSERT INTO semester (id, name) VALUES
  (1, '2019-1'),
  (2, '2019-2'),
  (3, '2020-1');

DROP TABLE IF EXISTS subject;

CREATE TABLE subject (
  id INT PRIMARY KEY,
  class_name VARCHAR(250) NOT NULL,
  credit INT
);

INSERT INTO subject (id, class_name, credit) VALUES
  (1, '3 A', 2),
  (2, '3 C', 4),
  (3, '2 B', 3);

DROP TABLE IF EXISTS enrollment;

CREATE TABLE enrollment (
  semester_id INT NOT NULL,
  subject_id INT NOT NULL,
  student_id INT NOT NULL
);

INSERT INTO enrollment (semester_id, subject_id, student_id) VALUES
  (1, 1, 1),
  (1, 2, 2),
  (2, 2, 3),
  (2, 3, 3);
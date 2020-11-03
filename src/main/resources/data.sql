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

-------------------------------------------------------------------

DROP TABLE IF EXISTS semester;

CREATE TABLE semester (
  id INT PRIMARY KEY,
  name VARCHAR(250) NOT NULL
);

INSERT INTO semester (id, name) VALUES
  (1, '2019-1'),
  (2, '2019-2'),
  (3, '2020-1');

-------------------------------------------------------------------

DROP TABLE IF EXISTS class;

CREATE TABLE class (
  name VARCHAR(250) PRIMARY KEY,
  semester_id INT,
  credit INT
);

INSERT INTO class (name, semester_id, credit) VALUES
  ('1 A', 1, 1),
  ('1 B', 1, 2),
  ('1 C', 1, 3),
  ('1 D', 1, 4),
  ('1 E', 1, 1),
  ('1 F', 1, 2),
  ('1 G', 1, 3),
  ('1 H', 1, 4),
  ('1 I', 1, 1),
  ('1 J', 1, 2),
  ('2 A', 2, 3),
  ('2 B', 2, 4),
  ('2 C', 2, 1),
  ('2 D', 2, 2),
  ('2 E', 2, 3),
  ('2 F', 2, 4),
  ('2 G', 2, 1),
  ('2 H', 2, 2),
  ('2 I', 2, 3),
  ('2 J', 2, 4);

-------------------------------------------------------------------

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
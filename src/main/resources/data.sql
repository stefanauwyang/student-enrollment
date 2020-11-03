DROP TABLE IF EXISTS student;

CREATE TABLE student (
  id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) DEFAULT NULL,
  nationality VARCHAR(100) DEFAULT NULL,
  full_time BOOLEAN DEFAULT FALSE
);

INSERT INTO student (id, first_name, last_name, nationality) VALUES
  (1, 'Michael', 'Wong', 'Singapore'),
  (2, 'Sylvia', 'Lim', 'Singapore'),
  (3, 'Anastasia', 'Potter', 'Indonesia'),
  (4, 'James', 'Bond', 'Indonesia');

-------------------------------------------------------------------

DROP TABLE IF EXISTS semester;

CREATE TABLE semester (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) NOT NULL UNIQUE
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
  class_id VARCHAR(250) NOT NULL,
  student_id INT NOT NULL,
  enroll_dt DATETIME DEFAULT NULL,
  approved BOOLEAN DEFAULT FALSE
);

INSERT INTO enrollment (class_id, enroll_dt, student_id) VALUES
  ('1 A', '2020-01-01T01:20:03', 1),
  ('2 B', '2020-01-02T23:02:31', 2),
  ('2 F', '2020-01-03T09:05:53', 3),
  ('1 I', '2020-01-04T07:32:04', 3);
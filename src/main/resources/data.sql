--DROP TABLE IF EXISTS student;
--
--CREATE TABLE student (
--  id INT AUTO_INCREMENT PRIMARY KEY,
--  first_name VARCHAR(250) NOT NULL,
--  last_name VARCHAR(250) DEFAULT NULL,
--  nationality VARCHAR(100) DEFAULT NULL,
--  full_time BOOLEAN DEFAULT FALSE
--);

INSERT INTO student (id, first_name, last_name, nationality) VALUES
  (1, 'Michael', 'Wong', 'Malaysia'),
  (2, 'Sylvia', 'Lim', 'Singapore'),
  (3, 'Anastasia', 'Potter', 'Indonesia'),
  (4, 'James', 'Lim', 'U.S.A');

-------------------------------------------------------------------

--DROP TABLE IF EXISTS semester;
--
--CREATE TABLE semester (
--  id INT AUTO_INCREMENT PRIMARY KEY,
--  name VARCHAR(250) NOT NULL UNIQUE,
--  status VARCHAR(15) DEFAULT 'OPEN'
--);

INSERT INTO semester (id, name, status) VALUES
  (1, '2017-1', 'CLOSED'),
  (2, '2017-2', 'CLOSED'),
  (3, '2018-1', 'CLOSED'),
  (4, '2018-2', 'CLOSED'),
  (5, '2019-1', 'CLOSED'),
  (6, '2019-2', 'CLOSED'),
  (7, '2020-1', 'CLOSED'),
  (8, '2020-2', 'ACTIVE'),
  (9, '2021-1', 'OPEN'),
  (10, '2021-2', 'OPEN');

-------------------------------------------------------------------

--DROP TABLE IF EXISTS class;
--
--CREATE TABLE class (
--  id INT PRIMARY KEY,
--  name VARCHAR(250) NOT NULL,
--  credit INT,
--  semester_id INT
--);

INSERT INTO class (id, name, credit) VALUES
  (1, 'Introduction to Computational Thinking', 3),
  (2, 'Digital Logic', 3),
  (3, 'Computer Organisation and Architecture', 3),
  (4, 'Data Structure', 3),
  (5, 'Engineering Mathematics 1', 3),
  (6, 'Engineering Mathematics 2', 3),
  (7, 'Physics for Computing', 2),
  (8, 'Algorithms', 3),
  (9, 'Objet Oriented Design & Programming', 3),
  (10, 'Computer Graphics & Visualization', 3),
  (11, 'Human‐Computer Interaction', 3),
  (12, 'Operating Systems', 3),
  (13, 'Software Engineering', 3),
  (14, 'Introduction to Databases', 3),
  (15, 'Introduction to Data Science', 3),
  (16, 'Advanced Computer Architecture', 3),
  (17, 'Advanced Software Engineering', 3),
  (18, 'Artificial Intelligence ‐ Problem Solving and Knowledge and Reasoning', 3),
  (19, 'Net Centric Computing', 3),
  (20, 'Compiler Techniques', 3),
  (21, 'Virtual and Augmented Reality', 3),
  (22, 'Computer Vision', 3),
  (23, 'Distributed Systems', 3),
  (24, 'Simulation and Modelling', 3),
  (25, 'Advanced Topics in Algorithms', 3),
  (26, 'Pervasive Networks', 3),
  (27, 'Personal Mobile Networks', 3),
  (28, 'Advanced Computer Networks', 3),
  (29, 'Cryptography and Network Security', 3),
  (30, 'Database System Principles', 3),
  (31, 'Data Analytics and Mining', 3),
  (32, 'Information Retrieval', 3),
  (33, 'Machine Learning', 3),
  (34, 'Neural networks and deep learning', 3),
  (35, 'Natural Language Processing', 3),
  (36, 'Intelligent Agents', 3),
  (37, 'Cyber Physical System Security', 3),
  (38, 'Time‐Critical Computing', 3),
  (39, 'Computer Security (System Security)', 3),
  (40, 'Security Management', 3),
  (41, 'Digital Forensics', 3),
  (42, 'Software Security', 3),
  (43, 'Application Security', 3),
  (44, 'Network Science', 3),
  (45, 'Data Science for Business', 3);

-------------------------------------------------------------------

--DROP TABLE IF EXISTS enrollment;
--
--CREATE TABLE enrollment (
--  student_id INT NOT NULL,
--  semester_id INT NOT NULL,
--  class_id INT NOT NULL
--);

INSERT INTO enrollment (student_id, semester_id, class_id) VALUES
  (1, 1, 1),
  (1, 1, 2),
  (1, 1, 3),
  (1, 1, 4),
  (1, 1, 5),
  (1, 1, 6),
  (1, 1, 7),
  (1, 2, 8),
  (1, 2, 9),
  (1, 2, 10),
  (1, 2, 11),
  (1, 2, 12),
  (1, 2, 13),
  (1, 2, 14),
  (1, 2, 15),
  (1, 3, 16),
  (1, 3, 17),
  (1, 3, 18),
  (1, 3, 19),
  (1, 3, 20),
  (1, 4, 21),
  (1, 4, 22),
  (1, 4, 23),
  (1, 4, 24),
  (1, 4, 25),
  (1, 4, 26),
  (1, 4, 27),
  (1, 5, 28),
  (1, 5, 29),
  (1, 5, 30),
  (1, 5, 31),
  (1, 5, 32),
  (1, 5, 33),
  (1, 5, 34),
  (1, 6, 35),
  (1, 6, 36),
  (1, 6, 37),
  (1, 6, 38),
  (1, 6, 39),
  (1, 7, 40),
  (1, 7, 41),
  (1, 7, 42),
  (1, 7, 43),
  (1, 7, 44),
  (1, 7, 45),
  (3, 1, 1),
  (3, 1, 2),
  (3, 1, 3),
  (3, 1, 4),
  (3, 1, 5),
  (3, 1, 6),
  (3, 1, 7),
  (3, 2, 8),
  (3, 2, 9),
  (3, 2, 10),
  (3, 2, 11),
  (3, 2, 12),
  (3, 2, 13),
  (3, 2, 14),
  (3, 2, 15),
  (3, 3, 16),
  (3, 3, 17),
  (3, 3, 18),
  (3, 3, 19),
  (3, 3, 20),
  (3, 4, 21),
  (3, 4, 22),
  (3, 4, 23),
  (3, 4, 24),
  (3, 4, 25),
  (3, 4, 26),
  (3, 4, 27),
  (3, 5, 28),
  (3, 5, 29),
  (3, 5, 30),
  (3, 5, 31),
  (3, 5, 32),
  (3, 5, 33),
  (3, 5, 34),
  (3, 6, 35),
  (3, 6, 36),
  (3, 6, 37),
  (3, 6, 38),
  (3, 6, 39),
  (3, 7, 40),
  (3, 7, 41),
  (3, 7, 42),
  (3, 7, 43),
  (3, 7, 44),
  (3, 7, 45);
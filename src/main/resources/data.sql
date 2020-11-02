DROP TABLE IF EXISTS student;

CREATE TABLE student (
  id INT PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) DEFAULT NULL,
  class VARCHAR(100) DEFAULT NULL,
  nationality VARCHAR(100) DEFAULT NULL
);

INSERT INTO student (id, first_name, last_name, class, nationality) VALUES
  (1, 'Stefan', 'Auw Yang', 'S2', 'Singapore'),
  (2, 'Fike', 'Inda Gunawan', 'S1', 'Singapore'),
  (3, 'Jaden', 'Auw Yang', 'TK', 'Indonesia');
DROP TABLE IF EXISTS student;

CREATE TABLE student (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) DEFAULT NULL,
  class VARCHAR(100) DEFAULT NULL,
  nationality VARCHAR(100) DEFAULT NULL
);

INSERT INTO student (first_name, last_name, class, nationality) VALUES
  ('Stefan', 'Auw Yang', 'S2', 'Singapore'),
  ('Fike', 'Inda Gunawan', 'S1', 'Singapore'),
  ('Jaden', 'Auw Yang', 'TK', 'Indonesia');
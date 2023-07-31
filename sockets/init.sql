CREATE TABLE STUDENTS(
    id INT PRIMARY KEY AUTO_INCREMENT,
    password VARCHAR(40) NOT NULL,
    name VARCHAR(40) NOT NULL
);

CREATE TABLE MARKS(
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    cpp_mark  Double,
    java_mark Double,
    ruby_mark Double
);

INSERT INTO STUDENTS(id, password, name)
VALUES (1, "123", "Osama"),
       (2, "1234", "Rama"),
       (3, "1321", "Ahamd");

INSERT INTO MARKS(id, student_id, cpp_mark, java_mark, ruby_mark)
VALUES (1, 1, 90, 75, 13),
       (2, 2, 80, 77, 26),
       (3, 3, 70, 100, 99);
CREATE TABLE student (
	student_id NUMBER(10) PRIMARY KEY,
	sname CHAR(30) NOT NULL,
	study_year NUMBER(1) DEFAULT 1
);

--Add a new column to an existing table
ALTER TABLE student ADD date_of_birth DATE 
NOT NULL;

--Modify an existing column
ALTER TABLE student MODIFY sname CHAR(40);

--Rename an existing column
ALTER TABLE student RENAME COLUMN student_id TO sid;

--Remove an existing column
ALTER TABLE student DROP COLUMN sname;

--Delete a table
DROP TABLE student;

--Remove all the rows within a table
TRUNCATE TABLE student;

--Insert a row with specified values
INSERT INTO student (student_id, sname) 
VALUES (1155123456, 'Jack');

--Copy rows from another table
INSERT INTO student (student_id, sname) 
SELECT sid, sname 
FROM new_student;

--Update the values of a row
UPDATE student
SET sname = 'David', study_year = 2
WHERE student_id = 1155123456; 

--Update all the rows within the table
UPDATE student SET study_year = 3;

--Delete a row
DELETE FROM student WHERE student_id = 1155123456; 

--Delete all the rows within the table
DELETE FROM student;

--Select all columns
SELECT * FROM student;

--Select specific columns
SELECT student_id, sname FROM student;

--Rename the column (temporarily)
SELECT student_id AS SID, 
study_year AS "Year of Study" 
FROM student;

--Eliminate the duplicate rows
SELECT DISTINCT study_year FROM student;

--Select rows based on some conditions
SELECT student_id, sname
FROM student
WHERE study_year > 1 AND sname LIKE 'J%';

--Sort the result set in ascending order
SELECT * FROM student ORDER BY student_id ASC;

--Sort the result set in descending order
SELECT * FROM student ORDER BY student_id DESC;

--Sort the result set by column alias
SELECT student_id AS ID FROM student 
ORDER BY ID ASC;

--Sort the result by 2 columns (Sorted by name followed by study_year)
SELECT sname, study_year FROM student 
ORDER BY sname ASC, study_year ASC;

--Display data from multiple table
SELECT programme.name, student_id 
FROM student, programme
WHERE prog_code = programme_id

--Use table alias
SELECT P.name, student_id 
FROM student, programme P
WHERE prog_code = programme_id;

--Count the numbers of rows in different group
SELECT study_year, COUNT(*) 
FROM student
GROUP BY study_year;

--Use together with HAVING
SELECT study_year, COUNT(*) 
FROM student
WHERE sname NOT LIKE 'J%';
GROUP BY study_year
HAVING COUNT(*) > 1;

--Single-row subquery
SELECT sname FROM student
WHERE student_id = (SELECT MAX(sid) FROM new_student);

--Multiple-row subquery
SELECT sname FROM student
WHERE student_id = ANY(SELECT sid FROM new_student);

--Pass value to the subquery
SELECT sname FROM student S
WHERE EXISTS (SELECT * FROM new_student
WHERE sid = S.student_id);

--Pass value to the subquery
SELECT sname FROM student S
WHERE EXISTS (SELECT * 
FROM new_student
WHERE sid = S.student_id);

--INTERSECT
--UNION
--EXCEPT

--Create a view
CREATE VIEW temp AS SELECT * 
FROM student WHERE study_year = 3;

--Drop a view
DROP VIEW temp;

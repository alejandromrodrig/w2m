ALTER SEQUENCE hibernate_sequence RESTART WITH 1;

DELETE FROM HERO;

INSERT INTO HERO(id,name,gender,birth_date,number, description, range) VALUES(100,'Spiderman','M','1990-12-07','633719666','Spiderman - 2000', 'A');
INSERT INTO HERO(id,name,gender,birth_date,number, description, range) VALUES(101,'Superman','M','1989-07-11','785873275','Superman', 'S');
INSERT INTO HERO(id,name,gender,birth_date,number, description, range) VALUES(102,'Manolito el fuerte','M','1992-10-01','607804892','Amateur', 'C');
INSERT INTO HERO(id,name,gender,birth_date,number, description, range) VALUES(103,'Lobezno','M','1978-02-21','748242723','Malas pulgas', 'A');
INSERT INTO HERO(id,name,gender,birth_date,number, description, range) VALUES(104,'Bruce Wayne','M','1994-12-01','626205170','Excentrico', 'B');
INSERT INTO HERO(id,name,gender,birth_date,number, description, range) VALUES(105,'Gandalf','M','1992-10-01','694199811', 'Mago blanco', 'S');
INSERT INTO HERO(id,name,gender,birth_date,number, description, range) VALUES(106,'Capitan Calzones','M','1978-02-21','676129657','Talentoso','C');
INSERT INTO HERO(id,name,gender,birth_date,number, description, range) VALUES(107,'Antman','M','1994-12-01','690818561','Minusculo', 'B');
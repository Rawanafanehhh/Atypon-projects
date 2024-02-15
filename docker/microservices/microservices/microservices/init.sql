CREATE database dockerr;
USE dockerr;
CREATE TABLE user(id int , username varchar(50) , password varchar(50));
CREATE TABLE Datas(user_name varchar(50) , data double);
CREATE TABLE statistics(id int , min double , max double , avg double);
INSERT INTO user VALUES (1 , "a" , "1");
INSERT INTO user VALUES (2 , "b" , "2");
INSERT INTO user VALUES (3 , "c" , "3");


CREATE DATABASE nutricao;

USE nutricao;

CREATE TABLE usuarios (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(100),
  peso DOUBLE,
  altura DOUBLE
);

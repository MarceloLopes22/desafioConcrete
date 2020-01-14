Create database concreteDb;

CREATE TABLE telefone (
  id INTEGER IDENTITY NOT NULL,
  ddd VARCHAR(3) NULL,
  numero VARCHAR(15) NULL,
  PRIMARY KEY(id)
);

CREATE TABLE usuario (
  id INTEGER IDENTITY NOT NULL,
  telefone_id INTEGER NOT NULL,
  nome VARCHAR(100) NULL,
  email VARCHAR(50) NULL,
  senha VARCHAR(50) NULL,
  data_criacao DATE NULL,
  data_alteracao DATE NULL,
  data_ultimo_login DATE NULL,
  token VARCHAR(100) NULL,
  PRIMARY KEY(id),
  INDEX usuario_FKIndex1(telefone_id),
  FOREIGN KEY(telefone_id)
    REFERENCES telefone(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);



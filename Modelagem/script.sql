CREATE TABLE tb_user (
  id INTEGER NOT NULL,
  name VARCHAR NULL,
  email VARCHAR NULL,
  password_2 VARCHAR NULL,
  created DATE NULL,
  modified DATE NULL,
  last_login DATE NULL,
  token VARCHAR NULL,
  PRIMARY KEY(id)
);

CREATE TABLE tb_phone (
  id INTEGER NOT NULL,
  tb_user_id INTEGER NOT NULL,
  ddd VARCHAR NULL,
  number VARCHAR NULL,
  PRIMARY KEY(id),
  INDEX tb_phone_FKIndex1(tb_user_id),
  FOREIGN KEY(tb_user_id)
    REFERENCES tb_user(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);



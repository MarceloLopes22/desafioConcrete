CREATE TABLE Conhecimento (
  idConhecimento INTEGER NOT NULL,
  descricao VARCHAR(255) NOT NULL,
  tipo INTEGER NULL,
  criado_por VARCHAR(50) NULL,
  alterado_por VARCHAR(50) NULL,
  data_criacao TIMESTAMP NULL,
  data_alteracao TIMESTAMP NULL,
  PRIMARY KEY(idConhecimento)
);

CREATE TABLE Vaga (
  idVaga INTEGER NOT NULL,
  titulo VARCHAR(100) NOT NULL,
  descricao VARCHAR(2000) NOT NULL,
  salario DECIMAL NOT NULL,
  quantidade INTEGER NOT NULL,
  data_validade DATE NOT NULL,
  horario_expediente VARCHAR(100) NOT NULL,
  observacao VARCHAR(255) NOT NULL,
  uf VARCHAR(2) NULL,
  municipio VARCHAR(30) NULL,
  criado_por VARCHAR(50) NULL,
  alterado_por VARCHAR(50) NULL,
  data_alteracao TIMESTAMP NULL,
  data_criacao TIMESTAMP NULL,
  PRIMARY KEY(idVaga)
);

CREATE TABLE Curso (
  idCurso INTEGER NOT NULL,
  descricao VARCHAR(255) NOT NULL,
  tipo INTEGER NOT NULL,
  criado_por VARCHAR(50) NULL,
  alterado_por VARCHAR(50) NULL,
  data_criacao TIMESTAMP NULL,
  data_alteracao TIMESTAMP NULL,
  PRIMARY KEY(idCurso)
);

CREATE TABLE usuario (
  idUsuario INTEGER NOT NULL,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(255) NOT NULL,
  senha VARCHAR(30) NOT NULL,
  foto VARCHAR(30) NULL,
  telefone_celular VARCHAR(11) NULL,
  telefone_fixo VARCHAR(10) NULL,
  uf VARCHAR(1) NOT NULL,
  municipio VARCHAR(30) NOT NULL,
  criado_por VARCHAR(50) NULL,
  alterado_por VARCHAR(50) NULL,
  data_criacao TIMESTAMP NULL,
  data_alteracao TIMESTAMP NULL,
  PRIMARY KEY(idUsuario)
);

CREATE TABLE Desafio (
  idDesafio INTEGER NOT NULL,
  descricao VARCHAR NULL,
  data_inicio_previsto DATE NULL,
  data_fim_prevista DATE NULL,
  prazo_execucao INTEGER NULL,
  criado_por VARCHAR(50) NULL,
  alterado_por VARCHAR(50) NULL,
  data_criacao TIMESTAMP NULL,
  data_alteracao TIMESTAMP NULL,
  PRIMARY KEY(idDesafio)
);

CREATE TABLE beneficio (
  idBeneficio INTEGER NOT NULL,
  descricao VARCHAR(100) NULL,
  criado_por VARCHAR(50) NULL,
  alterado_por VARCHAR(50) NULL,
  data_criacao TIMESTAMP NULL,
  data_alteracao TIMESTAMP NULL,
  PRIMARY KEY(idBeneficio)
);

CREATE TABLE Professor (
  idProfessor INTEGER UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  idUsuario INTEGER NOT NULL,
  PRIMARY KEY(idProfessor, idUsuario),
  INDEX Professor_FKIndex1(idUsuario),
  FOREIGN KEY(idUsuario)
    REFERENCES usuario(idUsuario)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Pergunta (
  idPergunta INTEGER NOT NULL,
  idDesafio INTEGER NOT NULL,
  descricao VARCHAR(150) NULL,
  PRIMARY KEY(idPergunta),
  INDEX Pergunta_FKIndex2(idDesafio),
  FOREIGN KEY(idDesafio)
    REFERENCES Desafio(idDesafio)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE candidato (
  idCandidato INTEGER NOT NULL,
  idUsuario INTEGER NOT NULL,
  cpf VARCHAR(15) NOT NULL,
  sexo INTEGER NOT NULL,
  data_nascimento DATE NOT NULL,
  PRIMARY KEY(idCandidato, idUsuario),
  INDEX candidato_FKIndex1(idUsuario),
  FOREIGN KEY(idUsuario)
    REFERENCES usuario(idUsuario)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Empresa (
  idEmpresa INTEGER NOT NULL,
  idUsuario INTEGER NOT NULL,
  cnpj VARCHAR(20) NOT NULL,
  porte INTEGER NOT NULL,
  area_atuacao VARCHAR(255) NOT NULL,
  site VARCHAR(255) NULL,
  PRIMARY KEY(idEmpresa, idUsuario),
  INDEX empresa_FKIndex2(idUsuario),
  FOREIGN KEY(idUsuario)
    REFERENCES usuario(idUsuario)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Vaga_beneficio (
  idVaga INTEGER NOT NULL,
  idBeneficio INTEGER NOT NULL,
  valor DECIMAL NULL,
  observacao VARCHAR(120) NULL,
  PRIMARY KEY(idVaga, idBeneficio),
  INDEX Vaga_has_beneficio_FKIndex1(idVaga),
  INDEX Vaga_has_beneficio_FKIndex2(idBeneficio),
  FOREIGN KEY(idVaga)
    REFERENCES Vaga(idVaga)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(idBeneficio)
    REFERENCES beneficio(idBeneficio)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Vaga_Conhecimento (
  idConhecimento INTEGER NOT NULL,
  idVaga INTEGER NOT NULL,
  nivel INTEGER UNSIGNED NULL,
  PRIMARY KEY(idConhecimento, idVaga),
  INDEX vaga_has_Conhecimento_FKIndex1(idVaga),
  INDEX vaga_has_Conhecimento_FKIndex2(idConhecimento),
  FOREIGN KEY(idVaga)
    REFERENCES Vaga(idVaga)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(idConhecimento)
    REFERENCES Conhecimento(idConhecimento)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Vaga_Desafio (
  idVaga INTEGER NOT NULL,
  Desafio_idDesafio INTEGER NOT NULL,
  PRIMARY KEY(idVaga, Desafio_idDesafio),
  INDEX Vaga_has_Desafios_FKIndex1(idVaga),
  INDEX Vaga_Desafio_FKIndex2(Desafio_idDesafio),
  FOREIGN KEY(idVaga)
    REFERENCES Vaga(idVaga)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Desafio_idDesafio)
    REFERENCES Desafio(idDesafio)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Professor_Curso (
  idProfessor INTEGER UNSIGNED ZEROFILL NOT NULL,
  idCurso INTEGER NOT NULL,
  idUsuario INTEGER NOT NULL,
  PRIMARY KEY(idProfessor, idCurso, idUsuario),
  INDEX Curso_has_Professor_FKIndex1(idCurso),
  INDEX Curso_has_Professor_FKIndex2(idProfessor, idUsuario),
  FOREIGN KEY(idCurso)
    REFERENCES Curso(idCurso)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(idProfessor, idUsuario)
    REFERENCES Professor(idProfessor, idUsuario)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE candidato_Desafio (
  idUsuario INTEGER NOT NULL,
  idCandidato INTEGER NOT NULL,
  idDesafio INTEGER NOT NULL,
  data_inicio_realizada TIMESTAMP NOT NULL,
  data_fim_realizada TIMESTAMP NOT NULL,
  PRIMARY KEY(idUsuario, idCandidato, idDesafio),
  INDEX candidato_has_Desafio_FKIndex1(idCandidato, idUsuario),
  INDEX candidato_has_Desafio_FKIndex2(idDesafio),
  FOREIGN KEY(idCandidato, idUsuario)
    REFERENCES candidato(idCandidato, idUsuario)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(idDesafio)
    REFERENCES Desafio(idDesafio)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE candidato_Curso (
  idCurso INTEGER NOT NULL,
  idCandidato INTEGER NOT NULL,
  idUsuario INTEGER NOT NULL,
  PRIMARY KEY(idCurso, idCandidato, idUsuario),
  INDEX candidato_has_Curso_FKIndex1(idCandidato, idUsuario),
  INDEX candidato_has_Curso_FKIndex2(idCurso),
  FOREIGN KEY(idCandidato, idUsuario)
    REFERENCES candidato(idCandidato, idUsuario)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(idCurso)
    REFERENCES Curso(idCurso)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE candidato_Conhecimento (
  idCandidato INTEGER NOT NULL,
  idConhecimento INTEGER NOT NULL,
  idUsuario INTEGER NOT NULL,
  nivel INTEGER UNSIGNED NULL,
  PRIMARY KEY(idCandidato, idConhecimento, idUsuario),
  INDEX candidato_has_Conhecimento_FKIndex1(idCandidato, idUsuario),
  INDEX candidato_has_Conhecimento_FKIndex2(idConhecimento),
  FOREIGN KEY(idCandidato, idUsuario)
    REFERENCES candidato(idCandidato, idUsuario)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(idConhecimento)
    REFERENCES Conhecimento(idConhecimento)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Empresa_Vaga (
  idUsuario INTEGER NOT NULL,
  idEmpresa INTEGER NOT NULL,
  idVaga INTEGER NOT NULL,
  PRIMARY KEY(idUsuario, idEmpresa, idVaga),
  INDEX Empresa_has_Vaga_FKIndex1(idEmpresa, idUsuario),
  INDEX Empresa_has_Vaga_FKIndex2(idVaga),
  FOREIGN KEY(idEmpresa, idUsuario)
    REFERENCES Empresa(idEmpresa, idUsuario)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(idVaga)
    REFERENCES Vaga(idVaga)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE candidato_Vaga (
  idCandidato INTEGER NOT NULL,
  idVaga INTEGER NOT NULL,
  idUsuario INTEGER NOT NULL,
  PRIMARY KEY(idCandidato, idVaga, idUsuario),
  INDEX candidato_has_Vaga_FKIndex1(idCandidato, idUsuario),
  INDEX candidato_has_Vaga_FKIndex2(idVaga),
  FOREIGN KEY(idCandidato, idUsuario)
    REFERENCES candidato(idCandidato, idUsuario)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(idVaga)
    REFERENCES Vaga(idVaga)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE instituicao_ensino (
  idInstituicao_Ensino INTEGER NOT NULL,
  idUsuario INTEGER NOT NULL,
  idEmpresa INTEGER NOT NULL,
  cnpj VARCHAR(20) NULL,
  tipo INTEGER NULL,
  PRIMARY KEY(idInstituicao_Ensino, idUsuario, idEmpresa),
  INDEX instituicao_ensino_FKIndex1(idEmpresa, idUsuario),
  FOREIGN KEY(idEmpresa, idUsuario)
    REFERENCES Empresa(idEmpresa, idUsuario)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Resposta (
  idResposta INTEGER NOT NULL,
  idPergunta INTEGER NOT NULL,
  opcao VARCHAR NULL,
  isCorreta BOOL NULL,
  PRIMARY KEY(idResposta),
  INDEX Resposta_FKIndex1(idPergunta),
  FOREIGN KEY(idPergunta)
    REFERENCES Pergunta(idPergunta)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE instituicao_ensino_Curso (
  idInstituicao_Ensino INTEGER NOT NULL,
  idCurso INTEGER NOT NULL,
  idEmpresa INTEGER NOT NULL,
  idUsuario INTEGER NOT NULL,
  PRIMARY KEY(idInstituicao_Ensino, idCurso, idEmpresa, idUsuario),
  INDEX instituicao_ensino_has_Curso_FKIndex1(idInstituicao_Ensino, idUsuario, idEmpresa),
  INDEX instituicao_ensino_has_Curso_FKIndex2(idCurso),
  FOREIGN KEY(idInstituicao_Ensino, idUsuario, idEmpresa)
    REFERENCES instituicao_ensino(idInstituicao_Ensino, idUsuario, idEmpresa)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(idCurso)
    REFERENCES Curso(idCurso)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);



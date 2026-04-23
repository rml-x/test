DROP DATABASE IF EXISTS sistema_requerimento;

CREATE DATABASE sistema_requerimento;

\c sistema_requerimento;

CREATE TABLE curso (
    id serial primary key,
    nome character varying(200) not null,
    site character varying(200),
    turno character varying(200) check(turno in('NOTURNO', 'DIURNO', 'VESPERTINO')),
    duracao integer check(duracao > 0), -- em horas
    ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE usuario (
    id serial primary key,
    nome character varying(200) not null,
    email character varying(200) unique,
    cpf character(11) unique,
    data_nascimento date,
    cep character(8),
    rua text,
    complemento text,
    nro character varying(10)
);

CREATE TABLE aluno (
    matricula character(10) primary key,
    usuario_id integer references usuario (id), -- fk
    curso_id integer references curso (id), -- fk
    status character varying(200) check(status in('CURSANDO', 'ABANDONO', 'TRANCADO', 'FORMADO')) DEFAULT 'CURSANDO'
);

CREATE TABLE tipo_requerimento (
    id serial primary key,
    descricao text not null
);
INSERT INTO tipo_requerimento (descricao) VALUES
('Abreviaaoo de Curso Superior ou Antecipacao de Colacao'),
('Ajuste de Matricula'),
('Aproveitamento de Estudos'),
('Atestado de Frequencia'),
('Certificacao de Conhecimentos'),
('Certificacao ENEM ou ENCCEJA'),
('Cancelamento de Matricula'),
('Historico'),
('Justificativa ou Abono de Faltas e Solicitacao de Segunda Chamada'),
('Registro de Nome Social'),
('Quebra de PreRequisito'),
('Reingresso'),
('Rematrícula'),
('Revisao de Prova ou Exame'),
('Trancamento de Disciplina'),
('Trancamento de Matricula'),
('Troca de Turma'),
('Validacao de Atividades Complementares');

CREATE TABLE requerimento (
    id serial primary key,
    aluno_matricula character(10) references aluno (matricula),
    data_hora_abertura timestamp default current_timestamp,
    data_hora_encerramento timestamp,
    observacao text,
    status text check(status in ('EM ANALISE', 'INDEFERIDO', 'DEFERIDO')) DEFAULT 'EM ANALISE',
    tipo_requerimento_id integer references tipo_requerimento (id) -- fk
);

CREATE TABLE anexo (
    id serial primary key,
    descricao text not null,
    arquivo bytea,
    requerimento_id integer references requerimento (id) -- fk
);

-- INSERTS --

INSERT INTO curso (nome, site, turno, duracao) VALUES
('Ciência da Computação', 'http://ifrs.edu.br/computacao', 'NOTURNO', 3200),
('Análise e Desenvolvimento de Sistemas', 'http://ifrs.edu.br/ads', 'VESPERTINO', 2400),
('Engenharia Mecânica', 'http://ifrs.edu.br/mecanica', 'DIURNO', 4000);

INSERT INTO usuario (nome, email, cpf, data_nascimento, cep, rua, nro) VALUES
('Ricardo Silva', 'ricardo.silva@email.com', '12345678901', '1995-03-15', '96200100', 'Rua Marechal Floriano', '123'),
('Ana Oliveira', 'ana.oliveira@email.com', '98765432100', '2000-07-22', '96201050', 'Av. Presidente Vargas', '450'),
('Beatriz Souza', 'beatriz.souza@email.com', '55544433322', '1998-11-30', '96200000', 'Rua General Neto', '88');

INSERT INTO aluno (matricula, usuario_id, curso_id, status) VALUES
('2023100001', 1, 1, 'CURSANDO'), -- Ricardo em Ciência da Computação
('2023100002', 2, 2, 'CURSANDO'), -- Ana em ADS
('2022100050', 3, 1, 'TRANCADO'); -- Beatriz em Ciência da Computação

INSERT INTO requerimento (aluno_matricula, observacao, status, tipo_requerimento_id) VALUES
('2023100001', 'Solicito aproveitamento da disciplina de Algoritmos.', 'EM ANALISE', 3),
('2023100002', 'Perdi a prova de BD devido a problemas de saude.', 'DEFERIDO', 9),
('2022100050', 'Gostaria de trancar a matricula por motivos pessoais.', 'INDEFERIDO', 16);

INSERT INTO anexo (descricao, requerimento_id) VALUES
('Historico Escolar da instituicao anterior', 1),
('Atestado Medico - Clinica Santa Casa', 2);

SELECT u.nome, c.nome as curso, a.matricula, a.status
FROM usuario u
JOIN aluno a ON u.id = a.usuario_id
JOIN curso c ON a.curso_id = c.id;
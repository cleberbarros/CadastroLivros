CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role VARCHAR NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE Livro (
    codl BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(40) NOT NULL,
    editora VARCHAR(40) NOT NULL,
    edicao Integer NOT NULL,
    anopublicacao VARCHAR(4) NOT NULL
);

CREATE TABLE Autor (
    codau BIGSERIAL PRIMARY KEY,
    nome VARCHAR(40) NOT NULL
);

CREATE TABLE Livro_Autor (
    livro_codl BIGINT NOT NULL,
    autor_codau BIGINT NOT NULL,
    FOREIGN KEY (livro_codl) REFERENCES Livro (codl),
    FOREIGN KEY (autor_codau) REFERENCES Autor (codau)
);

CREATE TABLE Assunto (
    codas BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(20) NOT NULL
);

CREATE TABLE Livro_Assunto (
    livro_codl BIGINT NOT NULL,
    assunto_codas BIGINT NOT NULL,
    FOREIGN KEY (livro_codl) REFERENCES Livro (codl),
    FOREIGN KEY (assunto_codas) REFERENCES Assunto (codas)
);

CREATE TABLE Venda (
    codve BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(20) NOT NULL,
    valor NUMERIC(15,2)
);

CREATE TABLE Livro_Venda (
    livro_codl BIGINT NOT NULL,
    venda_codve BIGINT NOT NULL,
    FOREIGN KEY (livro_codl) REFERENCES Livro (codl),
    FOREIGN KEY (venda_codve) REFERENCES Venda (codve)
);


-- Inserir usuários
INSERT INTO users (name, email) VALUES ('Cleber Barros', 'cleberbarros.ti@gmail.com');
INSERT INTO users (name, email) VALUES ('Ana Carla', 'anacarlapaes77@gmail.com');
INSERT INTO users (name, email) VALUES ('Cleber 2', 'gnucleber@gmail.com');

-- Inserir tipos de usuários
INSERT INTO user_roles (user_id, role) VALUES (1, 'MANAGER');
INSERT INTO user_roles (user_id, role) VALUES (2, 'MANAGER');
INSERT INTO user_roles (user_id, role) VALUES (3, 'USER');

-- Inserir Autores
INSERT INTO Autor (Nome) VALUES ('J.R.R. Tolkien');
INSERT INTO Autor (Nome) VALUES ('George R.R. Martin');

-- Inserir Assuntos
INSERT INTO Assunto (Descricao) VALUES ('Fantasia');
INSERT INTO Assunto (Descricao) VALUES ('Aventura');

-- Inserir Formatos de Venda
INSERT INTO Venda (Descricao, Valor) VALUES ('Impresso', 49.90);
INSERT INTO Venda (Descricao, Valor) VALUES ('E-book', 29.90);

-- Inserir Livros
INSERT INTO Livro (Titulo, Editora, Edicao, AnoPublicacao) VALUES ('O Senhor dos Anéis', 'Martins Fontes', 1, '1954');
INSERT INTO Livro (Titulo, Editora, Edicao, AnoPublicacao) VALUES ('A Guerra dos Tronos', 'Leya', 1, '1996');

-- Relacionar Livro com Autores
INSERT INTO Livro_Autor (Livro_Codl, Autor_CodAu) VALUES (1, 1); -- O Senhor dos Anéis - J.R.R. Tolkien
INSERT INTO Livro_Autor (Livro_Codl, Autor_CodAu) VALUES (2, 2); -- A Guerra dos Tronos - George R.R. Martin

-- Relacionar Livro com Assuntos
INSERT INTO Livro_Assunto (Livro_Codl, Assunto_CodAs) VALUES (1, 1); -- O Senhor dos Anéis - Fantasia
INSERT INTO Livro_Assunto (Livro_Codl, Assunto_CodAs) VALUES (2, 2); -- A Guerra dos Tronos - Aventura

-- Relacionar Livro com Formatos de Venda
INSERT INTO Livro_Venda (Livro_Codl, Venda_CodVe) VALUES (1, 1); -- O Senhor dos Anéis - Impresso
INSERT INTO Livro_Venda (Livro_Codl, Venda_CodVe) VALUES (2, 2); -- A Guerra dos Tronos - E-book



--view para relatorio
CREATE OR REPLACE VIEW vw_livros_detalhes AS
SELECT 
    l.codl AS livro_id,
    l.titulo AS livro_titulo,
    l.editora AS livro_editora,
    l.edicao AS livro_edicao,
    l.anopublicacao AS livro_anopublicacao,
    COALESCE(STRING_AGG(DISTINCT a.nome, ', '), 'Sem Autor') AS autores,
    COALESCE(STRING_AGG(DISTINCT ass.descricao, ', '), 'Sem Assunto') AS assuntos,
    COALESCE(STRING_AGG(DISTINCT v.descricao || ' (R$' || v.valor || ')', ', '), 'Sem Venda') AS vendas
FROM 
    Livro l
LEFT JOIN 
    Livro_Autor la ON l.codl = la.livro_codl
LEFT JOIN 
    Autor a ON la.autor_codau = a.codau
LEFT JOIN 
    Livro_Assunto las ON l.codl = las.livro_codl
LEFT JOIN 
    Assunto ass ON las.assunto_codas = ass.codas
LEFT JOIN 
    Livro_Venda lv ON l.codl = lv.livro_codl
LEFT JOIN 
    Venda v ON lv.venda_codve = v.codve
GROUP BY 
    l.codl, l.titulo, l.editora, l.edicao, l.anopublicacao;
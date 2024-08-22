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
    livro_codl Integer NOT NULL,
    autor_codau Integer NOT NULL,
    FOREIGN KEY (livro_codl) REFERENCES Livro (codl),
    FOREIGN KEY (autor_codau) REFERENCES Autor (codau)
);

CREATE TABLE Assunto (
    codas BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(20) NOT NULL
);

CREATE TABLE Livro_Assunto (
    livro_codl Integer NOT NULL,
    assunto_codas Integer NOT NULL,
    FOREIGN KEY (livro_codl) REFERENCES Livro (codl),
    FOREIGN KEY (assunto_codas) REFERENCES Assunto (codas)
);

CREATE TABLE FormatoVenda (
    codve BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(20) NOT NULL,
    valor NUMERIC(15,2)
);

CREATE TABLE Livro_FormatoVenda (
    livro_codl Integer NOT NULL,
    venda_codve Integer NOT NULL,
    FOREIGN KEY (livro_codl) REFERENCES Livro (codl),
    FOREIGN KEY (venda_codve) REFERENCES FormatoVenda (codve)
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
INSERT INTO FormatoVenda (Descricao, Valor) VALUES ('Impresso', 49.90);
INSERT INTO FormatoVenda (Descricao, Valor) VALUES ('E-book', 29.90);

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
INSERT INTO Livro_FormatoVenda (Livro_Codl, Venda_CodVe) VALUES (1, 1); -- O Senhor dos Anéis - Impresso
INSERT INTO Livro_FormatoVenda (Livro_Codl, Venda_CodVe) VALUES (2, 2); -- A Guerra dos Tronos - E-book


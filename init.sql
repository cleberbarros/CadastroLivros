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
    Codl BIGSERIAL PRIMARY KEY,
    Titulo VARCHAR(40) NOT NULL,
    Editora VARCHAR(40) NOT NULL,
    Edicao Integer NOT NULL,
    AnoPublicacao VARCHAR(4) NOT NULL
);

CREATE TABLE Autor (
    CodAu BIGSERIAL PRIMARY KEY,
    Nome VARCHAR(40) NOT NULL
);

CREATE TABLE Livro_Autor (
    Livro_Codl Integer NOT NULL,
    Autor_CodAu Integer NOT NULL,
    FOREIGN KEY (Livro_codl) REFERENCES Livro (Codl),
	FOREIGN KEY (Autor_CodAu) REFERENCES Autor (CodAu)
);

CREATE TABLE Assunto (
    CodAs BIGSERIAL PRIMARY KEY,
    Descricao VARCHAR(20) NOT NULL
);

CREATE TABLE Livro_Assunto (
    Livro_Codl Integer NOT NULL,
    Assunto_CodAs Integer NOT NULL,
    FOREIGN KEY (Livro_codl) REFERENCES Livro (Codl),
	FOREIGN KEY (Assunto_CodAs) REFERENCES Assunto (CodAs)
);


CREATE TABLE FormatoVenda (
    CodVe BIGSERIAL PRIMARY KEY,
    Descricao VARCHAR(20) NOT NULL,
	Valor NUMERIC(15,2) 
);


CREATE TABLE Livro_FormatoVenda (
    Livro_Codl Integer NOT NULL,
    Venda_CodVe Integer NOT NULL,
    FOREIGN KEY (Livro_codl) REFERENCES Livro (Codl),
	FOREIGN KEY (Venda_CodVe) REFERENCES FormatoVenda (CodVe)
);



-- Inserir usuários
INSERT INTO users (name, email) VALUES ('Cleber Barros', 'cleberbarros.ti@gmail.com');
INSERT INTO users (name, email) VALUES ('Ana Carla', 'anacarlapaes77@gmail.com');
INSERT INTO users (name, email) VALUES ('Cleber 2', 'gnucleber@gmail.com');

-- Inserir tipos de usuários
INSERT INTO user_roles (user_id, role) VALUES (1, 'MANAGER');
INSERT INTO user_roles (user_id, role) VALUES (2, 'MANAGER');
INSERT INTO user_roles (user_id, role) VALUES (3, 'USER');

# Cadastro de Livros

## Sobre o Projeto
Cadastro de Livros é uma plataforma de cadastro de livros como resultado de um desafio de desenvolvimento. Através do sistema, os usuários podem fazer login para adicionar e controlar livros .

## Tecnologias 
- **Backend:** Java 17, Spring Boot 3
- **Frontend:** Angular 17 (modo standalone)
- **Banco de Dados:** PostgreSQL 16
- **Autenticação:** OAuth2.0 com autenticação do Google
- **Documentação da API:** Swagger

## Estrutura 
- **Backend:** Localizado no diretório `backend`, utiliza Arquitetura limpa.
- **Frontend:** No diretório `frontend`, construído com Angular 17 em modo standalone.
- **Banco de Dados:** Utiliza PostgreSQL 16, com tabelas criadas automaticamente ao iniciar o serviço via Docker.

## Execução do Projeto com Docker
Este projeto está configurado para execução fácil usando Docker e Docker Compose. Certifique-se de ter Docker e Docker Compose instalados no seu sistema.

1. Clone o repositório para a sua máquina.
2. Navegue até a pasta raiz do projeto.
3. Execute o comando: `docker-compose up --build`.
    - Esse comando construirá as imagens necessárias para o backend, frontend e banco de dados, e iniciará os serviços.
4. Após a inicialização dos serviços, o frontend estará disponível em `http://localhost:4200` e o backend em `http://localhost:8080`.

## Swagger
A documentação da API está disponível através do Swagger. Você pode acessá-la em `http://localhost:8080/swagger-ui.html` para visualizar e testar as APIs disponíveis.

## Autenticação
- Para usuários regulares: A autenticação é realizada através do processo padrão de OAuth2.0 com o Google.
- Para usuários do tipo gerente: É necessário registrar diretamente o mesmo e-mail usado com o Google no banco de dados. Isso é necessário para autenticar e acessar funcionalidades específicas de gerente dentro do sistema.


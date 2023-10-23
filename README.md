# README

Este é um projeto com uma aplicação web que consiste em um backend Spring Boot e um frontend Angular. Antes de iniciar, siga as etapas abaixo para configurar e executar a aplicação.

## Pré-requisitos

Certifique-se de ter o seguinte software instalado:

- Java Development Kit (JDK)
- Apache Maven
- Node.js e Angular CLI
- Banco de Dados Postgresql 
- Git

## Configuração do Banco de Dados

Antes de iniciar o aplicativo, é necessário criar as tabelas no banco de dados. Certifique-se de que o banco de dados esteja configurado corretamente e as tabelas sejam criadas. Você pode fazer isso manualmente ou executar os scripts SQL apropriados.

Não esqueca de configura o application.properties com seu Postgresql
```
CREATE TABLE usuario (
    id INT PRIMARY KEY,
    username VARCHAR(16) NOT NULL,
    password VARCHAR(32) NOT NULL,
    token TEXT
);
CREATE TABLE pics (
    id INT PRIMARY KEY,
    user_id INT NOT NULL,
    base64 TEXT,
    mime_type VARCHAR(32),
    url_image VARCHAR(255),
    file_name VARCHAR(255)
);
CREATE TABLE comments (
    id INT PRIMARY KEY,
    pics_id INT NOT NULL,
    user_id INT NOT NULL,
    comment VARCHAR(255),
    username VARCHAR(16)
);
```

## Backend

1. Navegue até a pasta `Back-spring` no seu terminal.

2. Execute o seguinte comando para instalar as dependências do Maven e compilar o projeto:

   ```
   mvn clean install
   ```

   Certifique-se de que todas as dependências estejam instaladas corretamente.

3. Inicie o servidor Spring Boot com o seguinte comando:

   ```
   java -jar target/nome-do-arquivo-jar.jar
   ```

   Substitua `nome-do-arquivo-jar` pelo nome do arquivo gerado após a compilação.

4. O backend estará rodando em `http://localhost:8080`.

## Frontend

1. Navegue até a pasta `front-angular` no seu terminal.

2. Execute o seguinte comando para instalar as dependências do Angular:

   ```
   npm install
   ```

3. Inicie o servidor de desenvolvimento do Angular com o seguinte comando:

   ```
   ng serve
   ```

   O frontend estará acessível em `http://localhost:4200`.

## Autenticação

- O sistema utiliza JWT (JSON Web Token) para autenticação.
- O backend gera um token JWT que deve ser incluído nas requisições como cabeçalho de autorização.
- No frontend, o token JWT é armazenado em `document.cookie` para autenticação.

Certifique-se de configurar corretamente a autenticação para acessar os recursos protegidos.

Agora você deve estar pronto para executar a aplicação. Certifique-se de que todas as etapas foram concluídas com êxito e aproveite a experiência de desenvolvimento!

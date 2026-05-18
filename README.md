# TaskList API

API REST para gerenciamento de tarefas e categorias, desenvolvida com Spring Boot 3, autenticacao via JWT e persistencia em MySQL. O projeto usa migrations com Flyway, validacao de entrada com Jakarta Validation, documentacao OpenAPI/Swagger e testes unitarios com JUnit 5 e Mockito.

## Sumario

- [Sobre o projeto](#sobre-o-projeto)
- [Tecnologias](#tecnologias)
- [Funcionalidades](#funcionalidades)
- [Como executar](#como-executar)
- [Autenticacao](#autenticacao)
- [Endpoints](#endpoints)
- [Exemplos de requisicao](#exemplos-de-requisicao)
- [Testes](#testes)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Autor](#autor)

## Sobre o projeto

O TaskList API centraliza operacoes de uma lista de tarefas, permitindo criar categorias, registrar tarefas, filtrar por status e controlar o ciclo de vida de cada item.

A API foi pensada para demonstrar boas praticas comuns em uma aplicacao backend Java:

- Separacao entre controllers, services, repositories, DTOs e entidades.
- Autenticacao stateless com JWT.
- Migrations versionadas para criacao do schema.
- Validacao dos dados recebidos pela API.
- Tratamento global de erros.
- Documentacao interativa com Swagger UI.
- Testes unitarios para regras de servico.

## Tecnologias

| Area | Tecnologia |
| --- | --- |
| Linguagem | Java 17 |
| Framework | Spring Boot 3.4.2 |
| Web | Spring Web |
| Persistencia | Spring Data JPA + Hibernate |
| Banco de dados | MySQL |
| Migrations | Flyway |
| Seguranca | Spring Security + JWT |
| Validacao | Jakarta Validation |
| Documentacao | SpringDoc OpenAPI / Swagger UI |
| Testes | JUnit 5, Mockito, AssertJ |
| Build | Maven |

## Funcionalidades

- Login com geracao de token JWT.
- CRUD de categorias.
- CRUD de tarefas.
- Filtro de tarefas por `id`.
- Filtro de tarefas por `status`.
- Validacao de payloads de entrada.
- Respostas padronizadas para erros de validacao e recursos nao encontrados.
- Swagger UI para explorar a API.
- CORS configurado para o frontend local em Vite:
  - `http://localhost:5173`
  - `http://127.0.0.1:5173`

## Como executar

### Pre-requisitos

- Java 17 ou superior.
- MySQL em execucao.
- Maven Wrapper incluido no projeto.

### Configurar banco de dados

Crie o banco usado pela aplicacao:

```sql
CREATE DATABASE tasklist;
```

Por padrao, a aplicacao usa as credenciais abaixo em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost/tasklist
spring.datasource.username=root
spring.datasource.password=root
server.port=8081
```

Ajuste usuario e senha conforme o seu ambiente local.

### Subir a aplicacao

```powershell
git clone https://github.com/Netobleu/TaskList.git
cd TaskList
.\mvnw.cmd spring-boot:run
```

Em Linux/macOS:

```bash
git clone https://github.com/Netobleu/TaskList.git
cd TaskList
./mvnw spring-boot:run
```

A API ficara disponivel em:

```text
http://localhost:8081
```

Swagger UI:

```text
http://localhost:8081/swagger-ui.html
```

## Autenticacao

Somente `/auth/**` e os endpoints do Swagger ficam publicos. As demais rotas exigem token JWT no header `Authorization`.

Usuario inicial criado pela migration `V4__popula-users.sql`:

```text
usuario: admin
senha: admin123
```

Login:

```http
POST /auth/login
Content-Type: application/json
```

```json
{
  "username": "admin",
  "password": "admin123"
}
```

Resposta:

```json
{
  "token": "eyJhbGciOi..."
}
```

Use o token nas chamadas autenticadas:

```http
Authorization: Bearer <seu-token-jwt>
```

## Endpoints

### Autenticacao

| Metodo | Endpoint | Descricao | Autenticacao |
| --- | --- | --- | --- |
| POST | `/auth/login` | Autentica o usuario e retorna um JWT | Publica |

### Categorias

| Metodo | Endpoint | Descricao | Autenticacao |
| --- | --- | --- | --- |
| POST | `/api/v1/categorias` | Cria uma categoria | JWT |
| GET | `/api/v1/categorias` | Lista todas as categorias | JWT |
| GET | `/api/v1/categorias?id={id}` | Busca uma categoria por ID | JWT |
| PUT | `/api/v1/categorias/{id}` | Atualiza uma categoria | JWT |
| DELETE | `/api/v1/categorias/{id}` | Remove uma categoria | JWT |

### Tarefas

| Metodo | Endpoint | Descricao | Autenticacao |
| --- | --- | --- | --- |
| POST | `/api/v1/tarefas` | Cria uma tarefa | JWT |
| GET | `/api/v1/tarefas` | Lista todas as tarefas | JWT |
| GET | `/api/v1/tarefas?id={id}` | Busca uma tarefa por ID | JWT |
| GET | `/api/v1/tarefas?status={status}` | Filtra tarefas por status | JWT |
| PUT | `/api/v1/tarefas?id={id}` | Atualiza uma tarefa | JWT |
| DELETE | `/api/v1/tarefas/{id}` | Remove uma tarefa | JWT |

Status aceitos:

```text
PENDENTE
EM_ANDAMENTO
CONCLUIDA
```

## Exemplos de requisicao

### Criar categoria

```http
POST /api/v1/categorias
Authorization: Bearer <token>
Content-Type: application/json
```

```json
{
  "nome": "Estudos"
}
```

### Criar tarefa

```http
POST /api/v1/tarefas
Authorization: Bearer <token>
Content-Type: application/json
```

```json
{
  "titulo": "Finalizar documentacao",
  "descricao": "Revisar README e exemplos de uso da API",
  "status": "PENDENTE",
  "dataCriacao": "2026-05-18",
  "dataAtualizacao": "2026-05-18",
  "categoria": {
    "id": 1,
    "nome": "Estudos"
  }
}
```

### Atualizar tarefa

```http
PUT /api/v1/tarefas?id=1
Authorization: Bearer <token>
Content-Type: application/json
```

```json
{
  "titulo": "Finalizar documentacao",
  "descricao": "README revisado e validado",
  "status": "CONCLUIDA",
  "dataCriacao": "2026-05-18",
  "dataAtualizacao": "2026-05-18",
  "categoria": {
    "id": 1,
    "nome": "Estudos"
  }
}
```

## Testes

Execute a suite automatizada:

```powershell
.\mvnw.cmd test
```

Em Linux/macOS:

```bash
./mvnw test
```

Os testes unitarios cobrem os services de categorias e tarefas, incluindo criacao, listagem, atualizacao, exclusao e cenarios de recurso nao encontrado.

## Estrutura do projeto

```text
src/
  main/
    java/com/br/tasklist/
      config/          Configuracoes de seguranca, JWT, CORS e Swagger
      controller/      Endpoints REST
      dto/             Objetos de entrada e saida da API
      entities/        Entidades JPA
      exception/       Excecoes customizadas e handler global
      repository/      Repositories Spring Data JPA
      service/         Regras de negocio
      TaskListApplication.java
    resources/
      db/migration/    Migrations Flyway
      keys/            Chaves usadas no JWT
      application.properties
  test/
    java/com/br/tasklist/service/
      CategoriaServiceTest.java
      TarefasServiceTest.java
```

## Observacoes de seguranca

- As senhas dos usuarios sao armazenadas com BCrypt.
- O token JWT deve ser enviado apenas pelo header `Authorization`.
- Em um ambiente real, as chaves privadas nao devem ficar versionadas no repositorio.
- As credenciais do banco devem ser externalizadas por variaveis de ambiente ou profiles.

## Autor

**Ivony Mesquita Neto**

LinkedIn: <https://www.linkedin.com/in/ivony-mesquita-47a729241/>

Repositorio: <https://github.com/Netobleu/TaskList>

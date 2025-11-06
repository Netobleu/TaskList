# TaskList API - Gerenciador de Tarefas com Spring Boot 3

API REST **completa**, **segura** e **profissional** para gerenciamento de tarefas com autenticação JWT (RSA), arquitetura limpa e documentação OpenAPI.

---

##  Funcionalidades

- **CRUD completo** de **Tarefas** e **Categorias**
- Filtro de tarefas por **ID** e **status** (Pendente, Em Andamento, Concluída)
- **Autenticação com JWT + chaves RSA** (segurança avançada)
- **Validação de entrada** com Bean Validation
- **Logs estruturados** com SLF4J (INFO/DEBUG/WARN)
- **Tratamento global de exceções** (`404`, `400`, etc)
- **Documentação interativa** com Swagger UI
- **Migrations automáticas** com Flyway
- **Banco MySQL** (produção)

---

## Tecnologias & Ferramentas

| Camada | Tecnologia |
|-------|------------|
| Framework | Spring Boot 3.4.2 |
| Persistência | JPA + Hibernate |
| Banco | MySQL |
| Segurança | Spring Security + JWT (RS256) |
| Documentação | SpringDoc OpenAPI (Swagger) |
| Build | Maven |
| Logs | SLF4J + Logback |
| Validação | Jakarta Validation |
| DTOs | Record (Java 17) |
| Migrations | Flyway |

---

**Endpoints** (Swagger: `/swagger-ui.html`)
**Autenticação**


```json
{
  "username": "admin",
  "password": "admin123"
}
→ Retorna JWT válido por 1h
```

Categorias

```

| Método | Endpoint | Descrição |

POST -> /api/v1/categorias -> Criar categoria

GET -> /api/v1/categorias -> Listar todas ou por ID

PUT -> /api/v1/categorias/{id} -> Atualizar nome

DELETE -> /api/v1/categorias/{id} -> Deletar
```

Tarefas

```
Método | Endpoint | Parâmetros | Descrição

POST -> /api/v1/tarefas,- -> Criar tarefa

GET -> /api/v1/tarefas,"id, status" -> Listar (filtro opcional)

PUT -> /api/v1/tarefas,id (query) -> Atualizar tarefa

DELETE -> /api/v1/tarefas/{id},- -> Deletar
```

Como Rodar

# 1. Clone o projeto
git clone https://github.com/IvonyNeto/TaskList.git
cd TaskList
cd target 
java -jar TaskList-0.0.1-SNAPSHOT.jar

Acesse:

API: http://localhost:8081
Swagger: http://localhost:8081/swagger-ui.html

Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/br/tasklist/
│   │   ├── controller/     → Endpoints REST
│   │   ├── service/        → Lógica de negócio + logs
│   │   ├── repository/     → JPA
│   │   ├── dto/            → Records (imutáveis)
│   │   ├── entities/       → JPA Entities
│   │   ├── exception/      → Exceções personalizadas
│   │   ├── config/         → JWT, Security, Swagger
│   │   └── TaskListApplication.java
│   └── resources/
│       ├── keys/           → app.key.der, app.pub.der (RSA)
│       ├── db/migration/   → Flyway V1__create_tables.sql
│       └── application.yml

```

## Segurança

JWT assinado com RSA 2048 bits

Chaves privadas/públicas **em** src/main/resources/keys/

Senhas criptografadas com BCrypt

Apenas endpoints /auth/** e Swagger liberados

## Autor
**Ivony Mesquita Neto**

https://www.linkedin.com/in/ivony-mesquita-47a729241/


Desenvolvido com foco em boas práticas, segurança e clareza.




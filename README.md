# MotoClube Service

Este projeto é um microsserviço responsável por gerenciar as informações de um motoclube, incluindo MotoClube Geral, Capítulos locais, Membros e seus respectivos Veículos e Cargos. Faz parte de um sistema maior, com autenticação centralizada via `auth-service` e integração futura com serviços de comunicação via Kafka.

## Tecnologias utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Security
- H2 Database (para testes)
- Maven
- Swagger/OpenAPI
- JUnit & Mockito

## Estrutura da aplicação

O projeto utiliza arquitetura em camadas com base em DDD (Domain-Driven Design):

- **Controller**: camadas de entrada da aplicação (REST Controllers)
- **DTOs**: objetos de transferência de dados
- **Service & ServiceImpl**: regras de negócio
- **Domain**: entidades de domínio
- **Repository**: abstração de acesso a dados

## Endpoints disponíveis

A documentação completa dos endpoints está disponível via Swagger:

➡️ [Acesse o Swagger UI](http://localhost:8081/swagger-ui/index.html)

## Entidades já implementadas

- **MotoClubeGeral**: CRUD completo
- **Capitulo**: CRUD completo
- **Membro**: CRUD completo (testes em desenvolvimento)
- **Veiculo**: Relacionado à entidade Membro
- **Cargo**: Relacionado à entidade Membro

## Inicialização de dados

Para facilitar os testes, as tabelas das entidades `MotoClubeGeral`, `Capitulo`, `Membro`, `Cargo` e `Veiculo` são populadas automaticamente via a classe `DataInitializer`.

## Banco de dados

- Utiliza H2 em memória no ambiente de desenvolvimento e testes
- Futuramente será migrado para OracleDB ou MySQL via AWS RDS

## Porta de execução

Este serviço roda na porta `8081`, para não conflitar com o `auth-service`, que roda na porta `8080`.

## Autenticação

A aplicação requer autenticação via Bearer Token. O token deve ser obtido utilizando os endpoints do `auth-service`, que também estão incluídos na **collection do Postman**.

Cada requisição para o `motoclub-service` deve conter o token JWT no cabeçalho `Authorization`, conforme exemplo:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...
```

## Collection do Postman

Uma collection com todas as requisições organizadas foi criada e está disponível para facilitar os testes.

➡️ [Download da Collection Postman](./postman/motoclub-service-collection.json)

> **Nota:** Certifique-se de importar a collection no Postman e configurar o `token` no ambiente (env variable) conforme obtido no login via `auth-service`.

## Autor

Anderson Passos  
[LinkedIn](http://www.linkedin.com/in/anderson-passos-dev)
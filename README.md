# Desafio Sicredi - Sistema de Votação

Este projeto é uma API REST para gerenciamento de pautas e sessões de votação em assembleias. Desenvolvido como parte de um desafio técnico, focado em boas práticas de desenvolvimento, escalabilidade e manutenibilidade.

## 🏛 Arquitetura

A aplicação utiliza os princípios da **Arquitetura Hexagonal (Ports & Adapters)** e **Clean Architecture**, visando a separação total da regra de negócio de detalhes de infraestrutura.

- **Domain**: Contém as entidades de negócio, exceções e regras fundamentais. Não possui dependências externas.
- **Application (Use Cases)**: Implementa os casos de uso do sistema. Define as interfaces de entrada e saída (**Ports**).
- **Adapters (Inbound/Outbound)**: Implementações técnicas que se conectam ao mundo externo.
    - **Web (Inbound)**: Controladores REST e DTOs (Spring Boot).
    - **Persistence (Outbound)**: Implementação dos repositórios utilizando Spring Data JPA e mapeamento de entidades de banco de dados.
- **Infrastructure**: Configurações de frameworks, injeção de dependência e beans do Spring.

## 🚀 Como Executar

### Pré-requisitos
- Docker e Docker Compose instalados.

### Rodando com Docker Compose

A aplicação está configurada para subir o banco de dados PostgreSQL, o Apache Kafka e a própria API de forma orquestrada.

1. Clone o repositório.
2. Na raiz do projeto, execute:
   ```bash
   docker-compose up -d --build
   ```
3. A aplicação estará disponível em `http://localhost:8080`.

### Variáveis de Ambiente
O projeto utiliza um arquivo `.env` para configurações. Um exemplo pode ser encontrado em `.env.example`. Por padrão, o `docker-compose.yml` já possui valores default para facilitar a execução rápida.

---

## 📖 Documentação da API (Swagger)

A API utiliza o **SpringDoc OpenAPI** para documentação automática. Após subir a aplicação, você pode acessar a interface do Swagger em:

🔗 **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

Nesta interface, é possível visualizar todos os endpoints, schemas de requisição e realizar testes diretamente pelo navegador.

---

## 🛠 Como Usar

O fluxo básico da aplicação consiste em:

1. **Criar uma Pauta**:
   - `POST /v1/pautas`
   - Payload: `{"titulo": "Nova Pauta de Teste"}`

2. **Abrir uma Sessão de Votação**:
   - `POST /v1/pautas/{id}/sessao`
   - Payload (opcional): `{"duracaoEmMinutos": 5}` (Default: 1 minuto)

3. **Registrar um Voto**:
   - `POST /v1/pautas/{id}/votos`
   - Payload: `{"associadoId": "ID_DO_ASSOCIADO", "voto": "Sim"}` (Valores aceitos: "Sim" ou "Nao")
   - *Nota: Um associado só pode votar uma vez por pauta.*

4. **Consultar Resultado**:
   - `GET /v1/pautas/{id}/resultado`
   - Retorna o total de votos "Sim" e "Não".

---

## 🧪 Testes

Para executar os testes unitários e de integração:

```bash
mvn test
```

Os testes de integração utilizam **Testcontainers** para subir uma instância real do PostgreSQL durante a execução, garantindo a fidelidade dos testes de persistência.

---

## ⚡ Virtual Threads (Java 21)

A aplicação utiliza **Virtual Threads** (Project Loom), habilitadas nativamente pelo Spring Boot 3.2+ com Java 21, para melhorar a escalabilidade em operações I/O bound.

### O que são Virtual Threads?
Virtual Threads são threads leves gerenciadas pela JVM que permitem lidar com um número muito maior de requisições concorrentes sem o custo de memória das threads tradicionais do sistema operacional.

### Como está configurado?
- A propriedade `spring.threads.virtual.enabled=true` está ativada no `application.yml`.
- O Tomcat passa a utilizar Virtual Threads automaticamente para processar requisições HTTP.
- O endpoint temporário `GET /v1/diagnostic/thread-info` pode ser usado para confirmar que as requisições estão sendo processadas por Virtual Threads.

### Benefícios
- **Maior concorrência I/O bound**: Operações que aguardam respostas do banco de dados, APIs externas ou Kafka não bloqueiam threads do sistema operacional.
- **Sem complexidade adicional**: Não requer mudanças na arquitetura, use cases, ports ou adapters existentes.
- **Escalabilidade natural**: A aplicação pode processar milhares de requisições simultâneas com um pool de threads muito menor.

### Importante
- Virtual Threads **não substituem** otimizações de banco de dados (índices, queries eficientes, controle de concorrência).
- O pool de conexões do **HikariCP** continua sendo o gargalo real, pois o número de conexões ao banco é limitado (configurado entre 5 e 15 conexões). Aumentar o pool de conexões para compensar Virtual Threads não é recomendado — o limite continua sendo o banco de dados.

---

## 🧰 Tecnologias Utilizadas

- **Java 21** (com Virtual Threads)
- **Spring Boot 3.4**
- **Spring Data JPA**
- **Flyway** (Migração de banco de dados)
- **PostgreSQL**
- **Apache Kafka**
- **MapStruct** (Mapeamento de objetos)
- **Lombok**
- **SpringDoc OpenAPI (Swagger)**
- **Testcontainers**
- **Docker & Docker Compose**

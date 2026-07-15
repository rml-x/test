# Sistema de Requerimentos

Aplicação web em Java para gestão acadêmica de cursos, usuários, alunos e requerimentos (solicitações administrativas como trancamento de matrícula, revisão de prova, histórico escolar, etc.), com upload de anexos.

O projeto foi construído com **[Javalin](https://javalin.io/)** (framework web leve), **PostgreSQL** como banco de dados e **Mustache** para renderização das páginas HTML.

## Funcionalidades

- **Cursos**: cadastro, listagem, edição e exclusão.
- **Usuários**: cadastro, listagem, edição e exclusão, com dados de endereço e CPF.
- **Alunos**: vínculo entre usuário e curso, com número de matrícula e status (`CURSANDO`, `ABANDONO`, `TRANCADO`, `FORMADO`).
- **Tipos de Requerimento**: catálogo configurável dos tipos de solicitação disponíveis (ex.: trancamento de matrícula, revisão de prova, aproveitamento de estudos).
- **Requerimentos**: abertura de solicitações por aluno, acompanhamento de status (`EM ANALISE`, `DEFERIDO`, `INDEFERIDO`) e upload de anexos.
- **Anexos**: envio de arquivos vinculados a um requerimento.

## Tecnologias

- Java 25
- [Javalin](https://javalin.io/) 7.x — framework web
- Mustache (`javalin-rendering-mustache`) — templates HTML
- PostgreSQL (driver `org.postgresql:postgresql`)
- Maven — build e gerenciamento de dependências
- SLF4J — logging

## Estrutura do projeto

```
demo/
├── pom.xml
├── src/main/java/
│   ├── apresentacao/     # Main.java — rotas e controle das requisições HTTP
│   ├── negocio/          # Entidades: Aluno, Curso, Requerimento, TipoRequerimento, Usuario, Anexo
│   └── persistencia/     # DAOs e conexão com o banco (JDBC)
└── src/main/resources/
    ├── sistema_requerimento.sql   # script de criação do banco e dados iniciais
    └── templates/                 # páginas Mustache (index, formulários de cada módulo)
```

## Pré-requisitos

- JDK 25+
- Maven 3.8+
- PostgreSQL em execução localmente na porta padrão (`5432`)

## Configuração do banco de dados

1. Execute o script `demo/src/main/resources/sistema_requerimento.sql` em um cliente PostgreSQL (ex.: `psql`) para criar o banco `sistema_requerimento`, as tabelas e os dados iniciais:

   ```bash
   psql -U postgres -f demo/src/main/resources/sistema_requerimento.sql
   ```

2. Por padrão, a aplicação se conecta usando as credenciais definidas em `ConexaoPostgreSQL.java`:

   ```
   URL:     jdbc:postgresql://localhost:5432/sistema_requerimento
   Usuário: postgres
   Senha:   postgres
   ```

   Ajuste esse arquivo caso suas credenciais sejam diferentes.

## Como executar

```bash
cd demo
mvn compile exec:java -Dexec.mainClass="apresentacao.Main"
```

Ou, se preferir gerar e rodar o `.jar`:

```bash
cd demo
mvn package
java -cp target/classes apresentacao.Main
```

A aplicação sobe na porta **7070**. Acesse em:

```
http://localhost:7070
```

## Principais rotas

| Rota | Descrição |
|---|---|
| `GET /` | Lista de cursos (página inicial) |
| `GET /usuarios` | Lista de usuários |
| `GET /alunos` | Lista de alunos |
| `GET /tipo_requerimento` | Lista de tipos de requerimento |
| `GET /requerimento` | Lista de requerimentos |
| `GET /requerimento/aluno/{matricula}` | Requerimentos de um aluno específico |

Cada módulo possui ainda rotas de adição (`/tela_adicionar`), edição (`/tela_alterar/{id}`) e exclusão (`/excluir/{id}`).

## Observações

- Este é um projeto de estudo/demonstração (nome do repositório: `test`), sem autenticação implementada nas rotas.
- O arquivo `demo/README.TXT` original apenas alerta que o script SQL está na pasta `resources`.

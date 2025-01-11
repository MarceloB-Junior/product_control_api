
# Product Control API

## Visão Geral
Product Control é uma aplicação desenvolvida com Spring Boot que fornece uma interface RESTful para o gerenciamento de produtos. Esta API foi projetada para simplificar o processo de gerenciamento de produtos em um sistema, permitindo que os usuários realizem operações essenciais como criação, leitura, atualização e exclusão (CRUD) de registros de produtos. A aplicação inclui testes unitários abrangentes que garantem a qualidade e a robustez do código.

## Tabela de Conteúdos
- [Principais Funcionalidades](#principais-funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Como Começar](#como-começar)
  - [Pré-requisitos](#pré-requisitos)
  - [Instalação](#instalação)
- [Executando a Aplicação](#executando-a-aplicação)
- [Endpoints da API](#endpoints-da-api)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Testes da Aplicação](#testes-da-aplicação)
- [Agradecimentos](#agradecimentos)

## Principais Funcionalidades
- **Gerenciamento Completo**: Permite a criação, leitura, atualização e exclusão de produtos, proporcionando um controle total sobre os registros.
- **Validação de Dados**: Utiliza a validação Bean do Jakarta para garantir que os dados inseridos estejam corretos e atendam aos critérios definidos.
- **Verificação de Conflitos**: Implementa lógica para verificar se um produto com o mesmo nome já está em uso antes da criação ou se o produto não	existe.
- **Documentação Clara**: A API é documentada de forma clara, facilitando a integração por parte de outros desenvolvedores.
- **Suporte a HATEOAS**: Permite que os clientes naveguem pela API utilizando links fornecidos nas respostas.
- **Paginação de Resultados**: Oferece suporte à paginação ao recuperar a lista de produtos.
- **Testes Abrangentes:** Inclui testes unitários e funcionais que garantem a qualidade e a robustez do código, cobrindo as principais funcionalidades da aplicação.

## Tecnologias Utilizadas
- **Java 23**: Linguagem de programação utilizada para a aplicação.
- **Spring Boot 3.3.4**: Framework para construir a API RESTful.
- **Spring Data JPA**: Para interagir com o banco de dados usando JPA.
- **MySQL**: Banco de dados para armazenar registros de produtos.
- **Maven**: Gerenciador de dependências e ferramenta de build.
- **JUnit**: Framework para testes unitários.
- **Mockito**: Biblioteca para simulação (mocking) em testes unitários.
- **Rest Assured**: Biblioteca para testar APIs RESTful.

## Como Começar

### Pré-requisitos
Antes de começar, certifique-se de que você atendeu aos seguintes requisitos:
- Java Development Kit (JDK) 23 ou superior instalado.
- Maven instalado.
- Servidor MySQL em execução.

### Instalação
1. Clone o repositório:
   ```bash
   git clone https://github.com/MarceloB-Junior/product_control_api.git
   cd product_control_api
   ```
2. Configure o banco de dados:
   - Crie um novo banco de dados no MySQL (exemplo: `product_control_db`).
   - Atualize o arquivo `application.properties` com suas credenciais do banco de dados:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/product_control_db
     spring.datasource.username=seu_usuario
     spring.datasource.password=sua_senha
     spring.jpa.hibernate.ddl-auto=update
     ```
3. Instale as dependências:
   ```bash
   mvn install
   ```

## Executando a Aplicação
Para executar a aplicação, use o seguinte comando:
```bash
mvn spring-boot:run
```
A aplicação será iniciada em `http://localhost:8080`.

## Endpoints da API
- **Criar um Produto**
  - `POST /products`
  - Corpo da Requisição:
    ```json
    {
        "name": "Produto Exemplo",
        "value": 99.99
    }
    ```

- **Obter Todos os Produtos**
  - `GET /products`

- **Obter um Produto por ID**
  - `GET /products/{id}`

- **Atualizar um Produto**
  - `PUT /products/{id}`
  - Corpo da Requisição:
    ```json
    {
        "name": "Produto Atualizado",
        "value": 79.99
    }
    ```

- **Excluir um Produto**
  - `DELETE /products/{id}`

## Estrutura do Projeto
```
product_control/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── api/
│   │   │           └── product_control/
│   │   │               ├── configs/
│   │   │               ├── controllers/
│   │   │               ├── dtos/
│   │   │               ├── exceptions/
│   │   │               ├── models/
│   │   │               ├── repositories/
│   │   │               └── services/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/
│               └── api/
│                   └── product_control/
│                       ├── controllers/
│                       └── services/
└── pom.xml

```

## Testes da Aplicação

A Product Control API inclui uma série abrangente de testes unitários e funcionais que garantem a qualidade e a robustez do código. Os testes são divididos entre as classes `ProductServiceTest` e `ProductControllerTest`, cobrindo as principais funcionalidades da aplicação.

### Testes do ProductService

1. **saveProductReturnsProductModel**: Verifica se um produto é salvo corretamente e se o modelo retornado é o esperado.
2. **findAllReturnsPageOfProducts**: Testa a recuperação da lista paginada de produtos.
3. **existsByNameReturnsTrue**: Confirma que a verificação da existência de um produto pelo nome retorna verdadeiro quando o produto existe.
4. **existsByNameReturnsFalse**: Confirma que a verificação da existência de um produto pelo nome retorna falso quando o produto não existe.
5. **findByIdReturnsProductModelOptional**: Verifica se a busca por ID retorna um produto quando ele existe.
6. **findByIdReturnsEmptyOptional**: Verifica se a busca por ID retorna vazio quando o produto não existe.
7. **deleteRemovesProduct**: Testa se a remoção do produto é realizada corretamente.

### Testes do ProductController

1. **shouldReturnCreated_WhenPostingProduct**: Verifica se a criação de um novo produto retorna o status HTTP 201 (Criado).
2. **shouldThrowProductAlreadyExistsException_WhenProductAlreadyExists**: Testa se uma exceção é lançada ao tentar criar um produto que já existe, retornando status HTTP 409 (Conflito).
3. **shouldReturnSuccess_WhenGettingProduct**: Confirma que a recuperação bem-sucedida de um produto por ID retorna status HTTP 200 (OK).
4. **shouldThrowProductNotFoundException_WhenGettingNonExistingProduct**: Verifica se uma exceção é lançada ao tentar recuperar um produto inexistente, retornando status HTTP 404 (Não Encontrado).
5. **shouldReturnSuccess_WhenUpdatingProduct**: Testa se a atualização do produto retorna status HTTP 200 (OK).
6. **shouldThrowProductNotFoundException_WhenUpdatingNonExistingProduct**: Verifica se uma exceção é lançada ao tentar atualizar um produto inexistente, retornando status HTTP 404 (Não Encontrado).
7. **shouldReturnSuccess_WhenDeletingProduct**: Confirma que a exclusão bem-sucedida do produto retorna status HTTP 200 (OK).
8. **shouldThrowProductNotFoundException_WhenDeletingNonExistingProduct**: Verifica se uma exceção é lançada ao tentar excluir um produto inexistente, retornando status HTTP 404 (Não Encontrado).

## Agradecimentos
Agradecimentos à **Michelli Brito**, arquiteta e desenvolvedora Fullstack, por sua significativa contribuição ao entendimento sobre Spring Boot e API RESTful. Michelli é uma referência na comunidade Java e co-autora do livro *"Spring Boot: Da API REST aos Microservices"*.
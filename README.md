# Product Control API

## Visão Geral

**Product Control** é uma aplicação desenvolvida com Spring Boot que fornece uma interface RESTful para o gerenciamento de produtos. Esta API foi projetada para simplificar o processo de gerenciamento de produtos em um sistema, permitindo que os usuários realizem operações essenciais como criação, leitura, atualização e exclusão (CRUD) de registros de produtos.

A aplicação tem como objetivo oferecer uma solução robusta e escalável para empresas que precisam gerenciar seus produtos de forma eficiente. Com um design intuitivo e uma arquitetura bem estruturada, a **Product Control API** é ideal para desenvolvedores que desejam integrar funcionalidades de gerenciamento de produtos em suas aplicações.

### Principais Funcionalidades:

- **Gerenciamento Completo**: Permite a criação, leitura, atualização e exclusão de produtos, proporcionando um controle total sobre os registros.
  
- **Validação de Dados**: Utiliza a validação Bean do Jakarta para garantir que os dados inseridos estejam corretos e atendam aos critérios definidos, como campos obrigatórios e formatos válidos.

- **Verificação de Conflitos**: Implementa lógica para verificar se um produto com o mesmo nome já está em uso antes da criação, evitando duplicações indesejadas.

- **Documentação Clara**: A API é documentada de forma clara, facilitando a integração por parte de outros desenvolvedores.

- **Suporte a HATEOAS**: A aplicação segue os princípios HATEOAS (Hypermedia as the Engine of Application State), permitindo que os clientes naveguem pela API utilizando links fornecidos nas respostas.

- **Paginação de Resultados**: Oferece suporte à paginação ao recuperar a lista de produtos, permitindo uma navegação eficiente em grandes conjuntos de dados.

- **Links Auto-referenciais**: Cada produto retornado inclui links auto-referenciais e links para a lista de produtos, melhorando a usabilidade e a navegação na API.

A **Product Control API** é ideal para ser utilizada em sistemas de e-commerce, inventário ou qualquer aplicação que necessite de um gerenciamento eficiente de produtos. Com sua arquitetura baseada em microserviços e conformidade com as melhores práticas do setor, ela pode ser facilmente escalada e mantida.

## Tabela de Conteúdos

- [Recursos](#recursos)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Como Começar](#como-começar)
  - [Pré-requisitos](#pré-requisitos)
  - [Instalação](#instalação)
  - [Executando a Aplicação](#executando-a-aplicação)
- [Endpoints da API](#endpoints-da-api)
- [Estrutura do Projeto](#estrutura-do-projeto)

## Recursos

- Criar, ler, atualizar e excluir registros de produtos.
- Validar dados de entrada usando Jakarta Bean Validation.
- Verificar se um produto já existe com base no nome.

## Tecnologias Utilizadas

- **Java 23**: Linguagem de programação utilizada para a aplicação.
- **Spring Boot 3.3.4**: Framework para construir a API RESTful.
- **Spring Data JPA**: Para interagir com o banco de dados usando JPA.
- **MySQL**: Banco de dados para armazenar registros de produtos.
- **Maven**: Gerenciador de dependências e ferramenta de build.

## Como Começar

### Pré-requisitos

Antes de começar, certifique-se de que você atendeu aos seguintes requisitos:

- Java Development Kit (JDK) 23 ou superior instalado em sua máquina.
- Maven instalado em sua máquina.
- Servidor MySQL em execução e acessível.

### Instalação

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/MarceloB-Junior/product_control_api.git
   cd product_control_api
   ```

2. **Configure o banco de dados**:
   - Crie um novo banco de dados no MySQL (por exemplo, `product_control_db`).
   - Atualize o arquivo `application.properties` localizado em `src/main/resources` com suas credenciais do banco de dados:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/product_control_db
     spring.datasource.username=seu_usuario
     spring.datasource.password=sua_senha
     spring.jpa.hibernate.ddl-auto=update
     ```

3. **Instale as dependências**:
   ```bash
   mvn install
   ```

### Executando a Aplicação

Para executar a aplicação, use o seguinte comando:
```bash
mvn spring-boot:run
```

A aplicação será iniciada em `http://localhost:8080`.

## Endpoints da API

### Criar um Produto

**POST** `/products`

Corpo da Requisição:
```json
{
    "name": "Produto Exemplo",
    "value": 99.99
}
```

### Obter Todos os Produtos

**GET** `/products`

### Obter um Produto por ID

**GET** `/products/{id}`

### Atualizar um Produto

**PUT** `/products/{id}`

Corpo da Requisição:
```json
{
    "name": "Produto Atualizado",
    "value": 79.99
}
```

### Excluir um Produto

**DELETE** `/products/{id}`

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
└── pom.xml
```

# Agradecimentos

Meus agradecimentos à **Michelli Brito**, arquiteta e desenvolvedora Fullstack, por sua significativa contribuição ao meu entendimento sobre **Spring Boot** e **API RESTful**. Michelli é uma referência na comunidade de desenvolvimento Java, reconhecida por suas palestras e conteúdos educacionais.

Michelli foi premiada como **Microsoft MVP** na categoria **Developer Technologies** em quatro anos consecutivos: **2020, 2021, 2022 e 2023**. Este prêmio destaca sua influência e expertise na área, especialmente em Java e Spring Boot. Além disso, Michelli é co-autora do livro *Spring Boot: Da API REST aos Microservices*, que tem sido uma valiosa fonte de aprendizado para mim e muitos outros profissionais.
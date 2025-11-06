# Click Shop - API de Suporte Técnico

# Arthur Bessa Pian / RM 99215

Esta é uma API RESTful para o módulo de Suporte Técnico da "Click Shop". O projeto simula um sistema de gerenciamento de ocorrências (solicitações de suporte).

O objetivo principal é demonstrar a aplicação de conceitos de arquitetura em camadas, boas práticas de design de APIs REST, tratamento de exceções e o uso adequado de Status Codes HTTP. A aplicação **armazena os dados em memória**, sem a necessidade de um banco de dados externo.

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas para garantir a **Separação de Responsabilidades** (Separation of Concerns), tornando o código mais limpo, organizado e fácil de manter.

* **`controller` (Camada de Interface):** O ponto de entrada da API. Recebe requisições HTTP, traduz JSON para DTOs, chama a camada de serviço e retorna as respostas HTTP (JSON e Status Codes).
* **`service` (Camada de Negócio):** O cérebro da aplicação. Contém toda a lógica e regras de negócio (ex: "Não é permitido fechar um chamado já fechado", "Todo chamado novo começa como ABERTO").
* **`repository` (Camada de Dados):** Responsável por acessar os dados. Nesta aplicação, ela simula um banco de dados em memória usando um `Map` para persistir as ocorrências.
* **`model` (Camada de Domínio):** Define as entidades da nossa aplicação (ex: `Ocorrencia.java`, `StatusOcorrencia.java`).
* **`dto` (Data Transfer Object):** Define os "contratos" de dados que entram na API. São usados para validar e filtrar os dados que o usuário pode enviar.
* **`exception` (Camada de Erros):** Contém um `GlobalExceptionHandler` que captura exceções (como `ResourceNotFoundException`) e as transforma em respostas JSON amigáveis para o usuário.

## ✨ Tecnologias e Ferramentas

* **Java 17** (ou superior)
* **Spring Boot 3.x**
* **Spring Web**: Para a criação dos endpoints RESTful.
* **Spring Validation**: Para validar os DTOs de entrada (ex: `@NotBlank`).
* **Maven**: Gerenciador de dependências e build do projeto.
* **Lombok**: Ferramenta de produtividade.

### O Papel do Lombok

Você notará que as classes de modelo e DTO (como `Ocorrencia.java` e `OcorrenciaInputDTO.java`) usam anotações como `@Data`. Esta é uma anotação do **Lombok**.

O Lombok é uma biblioteca que nos ajuda a **reduzir código repetitivo (boilerplate)**. Em vez de escrevermos manually todos os métodos `getters`, `setters`, `toString()`, `equals()` e `hashCode()` para nossas classes de dados, o Lombok os gera automaticamente para nós em tempo de compilação.

**Por que usamos o Lombok?**
1.  **Código Limpo:** Nossas classes ficam muito menores e focadas apenas nos atributos.
2.  **Produtividade:** Não perdemos tempo escrevendo métodos que não têm lógica.
3.  **Manutenção Fácil:** Se você adicionar um novo campo (ex: `private String prioridade;`), não precisa se lembrar de criar o getter e o setter; o Lombok já faz isso.

> **Importante:** Para que sua IDE (IntelliJ, Eclipse, VS Code) entenda as anotações do Lombok e não mostre erros, você precisa ter o **plugin do Lombok** instalado nela.

## 🚀 Como Executar

1.  Certifique-se de ter o Java 17+ e o Maven instalados em seu sistema.
2.  Clone este repositório:
    ```sh
    git clone <url-do-seu-repositorio>
    cd suporte
    ```
3.  Execute a aplicação usando o Maven:
    ```sh
    mvn spring-boot:run
    ```
4.  O servidor iniciará e estará disponível em `http://localhost:8080`.

## ⚡ Endpoints da API (CRUD)

O prefixo base para todos os endpoints é `/api/ocorrencias`.

| Método | Rota | Descrição | Corpo (Request) | Resposta (Sucesso) |
| :--- | :--- | :--- | :--- | :--- |
| `POST` | `/` | Cria uma nova ocorrência. | JSON (`OcorrenciaInputDTO`) | `201 Created` + JSON (`Ocorrencia`) |
| `GET` | `/` | Lista todas as ocorrências existentes. | N/A | `200 OK` + Lista de `Ocorrencia` |
| `GET` | `/?status=ABERTO` | Lista ocorrências filtrando por status (`ABERTO`, `FECHADO`, etc). | N/A | `200 OK` + Lista de `Ocorrencia` |
| `GET` | `/{id}` | Busca uma ocorrência específica pelo seu ID. | N/A | `200 OK` + JSON (`Ocorrencia`) |
| `PATCH` | `/{id}/encerrar` | **(Regra de Negócio)** Encerra um chamado. | N/A | `200 OK` + JSON (`Ocorrencia`) |
| `DELETE` | `/{id}` | Deleta uma ocorrência pelo seu ID. | N/A | `204 No Content` |

---

> ### Exemplo de `Body` para o `POST`
>
> Use isto no Postman (na aba Body -> raw -> JSON):
>
> ```json
> {
>     "titulo": "Impressora não funciona",
>     "descricao": "A impressora do 3º andar está com a luz vermelha.",
>     "solicitante": "Arthur"
> }
> ```

# 🔒 LockX - Sistema de Armário Inteligente    <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java Logo" />


## 📝 Descrição  
O **LockX** é um sistema de armário inteligente projetado para facilitar a gestão e o uso de armários em condomínios e empresas. Ele oferece funcionalidades avançadas para cadastro de usuários, gestão de compartimentos, notificações de entregas e muito mais, garantindo segurança e praticidade.  

---

## 🚀 Funcionalidades Principais  

**Autenticação e Autorização:** Sistema de segurança completo usando *Spring Security* com autenticação baseada em *Tokens JWT*.

**Controle de Acesso Baseado em Papéis (Roles):** Perfis de usuário bem definidos (ADMIN, MORADOR, PORTEIRO, ENTREGADOR) com permissões específicas para cada endpoint da API.

**Gerenciamento de Entregas:** Fluxo completo de entrega, desde a solicitação de um compartimento pelo entregador até a confirmação de retirada pelo destinatário.

**Acesso Seguro por PIN:**
- Geração de *PINs aleatórios de 6 dígitos* para entregadores (depósito) e destinatários (retirada).
- Os PINs possuem *validade de 2 horas* e são vinculados a uma entrega e compartimento específicos.
- Notificação (simulada) por *e-mail* com os PINs gerados.
- Funcionalidade para *regenerar PINs* expirados.

**Gerenciamento de Armários e Compartimentos:** Controle de status (DISPONIVEL, OCUPADO, RESERVADO, MANUTENCAO) e outras propriedades dos lockers e seus compartimentos.

**Documentação de API:** Geração automática de documentação interativa da API com **Swagger/OpenAPI**.

**Containerização:** Suporte completo para execução em um ambiente containerizado com *Docker*.

---

## 🛠️ Tecnologias Utilizadas  

### 🧩 Backend

* *Backend:*
    * *Java 17+*
    * *Spring Boot 3*
    * *Spring Web:* Para a criação de endpoints RESTful.
    * *Spring Security:* Para segurança e autenticação.
    * *Spring Data JPA / Hibernate:* Para persistência de dados e mapeamento objeto-relacional.
    <p align="">
      <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white" alt="Hibernate Logo"/>
    </p>

### 🗄️ Banco de Dados 

* *PostgreSQL*
    <p>
    <img src="https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=fff&style=for-the-badge" alt="PostgresSQL Logo"/>
    </p>

### Build e Dependências  
* *Maven*
    <p align="">
      <img src="https://img.shields.io/badge/Maven-E6522C?logo=maven&logoColor=fff&style=for-the-badge" alt="Prometheus Logo" />
    </p>

### Autenticação

* *JSON Web Tokens (JWT)*
    <p align="">
      <img src="https://img.shields.io/badge/jwt-F46800?logo=jwt&logoColor=fff&style=for-the-badge" alt="Grafana Logo" />
    </p>

### Documentação
* *SpringDoc (Swagger UI)*
    <p align="">
      <img src="https://img.shields.io/badge/Swagger-85EA2D?logo=swagger&logoColor=000&style=for-the-badge" alt="Swagger Logo" />
    </p>
### Utilitários    
* *Lombok:* Para reduzir código boilerplate.
* *ModelMapper:* Para conversão entre DTOs e Entidades.

### Containerização
* *Docker*

---
## Configuração e Execução 🚀

### 1. Banco de Dados

1.  Certifique-se de que o PostgreSQL esteja instalado e rodando.
2.  Crie um banco de dados com o nome tokseg_armario.
3.  No arquivo src/main/resources/application.properties, ajuste as credenciais do banco de dados (spring.datasource.username e spring.datasource.password) se forem diferentes das padrões (postgres/123456).

### 2. Configuração de E-mail

1.  No arquivo src/main/resources/application.properties, configure as propriedades spring.mail.* com as credenciais do seu servidor SMTP (Gmail, Outlook, etc.). Para Gmail e Outlook, é recomendado usar uma **"Senha de App"** se a verificação em duas etapas estiver ativa.

### 3. Executando a Aplicação Localmente

1.  Abra o projeto em uma IDE como IntelliJ ou VS Code.
2.  Execute a classe principal ArmarioInteligenteApplication.java.
3.  A aplicação estará disponível em http://localhost:8080.

### 4. Executando com Docker 🐳

1.  Na raiz do projeto, construa a imagem Docker:
    bash
    docker build -t armario-inteligente .
    
   

2.  Execute o container. A flag --network="host" é a forma mais simples no Linux de permitir que o container acesse o PostgreSQL rodando em localhost na sua máquina.
    bash
    docker run -p 8080:8080 --network="host" --name armario-inteligente-container armario-inteligente
    
   

    * *Para Docker Desktop (Mac/Windows)*, use host.docker.internal na URL do banco de dados via variável de ambiente:
        bash
        docker run -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/tokseg_armario --name armario-inteligente-container armario-inteligente
        
    * Para ver os logs do container em tempo real: docker logs -f armario-intelignte-container

---
## Uso e Teste da API

* *Documentação Interativa:* A lista completa de endpoints, seus parâmetros e DTOs está disponível via Swagger UI em: http://localhost:8080/swagger-ui.html.
* *Fluxo de Teste Básico:*
    1.  Use o endpoint POST /api/auth/login para obter um token JWT.
    2.  Adicione este token ao header Authorization de requisições subsequentes no formato Bearer <SEU_TOKEN>.
    3.  Interaja com os endpoints protegidos para testar as funcionalidades de entrega, retirada, etc.
    4.  Observe o console da aplicação Spring Boot para ver os PINs "enviados" por e-mail.
---

## 🤝 Contribuição  

1. Faça um fork deste repositório
2. Crie uma branch para sua feature ou correção (git checkout -b minha-feature)
3. Faça o commit com suas alterações (git commit -m 'feat: minha nova feature')
4. Faça push para a branch (git push origin minha-feature)
5. Abra um Pull Request

---

## 📞 Contato
Dúvidas, sugestões ou problemas? <br>
Entre em contato com a equipe TokSeg - LockX!

<p align="center">
<b>LockX by TokSeg</b>
</p>  
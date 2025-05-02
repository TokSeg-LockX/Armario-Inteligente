# Sistema de Armário Inteligente TokSeg

## Sobre o Projeto

O **Sistema de Armário Inteligente TokSeg** é uma solução inovadora para o recebimento, armazenamento seguro e retirada de encomendas em condomínios residenciais, empresariais e industriais. O projeto automatiza o processo de entrega e retirada de encomendas, proporcionando mais segurança, praticidade e rastreabilidade tanto para moradores quanto para administradores e entregadores.

A solução integra-se ao aplicativo TokSeg, permitindo notificações em tempo real, controle de acesso por QR Code, PIN ou biometria, e um painel administrativo completo para gestão dos armários e geração de relatórios.

---

```

O projeto ainda está em construção, ou seja, as tecnologias e
funcionalidades podem ser moldadas de acordo com as necessidades.

```

## Funcionalidades Principais

- Cadastro e autenticação de usuários (morador, entregador, porteiro, administrador)
- Gestão de armários e compartimentos (status: disponível, ocupado, manutenção, reservado)
- Registro e notificação de entregas
- Geração de códigos de acesso seguros
- Retirada autônoma de encomendas
- Histórico detalhado de operações e relatórios gerenciais
- Autorização para retirada por terceiros
- Integração com o app TokSeg 

---

## Tecnologias Utilizadas

### Backend (em construção)

- Java 17
- Spring Boot 3
- Spring Security (autenticação e autorização via JWT)
- **Hibernate ORM** (mapeamento objeto-relacional)
- Spring Data JPA (repositórios e queries)
- Flyway (controle de versões do banco de dados)
- Swagger/OpenAPI (documentação da API)

### Banco de Dados (em construção)

- PostgreSQL (produção)
- MySQL (ambiente de testes e prototipagem)

### Notificações (em construção)

- Spring Mail (envio de emails)
- Push Notifications (integração com app TokSeg)

### Monitoramento e Logs (em construção)

- Prometheus + Grafana (monitoramento)
- ELK Stack (logs centralizados)
- Spring Sleuth + Zipkin (rastreamento de requisições)

---

## Estrutura do Projeto (em construção)
```
/backend # Código fonte Java/Spring Boot
/database # Scripts SQL para criação e migração do banco de dados
/docs # Documentação técnica e diagramas

```
---

## Como Contribuir

1. Faça um fork deste repositório
2. Crie uma branch para sua feature ou correção (`git checkout -b minha-feature`)
3. Commit suas alterações (`git commit -m 'feat: minha nova feature'`)
4. Faça push para a branch (`git push origin minha-feature`)
5. Abra um Pull Request

---

## Contato

Dúvidas, sugestões ou problemas?  
Entre em contato com a equipe TokSeg - LockX!

---

**TokSeg - Soluções em Portaria Inteligente e Monitoramento**

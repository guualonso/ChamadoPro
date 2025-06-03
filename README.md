# 🛠️ ChamadoPro (CHP)

Sistema desktop para gerenciamento de chamados técnicos, desenvolvido com **Java + JavaFX**, utilizando arquitetura em camadas, autenticação por tipo de usuário e integração com banco de dados **PostgreSQL**.

> 💡 Também pode ser executado com dados simulados em memória (sem banco).

---

## 🚀 Tecnologias utilizadas

- Java 17+
- JavaFX 17
- FXML
- JDBC
- PostgreSQL
- Maven

---

## 🗂️ Estrutura do Projeto

```
src/
├── config/         ← Configuração de banco de dados
├── controller/     ← Lógica das telas (JavaFX Controllers)
├── dao/            ← Camada de acesso a dados (Data Access Object)
├── model/          ← Classes de domínio: Chamado, Usuario, Enums etc.
├── resources/
│   ├── fxml/       ← Telas em FXML
│   ├── css/        ← Estilo visual
│   └── images/     ← Imagens da interface (ex: logo)
```

---

## 👥 Usuários para teste (modo em memória)

| Perfil   | E-mail             | Senha        |
|----------|--------------------|--------------|
| Admin    | admin@chp.com      | admin123     |
| Técnico  | tecnico@chp.com    | tecnico123   |
| Cliente  | cliente@chp.com    | cliente123   |

---

## 🔐 Funcionalidades por perfil

### 👨‍💼 Administrador
- Gerenciar usuários
- Atribuir técnicos aos chamados
- Visualizar todos os chamados

### 🧑‍🔧 Técnico
- Visualizar chamados atribuídos
- Atualizar status dos chamados
- Adicionar comentários

### 👤 Cliente
- Abrir chamados
- Acompanhar andamento
- Avaliar atendimento

---

## ▶️ Como executar o projeto

### ✅ Pré-requisitos

- JDK 17 instalado
- PostgreSQL (caso use banco)
- Maven instalado
- IDE (IntelliJ IDEA ou VSCode com suporte a JavaFX)

---

### ☑️ Configurando banco de dados

1. **Crie o arquivo** `src/main/resources/database.properties`:

```properties
db.url=jdbc:sua-url
db.user=seu-usuario
db.password=sua-senha
```

2. **Importe o script SQL** com a estrutura do banco (caso exista).

3. **Evite versionar esse arquivo**. Adicione ao `.gitignore`:

```
# Dados sensíveis
src/main/resources/database.properties
```

---

### ▶️ Executando

**Via Maven:**

```bash
mvn clean javafx:run
```

**Ou via IDE:**
- Defina a classe `Main` como ponto de entrada
- Certifique-se de que o JavaFX está corretamente configurado

---

## 💡 Modo de testes (sem banco)

Se não configurar o banco, o sistema funcionará com dados em memória (DAOs simulados). Ideal para testes rápidos e validação da interface.

---

## 🧪 Como testar

- Faça login com os usuários de teste
- Teste abertura de chamados, status, comentários e avaliações
- Troque de perfil para ver diferentes funcionalidades

---

## 🎯 Futuras melhorias

- Criptografia de senhas (ex: BCrypt)
- Geração de relatórios PDF
- Upload de arquivos (ex: prints de erro)
- Filtros por data e prioridade

---

## 🧑‍💻 Autor

**Gustavo Alonso**

---

## 📄 Licença

Este projeto é open-source e pode ser utilizado para fins acadêmicos ou pessoais.  
**Licença: MIT**
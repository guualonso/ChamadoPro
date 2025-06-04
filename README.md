
# 🛠️ ChamadoPro (CHP)

Sistema desktop para gerenciamento de chamados técnicos, desenvolvido com **Java + JavaFX**, utilizando arquitetura MVC em camadas, autenticação por tipo de usuário e persistência com banco de dados **PostgreSQL**.

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
├── model/          ← Classes de domínio: Chamado, Usuario, Comentário, Enums etc.
├── service/        ← Camada de regras de negócio
├── resources/
│   ├── fxml/       ← Telas em FXML
│   ├── css/        ← Estilo visual (JavaFX CSS)
│   └── images/     ← Ícones e logos da interface
```

---

## 👥 Usuários de teste

| Perfil   | E-mail             | Senha        |
|----------|--------------------|--------------|
| Admin    | admin@chp.com      | admin123     |
| Técnico  | tecnico@chp.com    | tecnico123   |
| Cliente  | cliente@chp.com    | cliente123   |

---

## 🔐 Funcionalidades por perfil

### 👨‍💼 Administrador
- Cadastro e gerenciamento de usuários
- Atribuição de técnicos aos chamados
- Visualização de todos os chamados
- Visualização da média de avaliações

### 🧑‍🔧 Técnico
- Visualizar chamados atribuídos
- Atualizar status dos chamados
- Adicionar comentários

### 👤 Cliente
- Abrir chamados
- Acompanhar chamados abertos
- Avaliar atendimento após resolução
- Comentar no chamado

---

## ▶️ Como executar o projeto

### ✅ Pré-requisitos

- JDK 17 ou superior
- PostgreSQL instalado e configurado
- Maven instalado
- IDE compatível (ex: IntelliJ IDEA, VS Code com JavaFX)

---

### ☑️ Configuração do banco de dados

1. **Crie o arquivo de propriedades:**

```properties
# src/main/resources/database.properties
db.url=jdbc:postgresql://localhost:5432/seubanco
db.user=seu_usuario
db.password=sua_senha
```

2. **Execute o script SQL de criação de tabelas** (caso tenha um).

3. **Reinicie a sequence de IDs após limpar dados (opcional):**

```sql
DELETE FROM comentarios;
DELETE FROM chamados;
ALTER SEQUENCE chamados_id_seq RESTART WITH 1;
```

---

### ▶️ Executando a aplicação

**Via terminal (Maven):**

```bash
mvn clean javafx:run
```

**Via IDE:**
- Defina a classe `Main` como ponto de entrada
- Garanta que o JavaFX está configurado corretamente nas opções de execução

---

## 🧪 Testes

- Faça login com um dos usuários de teste
- Teste as ações por perfil (abrir, comentar, avaliar, acompanhar, etc.)
- Alterne entre perfis para validar os controles de acesso

---

## 🎯 Futuras melhorias

- Criptografia de senhas com BCrypt
- Upload de arquivos (ex: prints de erro)
- Exportação de relatórios em PDF
- Filtro de chamados por data ou prioridade
- Paginação para grande volume de dados

---

## 🧑‍💻 Autor

[**Gustavo Alonso**](https://github.com/guualonso)

---

## 📄 Licença

Este projeto é open-source e pode ser utilizado para fins acadêmicos ou pessoais.  
**Licença: MIT**

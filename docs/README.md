# Solda-DORES

## Sobre o Projeto e Contexto
Este software é um projeto escolar desenvolvido em **Java**, como requisito para a conclusão do **2° módulo do curso técnico em Informática**.

Do ponto de vista acadêmico, o principal objetivo é avaliar nossos conhecimentos em:
- Programação orientada a objetos
- Lógica de programação
- Modelagem e implementação de banco de dados
- Desenvolvimento front-end
- Produção de documentação técnica

O software também busca auxiliar na **gestão de serviços de soldagem** para o nosso cliente real. Apesar de suas necessidades ultrapassarem o escopo exigido pelo projeto escolar, acreditamos que esta base já pode ajudá-lo minimamente na organização de seus atendimentos e registros.

- **Versão:** v2.0
- **Data de criação:** 13/10/2025
---

## Equipe de Desenvolvimento
O projeto foi desenvolvido pelos seguintes integrantes:
- Alex  
- Hugo  
- Rafael Moreira  
- Rafael Silva  
- Rafhael Muzzi  
---

## Estrutura do Projeto
O workspace está organizado da seguinte forma
- **`src/`** — código-fonte Java (`.java`)
- **`src/util/`** — arquivos utilitarios, como a classe de  Gradiente.
- **`lib/`** — dependências externas (`.jar`), como o conector MySQL
- **`docs/`** — documentação geral do projeto, incluindo:
  - entrevista em áudio
  - transcrição
  - manual do usuário
  - contrato de desenvolvimento
  - licenças
  - script do banco de dados
---

## Licença
Este projeto é licenciado sob a **Licença Apache 2.0**:  
[LICENSE](LICENSE)

Também está disponível uma **tradução não oficial em português**:  
[LICENSE_pt-br.md](LICENSE_pt-br.md)
---

## Como Executar o Projeto
Antes de iniciar, você deve ter instalado:
- **Java (JDK)**  
- **MySQL Server**

### Passo 1 — Criar o banco de dados
1. Abra seu MySQL.  
2. Execute o arquivo **`soldadores.sql`**.  
   Isso criará o banco de dados e suas tabelas.

### Passo 2 — Executar o programa
1. Abra o projeto em sua IDE.
2. Certifique-se de que os arquivos `.jar` da pasta `lib/` estão adicionados ao classpath.
3. Abra o arquivo principal em `src/` — **`Main.java`**.
4. Execute (“Run”).
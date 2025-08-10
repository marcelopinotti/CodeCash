# CodeCash

CodeCash: Simulação de Sistema Bancário Backend em Java
Bem-vindo ao CodeCash, um projeto de backend desenvolvido em Java e gerenciado com Gradle, que simula as operações fundamentais de um sistema bancário.

Este projeto serve como um exercício prático para aprimorar a compreensão de conceitos de programação orientada a objetos e o uso de ferramentas de construção (build) em Java.

## 📚 Sobre o Projeto
O CodeCash é uma aplicação de linha de comando que demonstra a estrutura básica de um sistema bancário. Ele permite realizar operações essenciais, como a criação de contas, gestão de investimentos e movimentações financeiras, através de uma interface textual simples.

## 🚀 Tecnologias Utilizadas
Este projeto utiliza as seguintes tecnologias principais:

<p align="center">
<img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Logo Java">
<img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white" alt="Logo Gradle">
<img src="https://img.shields.io/badge/Lombok-red?style=for-the-badge&logo=lombok&logoColor=white" alt="Logo Lombok">
</p>

Java: A linguagem de programação utilizada para construir a lógica do sistema bancário.

Gradle: A ferramenta de automação de build responsável por compilar o projeto e gerenciar dependências como o Lombok.

Lombok: Uma biblioteca Java que reduz a quantidade de código boilerplate, como getters, setters e construtores.

## ✨ Funcionalidades Principais
O CodeCash oferece as seguintes funcionalidades através de uma interface de linha de comando:

  Criação de Contas: Permite registrar novas contas no sistema.

  Gestão de Investimentos: Facilita a criação e manipulação de carteiras de investimento.

  Operações Financeiras: Realiza depósitos, saques e transferências entre contas existentes.

  Listagem de Dados: Permite visualizar informações sobre contas e investimentos.

## 📂 Estrutura do Projeto

src/main/java/: Contém o código fonte principal do projeto em Java.

Main.java: Ponto de entrada da aplicação e interface de linha de comando.

services/: Classes que implementam a lógica de negócio (ex: BankService).

repository/: Classes responsáveis pelo armazenamento e recuperação de dados em memória (neste caso).

model/: Classes que representam as entidades do sistema (ex: Account, Investment).

build.gradle.kts: O arquivo de configuração do Gradle.

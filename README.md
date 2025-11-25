# Jogo da Forca Educativo 🔤🤠

Projeto integrado para as disciplinas de **Interface Homem-Computador** e **Programação para Dispositivos Móveis**. Este aplicativo é um jogo da forca desenvolvido para Android, com foco educativo no aprendizado da ortografia (Português/Inglês) e personalização de experiência.

## 📋 Sobre o Projeto

O objetivo deste aplicativo é oferecer uma maneira divertida para jogadores exercitarem a ortografia e o vocabulário. O jogo apresenta palavras ocultas e desafia o jogador a completá-las antes que o boneco da forca seja completado ou o tempo acabe.

### ✨ Funcionalidades Implementadas

* **Menu Principal:** Acesso rápido ao Jogo, Ranking, Configurações e Tutorial.
* **Identificação do Jogador:** Solicita o nome do usuário antes de iniciar a partida para registro no ranking.
* **Jogo Interativo:**
    * **Bilingue:** Suporte para palavras em Português (ex: XÍCARA, NASCER) e Inglês (ex: CUP, BIRTH).
    * **Cronômetro Opcional:** Modo de jogo com tempo limite de 60 segundos.
    * **Feedback Visual:** Indicação clara de acertos (✓) e erros (✗).
    * **Acessibilidade:** Ajuste dinâmico do tamanho da fonte (Pequeno, Médio, Grande).
* **Persistência de Dados Híbrida:**
    * **SQLite:** Utilizado para armazenar o histórico de pontuações e gerar o Ranking (Top 5).
    * **SharedPreferences:** Utilizado para salvar as preferências do usuário (Idioma, Som, Tema, Tamanho da Fonte).
* **Ranking:** Exibe as 5 melhores pontuações vindas do banco de dados.
* **Temas:** Suporte completo a **Modo Claro** e **Modo Escuro** (Dark Mode).

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java
* **Layout:** XML
* **IDE:** Android Studio
* **Banco de Dados:** SQLite (`SQLiteOpenHelper`)
* **Preferências:** SharedPreferences
* **Internacionalização:** `Locale` e `Configuration` para troca dinâmica de idioma (PT/EN).
* **SDK Mínimo:** API 30 (Android 11.0)
* **SDK Alvo:** API 36

## 📱 Estrutura do Projeto

O aplicativo é composto por cinco atividades principais e uma classe auxiliar de banco de dados:

1.  **`MainActivity`**: Tela inicial que gerencia a navegação e inicializa a configuração de localidade (idioma).
2.  **`ConfigActivity`**: Permite ao usuário alterar:
    * Idioma (Português/Inglês).
    * Modo Escuro (Ativar/Desativar).
    * Cronômetro (Com/Sem tempo).
    * Tamanho da Fonte (Seekbar).
3.  **`GameActivity`**: Contém toda a lógica do jogo, temporizador (`CountDownTimer`), sorteio de palavras baseado no idioma escolhido e lógica de vitória/derrota.
4.  **`RankingActivity`**: Recupera e lista o "Top 5" jogadores do banco de dados SQLite.
5.  **`TutorialActivity`**: Tela informativa sobre como jogar.
6.  **`DatabaseHelper`**: Classe responsável por criar a tabela `ranking` e gerenciar as operações de *Insert* e *Select* no SQLite.

## 🚀 Como Executar o Projeto

1.  **Pré-requisitos:** Certifique-se de ter o [Android Studio](https://developer.android.com/studio) instalado.
2.  **Clonar/Baixar:** Faça o download deste repositório ou descompacte a pasta do projeto.
3.  **Abrir:** No Android Studio, vá em `File > Open` e selecione a pasta raiz do projeto (`Forca`).
4.  **Sincronizar:** Aguarde o Gradle sincronizar as dependências.
5.  **Rodar:** Conecte um dispositivo físico ou inicie um emulador (API 30+) e clique no botão **Play (▶)**.

## 📝 Critérios de Avaliação Atendidos

Este projeto atende aos requisitos acadêmicos avançados:

* [x] **Ícone:** Ícone do aplicativo configurado (`mipmap`).
* [x] **Navegação Completa:** Uso de `Intents` explícitas e passagem de parâmetros (`PutExtra`).
* [x] **Interface de Usuário:** Layouts organizados (`LinearLayout`, `ScrollView` se necessário).
* [x] **Persistência de Configurações:** Uso de `SharedPreferences` para manter o estado do app (Tema, Idioma, etc).
* [x] **Persistência de Dados Complexos:** Uso de **SQLite** para o Ranking.
* [x] **Recursos de Hardware/Sistema:** Uso de `Locale` para idioma e `CountDownTimer`.
* [x] **Organização:** Código modularizado (Helper de Banco de Dados separado das Activities).

## 👥 Autores

* **[Tuigg Barcelos]**


---
*Desenvolvido como requisito parcial para a aprovação na disciplina de Programação para Dispositivos Móveis - 2025.*

# Jogo da Forca Educativo 🔤🤠

Projeto integrado para as disciplinas de **Interface Homem-Computador** e **Programação para Dispositivos Móveis**. Este aplicativo é um jogo da forca desenvolvido para Android, com foco educativo no aprendizado da ortografia da língua portuguesa.

## 📋 Sobre o Projeto

O objetivo deste aplicativo é oferecer uma maneira divertida para crianças em fase de alfabetização ou ensino básico praticarem a ortografia. O jogo apresenta palavras que geralmente geram dúvidas (como o uso de X ou CH, S ou SS) e desafia o jogador a completá-las antes que o boneco da forca seja completado.

**Funcionalidades Principais:**
* **Menu Principal:** Navegação intuitiva para iniciar o jogo, ver o ranking ou acessar configurações.
* **Jogo Interativo:**
    * Sorteio aleatório de palavras (ex: XÍCARA, CHAVE, PÁSSARO).
    * Feedback visual imediato (verde para acertos, vermelho para erros).
    * Ilustração progressiva da forca (7 estágios de erro).
    * Sistema de pontuação (+100 por vitória, +10 por letra, -5 por erro).
* **Ranking (Persistência de Dados):** Armazenamento da pontuação recorde do jogador utilizando `SharedPreferences`.
* **Interface Amigável:** Layouts limpos e botões acessíveis.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java
* **Layout:** XML
* **IDE:** Android Studio
* **Persistência:** SharedPreferences
* **SDK Mínimo:** API 30 (Android 11.0)
* **SDK Alvo:** API 36

## 📱 Estrutura do Projeto

O aplicativo é composto por três atividades principais:

1.  **`MainActivity`**: Tela inicial contendo o menu de navegação com opções para "Jogar", "Ranking" e "Configurações".
2.  **`GameActivity`**: Contém toda a lógica do jogo, incluindo o processamento da entrada do utilizador, atualização da imagem da forca e contagem de pontos.
3.  **`RankingActivity`**: Exibe a melhor pontuação (recorde) alcançada pelo jogador, recuperando o dado salvo localmente.

## 🚀 Como Executar o Projeto

1.  **Pré-requisitos:** Certifique-se de ter o [Android Studio](https://developer.android.com/studio) instalado.
2.  **Clonar/Baixar:** Faça o download deste repositório ou descompacte a pasta do projeto.
3.  **Abrir:** No Android Studio, vá em `File > Open` e selecione a pasta raiz do projeto (`Forca`).
4.  **Sincronizar:** Aguarde o Gradle sincronizar as dependências (clique no ícone do "Elefante" se necessário).
5.  **Rodar:** Conecte um dispositivo físico ou inicie um emulador (API 30+) e clique no botão **Play (▶)**.

## 📝 Critérios de Avaliação Atendidos

Este projeto foi desenvolvido para atender aos seguintes requisitos académicos:
* [x] **Ícone:** Ícone do aplicativo configurado (`mipmap`).
* [x] **Navegação Completa:** Uso de `Intents` para transição entre telas.
* [x] **Interface de Usuário:** Uso de arquivos XML para layouts (`activity_main`, `activity_game`, `activity_ranking`).
* [x] **Lógica do Jogo:** Estruturas de controle para verificar letras e definir vitória/derrota.
* [x] **Persistência de Dados:** Uso de `SharedPreferences` para salvar o recorde.
* [x] **Organização:** Recursos de strings (`strings.xml`) e imagens (`drawable`) separados.

## 👥 Autores

* **[Tuigg Barcelos]**
  

---
*Desenvolvido como requisito parcial para a aprovação na disciplina de Programação para Dispositivos Móveis - 2025.*

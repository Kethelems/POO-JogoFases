# 🦄 Unicórnio: Rumo ao Arco-Íris

## ✨ Descrição
Projeto em Java que simula um jogo 2D onde:
- Você controla um **unicórnio jogador**
- Derrota **inimigos** com seu tiro arco-íris
- Avança até o **objetivo** ao final de cada fase
- **Partículas de glitter** aparecem ao derrotar inimigos ou completar fases

## 📝 Classes principais

| Classe              | Descrição breve |
|---------------------|-----------------|
| **Jogo**            | Classe principal que inicia a janela e executa o loop principal |
| **Fase**            | Contém e gerencia jogador, inimigos, objetivo e partículas |
| **Jogador**         | Unicórnio controlado pelo usuário, capaz de disparar o Raio Arco-Íris como tiro |
| **Inimigo**         | Personagem com movimento automático horizontal |
| **Objetivo**        | Marca o ponto fixo que representa a meta da fase |
| **Personagem**      | Classe abstrata base para jogador, inimigos e objetivo, contendo posição, velocidade e vida |
| **ParticulasGlitter** | Efeito visual que surge ao derrotar inimigos ou ao passar de fase |
| **RaioArcoIris**    | Tiro disparado pelo jogador, representado como um raio arco-íris que se move horizontalmente |

## 🎮 Controles
- **Setas direcionais**: movimentam o unicórnio
  - Pressionar duas ao mesmo tempo permite andar em diagonal
- **Tecla de disparo (caso implementada)**: dispara o Raio Arco-Íris como tiro contra inimigos

## ⚙️ Requisitos
- Java JDK instalado
- IDE ou compilador Java

## 🚀 Execução
Compile todos os arquivos e execute a classe **Jogo.java** para iniciar:

```bash
javac *.java
java Jogo

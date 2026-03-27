# Streaming de Música

Aplicação em Java (projeto acadêmico) que simula um pequeno sistema de streaming: cadastro de músicas a partir de arquivos locais (MP3/WAV), busca, reprodução e organização em playlists com uso de **Programação Orientada a Objetos** (`Musica`, `Playlist`, `Usuario`).

## Requisitos

- [JDK 11](https://adoptium.net/) ou superior
- [Apache Maven](https://maven.apache.org/) 3.6+
- Conexão com a internet na **primeira** execução do `mvn` (download de dependências no Maven Central)

## Estrutura principal

```
src/main/java/
├── Main.java                 # Menu e fluxo do programa
├── model/
│   ├── Musica.java           # Dados da faixa; tocar(); validarFormatoAudio()
│   ├── Playlist.java         # Lista de músicas; mostrarMusicas(); tocarTudo()
│   └── Usuario.java          # Perfil; criarPlaylist(); adicionarNaPlaylist()
├── service/
│   ├── MusicaService.java    # Catálogo em memória (CRUD simplificado)
│   ├── LocalAudioService.java
│   └── ...
└── util/
    └── Validador.java
```

Dependência de reprodução MP3: **JLayer** (`jlayer`), declarada no `pom.xml`.

## Como compilar

Na raiz do projeto (onde está o `pom.xml`):

```bash
mvn compile
```

Para gerar o JAR e copiar dependências para `target/dependency/`:

```bash
mvn package
```

## Como executar

### Opção recomendada (Maven)

O `pom.xml` já define a classe principal `Main` no plugin `exec-maven-plugin`. Basta:

```bash
mvn exec:java
```

### PowerShell e parâmetros `-D`

No PowerShell, argumentos como `-Dexec.mainClass=Main` às vezes são interpretados de forma errada. Use aspas:

```powershell
mvn "exec:java" "-Dexec.mainClass=Main"
```

Ou simplesmente `mvn exec:java`, pois a main class já está configurada.

### Executar sem `exec` (após `mvn package`)

O JAR gerado **não** inclui as bibliotecas dentro de um único “fat JAR”; é preciso incluir as JARs de `target/dependency` no classpath. No Windows (PowerShell, a partir da raiz do projeto):

```powershell
java -cp "target\musica-streaming-1.0-SNAPSHOT.jar;target\dependency\*" Main
```

### IDE

Abra o projeto como projeto Maven, localize `src/main/java/Main.java` e execute com **Run**.

## Uso do menu

| Opção | Função |
|------|--------|
| 1 | Cadastrar música (seleção de arquivo + título, artista, gênero; duração automática para WAV quando possível) |
| 2 | Listar músicas do catálogo |
| 3 / 4 | Buscar por título ou artista |
| 5 | Remover música do catálogo |
| 6 | Reproduzir música pelo índice |
| 7 | Importar todas as MP3/WAV de uma pasta |
| 8 | Demo rápida de POO (músicas de exemplo + método estático) |
| 9 | Gerenciar playlists: usuário, criar playlist, adicionar músicas **já cadastradas** no catálogo, listar e “tocar” playlist (saída no console) |
| 0 | Sair |

Fluxo típico para testar playlists: **1** ou **7** → cadastrar/importar → **9** → criar usuário (na primeira vez) → criar playlist → adicionar música pelo índice do catálogo.

## Conceitos de POO usados

- **Classe**: molde (`Usuario`, `Playlist`, `Musica`).
- **Objeto**: instância criada com `new`.
- **Método de instância**: precisa de um objeto (ex.: `musica.tocar()`).
- **Método estático**: chamado na classe (ex.: `Musica.validarFormatoAudio(".mp3")`).

## Problemas comuns

### Maven: “Este host não é conhecido (repo.maven.apache.org)”

Falha de **DNS/rede**: o computador não resolve `repo.maven.apache.org`. Corrija DNS (ex.: 8.8.8.8 / 1.1.1.1), execute `ipconfig /flushdns` ou use uma rede sem bloqueio. Em redes corporativas pode ser necessário configurar proxy em `%USERPROFILE%\.m2\settings.xml`.

### Reprodução de áudio

- **WAV**: usa API padrão do Java (`javax.sound.sampled`).
- **MP3**: usa JLayer; em caso de erro, verifique se o arquivo existe e se o formato é suportado.

## Licença / uso acadêmico

Projeto voltado a estudos; ajuste autores e requisitos da disciplina conforme orientação do professor.

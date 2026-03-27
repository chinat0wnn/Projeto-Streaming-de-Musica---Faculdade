# Java Console Music Streaming Platform - Implementation Plan

## Decisions
- **Build tool:** Maven (`pom.xml`)
- **MP3 playback:** JLayer (`javazoom:jlayer:1.0.1`) via Maven dependency
- **WAV playback:** `javax.sound.sampled` (built-in JDK)
- **Java version:** 11+

## Maven Project Structure

```
pom.xml
src/main/java/
├── Main.java
├── model/
│   └── Musica.java
├── service/
│   ├── MusicaService.java
│   ├── FonteAudioService.java
│   └── LocalAudioService.java
└── util/
    └── Validador.java
```

## Files to Create (in order)

### 1. `pom.xml`
- groupId: `com.streaming`, artifactId: `musica-streaming`, version: `1.0-SNAPSHOT`
- Java 11 source/target via `maven-compiler-plugin`
- Dependency: `javazoom:jlayer:1.0.1`
- `maven-jar-plugin` with Main-Class manifest entry for `Main`

### 2. `src/main/java/model/Musica.java`
- Fields: `String titulo`, `String artista`, `String genero`, `double duracao`, `String caminhoArquivo`
- Constructor with all fields
- Getters and setters for all fields
- `toString()` returning formatted string with title, artist, genre, duration (mm:ss), and file path

### 3. `src/main/java/util/Validador.java`
- All static methods, no instantiation
- `validarString(String valor, String nomeCampo)` — throws `IllegalArgumentException` if null/blank
- `validarDuracao(double duracao)` — throws if <= 0
- `validarCaminhoArquivo(String caminho)` — throws if null/blank, file doesn't exist, or extension is not `.wav`/`.mp3`
- `validarDuplicata(List<Musica> lista, String titulo, String artista)` — throws if same titulo+artista exists (case-insensitive)

### 4. `src/main/java/service/FonteAudioService.java`
- Interface with single method: `void reproduzir(String caminhoArquivo) throws Exception`

### 5. `src/main/java/service/LocalAudioService.java`
- Implements `FonteAudioService`
- `reproduzir(String caminhoArquivo)`:
  - Detects extension (`.wav` / `.mp3`)
  - `.wav`: uses `javax.sound.sampled.AudioSystem` → `Clip` → play, waits for clip to finish via `CountDownLatch` or `Thread.sleep(clip.getMicrosecondLength() / 1000)`, allows interruption with Enter key
  - `.mp3`: uses `javazoom.jl.player.Player` to play the file
  - Catches `FileNotFoundException`, `UnsupportedAudioFileException`, and general exceptions with pt-BR messages

### 6. `src/main/java/service/MusicaService.java`
- Private `ArrayList<Musica> musicas`
- `cadastrar(Musica m)`: validates all fields via `Validador`, checks duplicate, adds to list
- `listarTodas()`: returns unmodifiable copy of the list
- `buscarPorTitulo(String t)`: case-insensitive `contains` partial match, returns `List<Musica>`
- `buscarPorArtista(String a)`: case-insensitive `contains` partial match, returns `List<Musica>`
- `remover(int index)`: validates index bounds, removes and returns the removed `Musica`

### 7. `src/main/java/Main.java`
- Creates `MusicaService` and `LocalAudioService` instances
- `Scanner` for user input in a `while(true)` loop
- Box-drawn menu using `+`, `-`, `|` characters (square corners):
  ```
  +-------------------------------+
  |   STREAMING DE MUSICA v1.0    |
  +-------------------------------+
  | [1] Cadastrar Musica          |
  | [2] Listar Musicas            |
  | [3] Buscar por Titulo         |
  | [4] Buscar por Artista        |
  | [5] Remover Musica            |
  | [6] Reproduzir Musica         |
  | [0] Sair                      |
  +-------------------------------+
  ```
- Each option wrapped in try/catch for `InputMismatchException`, `IllegalArgumentException`, `IndexOutOfBoundsException`
- Option 1 (Cadastrar): prompts titulo, artista, genero, duracao, caminhoArquivo — creates `Musica` and calls `cadastrar`
- Option 2 (Listar): calls `listarTodas()`, prints numbered list or "Nenhuma musica cadastrada"
- Option 3 (Buscar titulo): prompts search term, prints results or "Nenhuma musica encontrada"
- Option 4 (Buscar artista): same as 3 but by artist
- Option 5 (Remover): lists all, prompts index, calls `remover`
- Option 6 (Reproduzir): lists all, prompts index, calls `reproduzir` with the selected music's path
- Option 0 (Sair): prints farewell, `System.exit(0)`
- All messages in pt-BR

## Verification
- Compile with `mvn compile`
- Package with `mvn package`
- Run with `java -cp target/classes;target/dependency/* Main` (or via `mvn exec:java`)

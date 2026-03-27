# Plano: Criação de Classes e Atributos — Streaming de Música

## Contexto

A aplicação atualmente possui apenas uma classe de modelo (`Musica`), sem persistência de dados, sem usuários e sem playlists. O objetivo é expandir o domínio da aplicação com novas classes e atributos que reflitam um sistema de streaming real, mantendo a consistência com o padrão já adotado (pacote `model/`, encapsulamento com getters/setters, construtores, `toString()`).

---

## Classes a Criar

### 1. `model/Usuario.java`
Representa quem usa o sistema.

**Atributos:**
```
- int id
- String nome
- String email
- String senha
- LocalDate dataCadastro
```

**Por que:** Permite associar playlists e histórico a um usuário específico. Base para qualquer funcionalidade de autenticação futura.

---

### 2. `model/Artista.java`
Representa o artista/banda de forma independente da música.

**Atributos:**
```
- int id
- String nome
- String biografia
- String genero
```

**Por que:** Atualmente `artista` é apenas uma `String` dentro de `Musica`. Criar uma classe `Artista` permite buscar todas as músicas de um artista, exibir sua bio, e associar múltiplos álbuns.

---

### 3. `model/Album.java`
Agrupa músicas de um mesmo artista em uma obra.

**Atributos:**
```
- int id
- String titulo
- Artista artista
- int anoLancamento
- List<Musica> faixas
```

**Por que:** Representa a relação natural entre artista → álbum → músicas, algo que já existe na realidade e que o sistema atual ignora.

---

### 4. `model/Playlist.java`
Coleção de músicas criada pelo usuário.

**Atributos:**
```
- int id
- String nome
- String descricao
- Usuario dono
- List<Musica> musicas
- LocalDateTime dataCriacao
```

**Por que:** É um conceito central em qualquer streaming. Permite ao usuário organizar músicas de forma personalizada.

---

### 5. `model/HistoricoReproducao.java`
Registra cada vez que uma música é tocada.

**Atributos:**
```
- int id
- Usuario usuario
- Musica musica
- LocalDateTime dataHora
```

**Por que:** Possibilita funcionalidades como "tocadas recentemente", "mais ouvidas", e relatórios de uso.

---

## Impacto na Classe Existente `Musica`

A classe `Musica` pode receber dois atributos opcionais para integração com as novas classes:

```
- Album album         // referência ao álbum ao qual pertence (pode ser null)
- int numeroFaixa     // posição dentro do álbum
```

---

## Padrão a Seguir

Todas as novas classes devem seguir exatamente o padrão já adotado em `model/Musica.java`:
- Atributos `private`
- Construtor com todos os atributos obrigatórios
- Getters e setters para cada atributo
- `@Override toString()` formatado

---

## Serviços Correspondentes

Para cada nova classe de modelo, um serviço deve ser criado em `service/`:

| Classe        | Serviço               | Responsabilidade                         |
|---------------|-----------------------|------------------------------------------|
| `Usuario`     | `UsuarioService`      | Cadastro, login simples, busca           |
| `Artista`     | `ArtistaService`      | CRUD de artistas                         |
| `Album`       | `AlbumService`        | CRUD de álbuns, associar faixas          |
| `Playlist`    | `PlaylistService`     | Criar/editar playlists, add/remover músicas |
| `Historico`   | `HistoricoService`    | Registrar reproduções, listar recentes   |

---

## Arquivos a Criar

```
src/main/java/model/Usuario.java
src/main/java/model/Artista.java
src/main/java/model/Album.java
src/main/java/model/Playlist.java
src/main/java/model/HistoricoReproducao.java

src/main/java/service/UsuarioService.java
src/main/java/service/ArtistaService.java
src/main/java/service/AlbumService.java
src/main/java/service/PlaylistService.java
src/main/java/service/HistoricoService.java
```

## Arquivo a Modificar

```
src/main/java/model/Musica.java   → adicionar atributos `album` e `numeroFaixa`
```

---

## Verificação

Após implementação:
1. Compilar com `mvn compile` — nenhum erro esperado
2. Testar criação de instâncias das novas classes no `Main.java`
3. Testar associação `Playlist → Musica` e `Album → Artista → Musica`

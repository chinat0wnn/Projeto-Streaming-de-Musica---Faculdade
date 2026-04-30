package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe base da hierarquia de usuarios.
 * Subclasses: UsuarioFree, UsuarioPremium.
 */
public abstract class Usuario {

    private String nome;
    private String email;
    protected final List<Playlist> playlists = new ArrayList<>();

    // ── Construtor ────────────────────────────────────────────────

    /**
     * Construtor — valida nome e email ao criar o usuario.
     */
    public Usuario(String nome, String email) {
        setNome(nome);
        setEmail(email);
    }

    // ── Metodos para override pelas subclasses ────────────────────

    /**
     * Retorna o limite maximo de playlists que o usuario pode criar.
     * Subclasses sobrescrevem para definir seus limites.
     */
    public abstract int getMaxPlaylists();

    /**
     * Retorna o limite maximo de musicas por playlist.
     * Subclasses sobrescrevem para definir seus limites.
     */
    public abstract int getMaxMusicasPorPlaylist();

    /**
     * Retorna o tipo/plano do usuario como string.
     */
    public abstract String getTipoUsuario();

    /**
     * Exibe anuncio antes de reproduzir (se aplicavel).
     * Subclasses Free sobrescrevem para exibir anuncios.
     */
    public abstract void exibirAnuncio();

    // ── Getters e Setters (com validacao) ─────────────────────────

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do usuario nao pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("O email do usuario nao pode ser vazio.");
        }
        String emailTrimmed = email.trim();
        if (!emailTrimmed.contains("@") || !emailTrimmed.contains(".")) {
            throw new IllegalArgumentException("Formato de email invalido. Deve conter '@' e '.'");
        }
        this.email = emailTrimmed;
    }

    public List<Playlist> getPlaylists() {
        return Collections.unmodifiableList(playlists);
    }

    // ── Metodos de negocio ────────────────────────────────────────

    /**
     * Cria uma playlist, respeitando o limite do tipo de usuario.
     */
    public Playlist criarPlaylist(String nome) {
        if (playlists.size() >= getMaxPlaylists()) {
            throw new IllegalArgumentException(
                    "Limite de playlists atingido (" + getMaxPlaylists() + "). "
                    + "Faca upgrade para Premium para playlists ilimitadas!");
        }
        Playlist playlist = new Playlist(nome);
        playlists.add(playlist);
        return playlist;
    }

    /**
     * Cria uma playlist com descricao, respeitando o limite do tipo de usuario.
     */
    public Playlist criarPlaylist(String nome, String descricao) {
        if (playlists.size() >= getMaxPlaylists()) {
            throw new IllegalArgumentException(
                    "Limite de playlists atingido (" + getMaxPlaylists() + "). "
                    + "Faca upgrade para Premium para playlists ilimitadas!");
        }
        Playlist playlist = new Playlist(nome, descricao);
        playlists.add(playlist);
        return playlist;
    }

    /**
     * Adiciona musica em uma playlist, respeitando o limite por playlist.
     */
    public void adicionarNaPlaylist(String nomePlaylist, Musica musica) {
        if (nomePlaylist == null || nomePlaylist.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da playlist nao pode ser vazio.");
        }
        if (musica == null) {
            throw new IllegalArgumentException("Musica nao pode ser nula.");
        }

        Playlist playlist = encontrarPlaylist(nomePlaylist.trim());
        if (playlist == null) {
            throw new IllegalArgumentException("Playlist nao encontrada: " + nomePlaylist);
        }

        if (playlist.getListaMusicas().size() >= getMaxMusicasPorPlaylist()) {
            throw new IllegalArgumentException(
                    "Limite de musicas por playlist atingido (" + getMaxMusicasPorPlaylist() + "). "
                    + "Faca upgrade para Premium para musicas ilimitadas!");
        }

        playlist.adicionarMusica(musica);
    }

    protected Playlist encontrarPlaylist(String nomePlaylist) {
        for (Playlist p : playlists) {
            if (p.getNome().equalsIgnoreCase(nomePlaylist)) return p;
        }
        return null;
    }

    // ── toString ──────────────────────────────────────────────────

    @Override
    public String toString() {
        return String.format("[%s] %s | Email: %s | Playlists: %d",
                getTipoUsuario().toUpperCase(), nome, email, playlists.size());
    }
}

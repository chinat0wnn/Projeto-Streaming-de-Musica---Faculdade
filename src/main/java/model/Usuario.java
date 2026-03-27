package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Usuario {
    private final String nome;
    private final String email;
    private final List<Playlist> playlists = new ArrayList<>();

    public Usuario(String nome, String email) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do usuario nao pode ser vazio.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("O email do usuario nao pode ser vazio.");
        }
        this.nome = nome.trim();
        this.email = email.trim();
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public List<Playlist> getPlaylists() {
        return Collections.unmodifiableList(playlists);
    }

    public Playlist criarPlaylist(String nome) {
        Playlist playlist = new Playlist(nome);
        playlists.add(playlist);
        return playlist;
    }

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
        playlist.adicionarMusica(musica);
    }

    private Playlist encontrarPlaylist(String nomePlaylist) {
        for (Playlist p : playlists) {
            if (p.getNome().equalsIgnoreCase(nomePlaylist)) return p;
        }
        return null;
    }
}


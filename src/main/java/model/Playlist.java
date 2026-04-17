package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playlist {

    private String nome;
    private String descricao;
    private final List<Musica> listaMusicas = new ArrayList<>();

    // ── Construtores ──────────────────────────────────────────────

    /**
     * Construtor com apenas o nome da playlist.
     */
    public Playlist(String nome) {
        setNome(nome);
        this.descricao = "";
    }

    /**
     * Construtor sobrecarregado — permite definir nome e descricao.
     */
    public Playlist(String nome, String descricao) {
        setNome(nome);
        setDescricao(descricao);
    }

    // ── Getters e Setters (com validacao) ─────────────────────────

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da playlist nao pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao == null) {
            throw new IllegalArgumentException("A descricao nao pode ser nula.");
        }
        this.descricao = descricao.trim();
    }

    public List<Musica> getListaMusicas() {
        return Collections.unmodifiableList(listaMusicas);
    }

    // ── Metodos de negocio ────────────────────────────────────────

    public void adicionarMusica(Musica m) {
        if (m == null) {
            throw new IllegalArgumentException("Musica nao pode ser nula.");
        }
        listaMusicas.add(m);
    }

    public void mostrarMusicas() {
        System.out.println("--- Playlist: " + nome + " ---");
        if (!descricao.isEmpty()) {
            System.out.println("    " + descricao);
        }
        if (listaMusicas.isEmpty()) {
            System.out.println("(vazia)");
            return;
        }
        for (int i = 0; i < listaMusicas.size(); i++) {
            Musica m = listaMusicas.get(i);
            System.out.println("[" + i + "] " + m.getTitulo() + " - " + m.getArtista());
        }
    }

    public void tocarTudo() {
        if (listaMusicas.isEmpty()) {
            System.out.println("Playlist '" + nome + "' esta vazia.");
            return;
        }
        System.out.println("Tocando playlist '" + nome + "'...");
        for (Musica m : listaMusicas) {
            m.tocar();
        }
    }

    // ── toString ──────────────────────────────────────────────────

    @Override
    public String toString() {
        return String.format("Playlist: %s | Descricao: %s | Musicas: %d",
                nome, descricao.isEmpty() ? "(sem descricao)" : descricao, listaMusicas.size());
    }
}

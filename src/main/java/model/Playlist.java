package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playlist {
    private final String nome;
    private final List<Musica> listaMusicas = new ArrayList<>();

    public Playlist(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da playlist nao pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public String getNome() {
        return nome;
    }

    public List<Musica> getListaMusicas() {
        return Collections.unmodifiableList(listaMusicas);
    }

    public void adicionarMusica(Musica m) {
        if (m == null) {
            throw new IllegalArgumentException("Musica nao pode ser nula.");
        }
        listaMusicas.add(m);
    }

    public void mostrarMusicas() {
        System.out.println("--- Playlist: " + nome + " ---");
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
}


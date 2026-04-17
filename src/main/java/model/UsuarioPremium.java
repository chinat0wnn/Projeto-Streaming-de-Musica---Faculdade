package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Usuario do plano premium.
 * Sem limites de playlists/musicas, sem anuncios, com modo shuffle.
 */
public class UsuarioPremium extends Usuario {

    // ── Construtor ────────────────────────────────────────────────

    /**
     * Construtor — delega ao construtor da classe pai (Usuario).
     */
    public UsuarioPremium(String nome, String email) {
        super(nome, email);
    }

    // ── Metodos sobrescritos (@Override) ──────────────────────────

    @Override
    public int getMaxPlaylists() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getMaxMusicasPorPlaylist() {
        return Integer.MAX_VALUE;
    }

    @Override
    public String getTipoUsuario() {
        return "Premium";
    }

    /**
     * Usuarios Premium nao veem anuncios.
     */
    @Override
    public void exibirAnuncio() {
        // Premium: sem anuncios!
    }

    // ── Metodo exclusivo do Premium ──────────────────────────────

    /**
     * Toca todas as musicas da playlist em ordem aleatoria.
     * Funcionalidade exclusiva do plano Premium.
     */
    public void tocarShuffle(Playlist playlist) {
        if (playlist == null) {
            throw new IllegalArgumentException("Playlist nao pode ser nula.");
        }
        List<Musica> musicas = playlist.getListaMusicas();
        if (musicas.isEmpty()) {
            System.out.println("Playlist '" + playlist.getNome() + "' esta vazia.");
            return;
        }

        System.out.println("Tocando playlist '" + playlist.getNome() + "' em modo SHUFFLE...");

        // Cria copia para embaralhar sem alterar a original
        List<Musica> embaralhada = new ArrayList<>(musicas);
        Collections.shuffle(embaralhada);

        for (Musica m : embaralhada) {
            m.tocar();
        }
    }
}

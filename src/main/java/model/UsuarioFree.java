package model;

/**
 * Usuario do plano gratuito.
 * Limites: max 3 playlists, max 5 musicas por playlist, exibe anuncios.
 */
public class UsuarioFree extends Usuario {

    private static final int MAX_PLAYLISTS = 3;
    private static final int MAX_MUSICAS_POR_PLAYLIST = 5;

    // ── Construtor ────────────────────────────────────────────────

    /**
     * Construtor — delega ao construtor da classe pai (Usuario).
     */
    public UsuarioFree(String nome, String email) {
        super(nome, email);
    }

    // ── Metodos sobrescritos (@Override) ──────────────────────────

    @Override
    public int getMaxPlaylists() {
        return MAX_PLAYLISTS;
    }

    @Override
    public int getMaxMusicasPorPlaylist() {
        return MAX_MUSICAS_POR_PLAYLIST;
    }

    @Override
    public String getTipoUsuario() {
        return "Free";
    }

    /**
     * Usuarios Free veem anuncios antes de cada reproducao.
     */
    @Override
    public void exibirAnuncio() {
        System.out.println("+------------------------------------------+");
        System.out.println("|  [ANUNCIO] Assine Premium e ouca sem     |");
        System.out.println("|  anuncios, com playlists ilimitadas!     |");
        System.out.println("+------------------------------------------+");
    }
}

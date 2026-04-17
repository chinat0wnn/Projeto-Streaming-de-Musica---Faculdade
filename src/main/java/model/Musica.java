package model;

public class Musica {

    private String titulo;
    private String artista;
    private String genero;
    private double duracao;
    private String caminhoArquivo;

    // ── Construtores ──────────────────────────────────────────────

    /**
     * Construtor completo — garante que o objeto nasce em estado valido.
     * Todas as validacoes sao delegadas aos setters para evitar duplicacao.
     */
    public Musica(String titulo, String artista, String genero, double duracao, String caminhoArquivo) {
        setTitulo(titulo);
        setArtista(artista);
        setGenero(genero);
        setDuracao(duracao);
        setCaminhoArquivo(caminhoArquivo);
    }

    /**
     * Construtor simplificado — util para demos onde o arquivo nao importa.
     */
    public Musica(String titulo, String artista, String genero, double duracao) {
        this(titulo, artista, genero, duracao, "sem-arquivo.mp3");
    }

    // ── Metodos de negocio ────────────────────────────────────────

    public void tocar() {
        int minutos = (int) duracao / 60;
        int segundos = (int) duracao % 60;
        System.out.println("Tocando agora: " + titulo + " - " + artista + " (" + String.format("%02d:%02d", minutos, segundos) + ")");
    }

    public static boolean validarFormatoAudio(String formato) {
        if (formato == null) return false;
        String f = formato.trim().toLowerCase();
        if (f.isEmpty()) return false;
        if (!f.startsWith(".")) f = "." + f;
        return f.equals(".mp3") || f.equals(".wav");
    }

    // ── Getters e Setters (com validacao) ─────────────────────────

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O titulo nao pode ser vazio.");
        }
        this.titulo = titulo.trim();
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        if (artista == null || artista.trim().isEmpty()) {
            throw new IllegalArgumentException("O artista nao pode ser vazio.");
        }
        this.artista = artista.trim();
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        if (genero == null || genero.trim().isEmpty()) {
            throw new IllegalArgumentException("O genero nao pode ser vazio.");
        }
        this.genero = genero.trim();
    }

    public double getDuracao() {
        return duracao;
    }

    public void setDuracao(double duracao) {
        if (duracao <= 0) {
            throw new IllegalArgumentException("A duracao deve ser maior que zero.");
        }
        this.duracao = duracao;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        if (caminhoArquivo == null || caminhoArquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("O caminho do arquivo nao pode ser vazio.");
        }
        String nome = caminhoArquivo.trim().toLowerCase();
        if (!nome.endsWith(".mp3") && !nome.endsWith(".wav")) {
            throw new IllegalArgumentException("Formato de arquivo nao suportado. Use .mp3 ou .wav.");
        }
        this.caminhoArquivo = caminhoArquivo.trim();
    }

    // ── toString ──────────────────────────────────────────────────

    @Override
    public String toString() {
        int minutos = (int) duracao / 60;
        int segundos = (int) duracao % 60;
        return String.format("Titulo: %s | Artista: %s | Genero: %s | Duracao: %02d:%02d | Arquivo: %s",
                titulo, artista, genero, minutos, segundos, caminhoArquivo);
    }
}

package model;

public class Musica {

    private String titulo;
    private String artista;
    private String genero;
    private double duracao;
    private String caminhoArquivo;

    public Musica(String titulo, String artista, String genero, double duracao, String caminhoArquivo) {
        this.titulo = titulo;
        this.artista = artista;
        this.genero = genero;
        this.duracao = duracao;
        this.caminhoArquivo = caminhoArquivo;
    }

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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public double getDuracao() {
        return duracao;
    }

    public void setDuracao(double duracao) {
        this.duracao = duracao;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    @Override
    public String toString() {
        int minutos = (int) duracao / 60;
        int segundos = (int) duracao % 60;
        return String.format("Titulo: %s | Artista: %s | Genero: %s | Duracao: %02d:%02d | Arquivo: %s",
                titulo, artista, genero, minutos, segundos, caminhoArquivo);
    }
}

package util;

import model.Musica;

import java.io.File;
import java.util.List;

public final class Validador {

    private Validador() {
    }

    public static void validarString(String valor, String nomeCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo '" + nomeCampo + "' nao pode ser vazio.");
        }
    }

    public static void validarDuracao(double duracao) {
        if (duracao <= 0) {
            throw new IllegalArgumentException("A duracao deve ser maior que zero.");
        }
    }

    public static void validarCaminhoArquivo(String caminho) {
        validarString(caminho, "caminhoArquivo");
        File arquivo = new File(caminho);
        if (!arquivo.exists()) {
            throw new IllegalArgumentException("Arquivo nao encontrado: " + caminho);
        }
        String nome = arquivo.getName().toLowerCase();
        if (!nome.endsWith(".wav") && !nome.endsWith(".mp3")) {
            throw new IllegalArgumentException("Formato de arquivo nao suportado. Use .wav ou .mp3.");
        }
    }

    public static void validarDuplicata(List<Musica> lista, String titulo, String artista) {
        for (Musica m : lista) {
            if (m.getTitulo().equalsIgnoreCase(titulo) && m.getArtista().equalsIgnoreCase(artista)) {
                throw new IllegalArgumentException("Musica ja cadastrada: " + titulo + " - " + artista);
            }
        }
    }

    /**
     * Valida formato basico de email (deve conter '@' e '.').
     */
    public static void validarEmail(String email) {
        validarString(email, "email");
        String emailTrimmed = email.trim();
        if (!emailTrimmed.contains("@") || !emailTrimmed.contains(".")) {
            throw new IllegalArgumentException("Formato de email invalido. Deve conter '@' e '.'");
        }
    }
}

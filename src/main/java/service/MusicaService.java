package service;

import model.Musica;
import util.Validador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MusicaService {

    private final ArrayList<Musica> musicas = new ArrayList<>();

    public void cadastrar(Musica m) {
        Validador.validarString(m.getTitulo(), "titulo");
        Validador.validarString(m.getArtista(), "artista");
        Validador.validarString(m.getGenero(), "genero");
        Validador.validarDuracao(m.getDuracao());
        Validador.validarCaminhoArquivo(m.getCaminhoArquivo());
        Validador.validarDuplicata(musicas, m.getTitulo(), m.getArtista());
        musicas.add(m);
    }

    public List<Musica> listarTodas() {
        return Collections.unmodifiableList(musicas);
    }

    public List<Musica> buscarPorTitulo(String t) {
        Validador.validarString(t, "titulo");
        List<Musica> resultado = new ArrayList<>();
        String busca = t.toLowerCase();
        for (Musica m : musicas) {
            if (m.getTitulo().toLowerCase().contains(busca)) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    public List<Musica> buscarPorArtista(String a) {
        Validador.validarString(a, "artista");
        List<Musica> resultado = new ArrayList<>();
        String busca = a.toLowerCase();
        for (Musica m : musicas) {
            if (m.getArtista().toLowerCase().contains(busca)) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    public Musica remover(int index) {
        if (index < 0 || index >= musicas.size()) {
            throw new IndexOutOfBoundsException("Indice invalido: " + index + ". Total de musicas: " + musicas.size());
        }
        return musicas.remove(index);
    }
}

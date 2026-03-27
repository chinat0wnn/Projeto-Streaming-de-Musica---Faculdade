import model.Musica;
import model.Playlist;
import model.Usuario;
import service.LocalAudioService;
import service.FonteAudioService;
import service.MusicaService;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final MusicaService musicaService = new MusicaService();
    private static final FonteAudioService audioService = new LocalAudioService();
    private static final Scanner scanner = new Scanner(System.in);
    private static Usuario usuarioAtual;

    public static void main(String[] args) {
        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();
            try {
                System.out.print("Escolha uma opcao: ");
                opcao = scanner.nextInt();
                scanner.nextLine();
                switch (opcao) {
                    case 1:
                        cadastrarMusica();
                        break;
                    case 2:
                        listarMusicas();
                        break;
                    case 3:
                        buscarPorTitulo();
                        break;
                    case 4:
                        buscarPorArtista();
                        break;
                    case 5:
                        removerMusica();
                        break;
                    case 6:
                        reproduzirMusica();
                        break;
                    case 7:
                        importarDiretorio();
                        break;
                    case 8:
                        demoPOO();
                        break;
                    case 9:
                        gerenciarPlaylists();
                        break;
                    case 0:
                        System.out.println("Encerrando o sistema. Ate logo!");
                        break;
                    default:
                        System.out.println("Opcao invalida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. Digite um numero.");
                scanner.nextLine();
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("+-------------------------------+");
        System.out.println("|   STREAMING DE MUSICA v1.0    |");
        System.out.println("+-------------------------------+");
        System.out.println("| [1] Cadastrar Musica          |");
        System.out.println("| [2] Listar Musicas            |");
        System.out.println("| [3] Buscar por Titulo         |");
        System.out.println("| [4] Buscar por Artista        |");
        System.out.println("| [5] Remover Musica            |");
        System.out.println("| [6] Reproduzir Musica         |");
        System.out.println("| [7] Importar de Diretorio     |");
        System.out.println("| [8] Demo POO (Playlists)      |");
        System.out.println("| [9] Gerenciar Playlists       |");
        System.out.println("| [0] Sair                      |");
        System.out.println("+-------------------------------+");
    }

    private static String selecionarArquivo() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecione um arquivo de musica");
        chooser.setFileFilter(new FileNameExtensionFilter("Arquivos de audio (*.mp3, *.wav)", "mp3", "wav"));
        chooser.setAcceptAllFileFilterUsed(false);
        int resultado = chooser.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    private static double obterDuracaoAudio(String caminho) {
        try {
            File arquivo = new File(caminho);
            if (arquivo.getName().toLowerCase().endsWith(".wav")) {
                AudioInputStream ais = AudioSystem.getAudioInputStream(arquivo);
                long frames = ais.getFrameLength();
                float frameRate = ais.getFormat().getFrameRate();
                ais.close();
                if (frames > 0 && frameRate > 0) {
                    return frames / frameRate;
                }
            }
        } catch (Exception ignored) {
        }
        return -1;
    }

    private static void cadastrarMusica() {
        try {
            System.out.println("Selecione o arquivo de musica na janela...");
            String caminho = selecionarArquivo();
            if (caminho == null) {
                System.out.println("Nenhum arquivo selecionado. Cadastro cancelado.");
                return;
            }
            System.out.println("Arquivo selecionado: " + caminho);

            System.out.print("Titulo: ");
            String titulo = scanner.nextLine();
            System.out.print("Artista: ");
            String artista = scanner.nextLine();
            System.out.print("Genero: ");
            String genero = scanner.nextLine();

            double duracao = obterDuracaoAudio(caminho);
            if (duracao > 0) {
                int min = (int) duracao / 60;
                int seg = (int) duracao % 60;
                System.out.println("Duracao detectada automaticamente: " + String.format("%02d:%02d", min, seg));
            } else {
                System.out.print("Duracao (em segundos): ");
                duracao = scanner.nextDouble();
                scanner.nextLine();
            }

            Musica musica = new Musica(titulo, artista, genero, duracao, caminho);
            musicaService.cadastrar(musica);
            System.out.println("Musica cadastrada com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("Duracao invalida. Digite um numero valido.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarMusicas() {
        List<Musica> musicas = musicaService.listarTodas();
        if (musicas.isEmpty()) {
            System.out.println("Nenhuma musica cadastrada.");
            return;
        }
        System.out.println("--- Lista de Musicas ---");
        for (int i = 0; i < musicas.size(); i++) {
            System.out.println("[" + i + "] " + musicas.get(i));
        }
    }

    private static void buscarPorTitulo() {
        try {
            System.out.print("Digite o titulo para busca: ");
            String titulo = scanner.nextLine();
            List<Musica> resultado = musicaService.buscarPorTitulo(titulo);
            if (resultado.isEmpty()) {
                System.out.println("Nenhuma musica encontrada.");
            } else {
                System.out.println("--- Resultados ---");
                for (Musica m : resultado) {
                    System.out.println(m);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void buscarPorArtista() {
        try {
            System.out.print("Digite o artista para busca: ");
            String artista = scanner.nextLine();
            List<Musica> resultado = musicaService.buscarPorArtista(artista);
            if (resultado.isEmpty()) {
                System.out.println("Nenhuma musica encontrada.");
            } else {
                System.out.println("--- Resultados ---");
                for (Musica m : resultado) {
                    System.out.println(m);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void removerMusica() {
        List<Musica> musicas = musicaService.listarTodas();
        if (musicas.isEmpty()) {
            System.out.println("Nenhuma musica cadastrada.");
            return;
        }
        listarMusicas();
        try {
            System.out.print("Digite o indice da musica a remover: ");
            int index = scanner.nextInt();
            scanner.nextLine();
            Musica removida = musicaService.remover(index);
            System.out.println("Musica removida: " + removida.getTitulo());
        } catch (InputMismatchException e) {
            System.out.println("Entrada invalida. Digite um numero.");
            scanner.nextLine();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void reproduzirMusica() {
        List<Musica> musicas = musicaService.listarTodas();
        if (musicas.isEmpty()) {
            System.out.println("Nenhuma musica cadastrada.");
            return;
        }
        listarMusicas();
        try {
            System.out.print("Digite o indice da musica a reproduzir: ");
            int index = scanner.nextInt();
            scanner.nextLine();
            if (index < 0 || index >= musicas.size()) {
                System.out.println("Indice invalido.");
                return;
            }
            Musica musica = musicas.get(index);
            System.out.println("Reproduzindo: " + musica.getTitulo() + " - " + musica.getArtista());
            audioService.reproduzir(musica.getCaminhoArquivo());
        } catch (InputMismatchException e) {
            System.out.println("Entrada invalida. Digite um numero.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Erro ao reproduzir: " + e.getMessage());
        }
    }

    private static void importarDiretorio() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecione o diretorio com as musicas");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        int resultado = chooser.showOpenDialog(null);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            System.out.println("Nenhum diretorio selecionado. Importacao cancelada.");
            return;
        }

        File diretorio = chooser.getSelectedFile();
        File[] arquivos = diretorio.listFiles(f -> {
            String nome = f.getName().toLowerCase();
            return f.isFile() && (nome.endsWith(".mp3") || nome.endsWith(".wav"));
        });

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum arquivo MP3 ou WAV encontrado no diretorio.");
            return;
        }

        System.out.println("Encontrados " + arquivos.length + " arquivo(s) de audio.");
        System.out.print("Artista padrao para todas as musicas: ");
        String artista = scanner.nextLine();
        System.out.print("Genero padrao para todas as musicas: ");
        String genero = scanner.nextLine();

        int importadas = 0;
        int ignoradas = 0;
        for (File arquivo : arquivos) {
            String caminho = arquivo.getAbsolutePath();
            String nome = arquivo.getName();
            String titulo = nome.contains(".") ? nome.substring(0, nome.lastIndexOf('.')) : nome;

            double duracao = obterDuracaoAudio(caminho);
            if (duracao <= 0) duracao = 0;

            try {
                Musica musica = new Musica(titulo, artista, genero, duracao > 0 ? duracao : 1, caminho);
                musicaService.cadastrar(musica);
                System.out.println("  [OK] " + titulo);
                importadas++;
            } catch (IllegalArgumentException e) {
                System.out.println("  [IGNORADA] " + titulo + " - " + e.getMessage());
                ignoradas++;
            }
        }

        System.out.println("Importacao concluida: " + importadas + " importada(s), " + ignoradas + " ignorada(s).");
    }

    private static void demoPOO() {
        System.out.println("--- Demo POO: Classe, Objeto e Static ---");

        Usuario usuario = new Usuario("Breno", "breno@email.com");

        System.out.println("Musica.validarFormatoAudio('.mp3') = " + Musica.validarFormatoAudio(".mp3"));
        System.out.println("Musica.validarFormatoAudio('.flac') = " + Musica.validarFormatoAudio(".flac"));

        Musica m1 = new Musica("Bohemian Rhapsody", "Queen", "Rock", 354, "caminho/nao-usado.mp3");
        Musica m2 = new Musica("Billie Jean", "Michael Jackson", "Pop", 294, "caminho/nao-usado.wav");

        Playlist p = usuario.criarPlaylist("Favoritas");
        usuario.adicionarNaPlaylist("Favoritas", m1);
        usuario.adicionarNaPlaylist("Favoritas", m2);

        p.mostrarMusicas();
        p.tocarTudo();
    }

    private static void gerenciarPlaylists() {
        garantirUsuario();

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("+-------------------------------+");
            System.out.println("|      GERENCIAR PLAYLISTS      |");
            System.out.println("+-------------------------------+");
            System.out.println("| Usuario: " + usuarioAtual.getNome() + " <" + usuarioAtual.getEmail() + ">");
            System.out.println("| [1] Criar playlist            |");
            System.out.println("| [2] Listar playlists          |");
            System.out.println("| [3] Adicionar musica na lista |");
            System.out.println("| [4] Mostrar musicas playlist  |");
            System.out.println("| [5] Tocar playlist            |");
            System.out.println("| [0] Voltar                    |");
            System.out.println("+-------------------------------+");

            try {
                System.out.print("Escolha uma opcao: ");
                opcao = scanner.nextInt();
                scanner.nextLine();
                switch (opcao) {
                    case 1:
                        criarPlaylist();
                        break;
                    case 2:
                        listarPlaylists();
                        break;
                    case 3:
                        adicionarMusicaEmPlaylist();
                        break;
                    case 4:
                        mostrarMusicasPlaylist();
                        break;
                    case 5:
                        tocarPlaylist();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcao invalida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. Digite um numero.");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }

            System.out.println();
        }
    }

    private static void garantirUsuario() {
        if (usuarioAtual != null) return;
        System.out.println("--- Criacao de Usuario ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        usuarioAtual = new Usuario(nome, email);
        System.out.println("Usuario criado com sucesso!");
        System.out.println();
    }

    private static void criarPlaylist() {
        System.out.print("Nome da playlist: ");
        String nome = scanner.nextLine();
        usuarioAtual.criarPlaylist(nome);
        System.out.println("Playlist criada: " + nome);
    }

    private static void listarPlaylists() {
        List<Playlist> playlists = usuarioAtual.getPlaylists();
        if (playlists.isEmpty()) {
            System.out.println("Nenhuma playlist criada.");
            return;
        }
        System.out.println("--- Playlists ---");
        for (int i = 0; i < playlists.size(); i++) {
            Playlist p = playlists.get(i);
            System.out.println("[" + i + "] " + p.getNome() + " (" + p.getListaMusicas().size() + " musica(s))");
        }
    }

    private static void adicionarMusicaEmPlaylist() {
        if (usuarioAtual.getPlaylists().isEmpty()) {
            System.out.println("Voce ainda nao tem playlists. Crie uma primeiro.");
            return;
        }

        List<Musica> musicas = musicaService.listarTodas();
        if (musicas.isEmpty()) {
            System.out.println("Nenhuma musica cadastrada no catalogo. Cadastre/importa primeiro.");
            return;
        }

        listarPlaylists();
        System.out.print("Digite o nome da playlist: ");
        String nomePlaylist = scanner.nextLine();
        Playlist playlist = buscarPlaylist(nomePlaylist);
        if (playlist == null) {
            System.out.println("Playlist nao encontrada.");
            return;
        }

        System.out.println("--- Catalogo de Musicas ---");
        for (int i = 0; i < musicas.size(); i++) {
            Musica m = musicas.get(i);
            System.out.println("[" + i + "] " + m.getTitulo() + " - " + m.getArtista());
        }
        System.out.print("Indice da musica para adicionar: ");
        int idx = scanner.nextInt();
        scanner.nextLine();
        if (idx < 0 || idx >= musicas.size()) {
            System.out.println("Indice invalido.");
            return;
        }

        usuarioAtual.adicionarNaPlaylist(playlist.getNome(), musicas.get(idx));
        System.out.println("Musica adicionada na playlist '" + playlist.getNome() + "'.");
    }

    private static void mostrarMusicasPlaylist() {
        if (usuarioAtual.getPlaylists().isEmpty()) {
            System.out.println("Nenhuma playlist criada.");
            return;
        }
        listarPlaylists();
        System.out.print("Digite o nome da playlist: ");
        String nomePlaylist = scanner.nextLine();
        Playlist playlist = buscarPlaylist(nomePlaylist);
        if (playlist == null) {
            System.out.println("Playlist nao encontrada.");
            return;
        }
        playlist.mostrarMusicas();
    }

    private static void tocarPlaylist() {
        if (usuarioAtual.getPlaylists().isEmpty()) {
            System.out.println("Nenhuma playlist criada.");
            return;
        }
        listarPlaylists();
        System.out.print("Digite o nome da playlist: ");
        String nomePlaylist = scanner.nextLine();
        Playlist playlist = buscarPlaylist(nomePlaylist);
        if (playlist == null) {
            System.out.println("Playlist nao encontrada.");
            return;
        }
        playlist.tocarTudo();
    }

    private static Playlist buscarPlaylist(String nomePlaylist) {
        for (Playlist p : usuarioAtual.getPlaylists()) {
            if (p.getNome().equalsIgnoreCase(nomePlaylist)) return p;
        }
        return null;
    }
}

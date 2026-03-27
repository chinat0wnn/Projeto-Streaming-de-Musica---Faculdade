package service;

import javazoom.jl.player.Player;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LocalAudioService implements FonteAudioService {

    @Override
    public void reproduzir(String caminhoArquivo) throws Exception {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            throw new FileNotFoundException("Arquivo nao encontrado: " + caminhoArquivo);
        }

        String nome = arquivo.getName().toLowerCase();
        if (nome.endsWith(".wav")) {
            reproduzirWav(arquivo);
        } else if (nome.endsWith(".mp3")) {
            reproduzirMp3(arquivo);
        } else {
            throw new UnsupportedOperationException("Formato nao suportado: " + nome);
        }
    }

    private void reproduzirWav(File arquivo) throws Exception {
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivo)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            System.out.println("Reproduzindo WAV... Pressione ENTER para parar.");
            System.in.read();
            clip.stop();
            clip.close();
        } catch (UnsupportedAudioFileException e) {
            throw new UnsupportedOperationException("Arquivo WAV nao suportado: " + e.getMessage());
        } catch (LineUnavailableException e) {
            throw new Exception("Dispositivo de audio indisponivel: " + e.getMessage());
        }
    }

    private void reproduzirMp3(File arquivo) throws Exception {
        try (FileInputStream fis = new FileInputStream(arquivo);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            Player player = new Player(bis);
            System.out.println("Reproduzindo MP3... (aguarde o termino da faixa)");
            player.play();
        } catch (Exception e) {
            throw new Exception("Erro ao reproduzir MP3: " + e.getMessage());
        }
    }
}

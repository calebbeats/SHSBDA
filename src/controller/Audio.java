package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

// Class that is responsible for playing audio in game
public class Audio {

    private Clip c;

    public void playAudio(String s) {
        AudioInputStream stream;
        try {
            stream = AudioSystem.getAudioInputStream(getClass().getResource(s));
            if (s.equals("/resources/chugchug.wav")) {
                c = AudioSystem.getClip();
                c.open(stream);
                c.loop(Clip.LOOP_CONTINUOUSLY);
                c.start();
            } else {
                Clip clip = AudioSystem.getClip();
                clip.open(stream);
                clip.start();
            }
            stream.close();
        } catch (IOException | LineUnavailableException
                | UnsupportedAudioFileException ex) {
            Logger.getLogger(MouseController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public Clip getC() {
        return c;
    }
}

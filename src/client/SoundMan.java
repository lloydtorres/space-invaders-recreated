package client;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.sound.sampled.*;

// Used to play sounds in the game
// Reference: http://stackoverflow.com/questions/15526255/best-way-to-get-sound-on-button-press-for-a-java-calculator
public class SoundMan {
    private SoundMan() {}
    private static String[] numList = {"1","2","3","4"};

    // gets a preset name from somewhere in the program then passes the proper WAV file path to musicPlayer()
    public static Clip play(String name) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (Arrays.asList(numList).contains(name)){
            return musicPlayer("sounds/beat" + name + ".wav");
        }
        else if (name.equals("playerShoot")){
            return musicPlayer("sounds/playerShoot.wav");
        }
        else if (name.equals("alienShot")){
            return musicPlayer("sounds/alienShot.wav");
        }
        else if (name.equals("ufo")){
            return musicPlayer("sounds/ufo.wav");
        }
        else if (name.equals("playerDown")){
            return musicPlayer("sounds/playerDown.wav");
        }
        else {
            return null;
        }
    }

    // actually plays the music
    private static Clip musicPlayer(String fileName) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        try {
            clip.open(audioInputStream);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clip.start();
        return clip;
    }
}

package client;
import kuusisto.tinysound.TinySound;
import kuusisto.tinysound.Sound;

public class TinySoundAdapter implements SoundPlayer {
    private Sound sound;

    public TinySoundAdapter() {
        TinySound.init();
    }

    @Override
    public void play(String filePath) {
        this.sound = TinySound.loadSound(filePath);
        if (sound != null) {
            sound.play();
        }
    }

    @Override
    public void stop() {
        if (sound != null) {
            sound.stop();
        }
    }

    @Override
    public void setVolume(float volume) {
        TinySound.setGlobalVolume((double) volume);
    }

    public void shutdown() {
        TinySound.shutdown();
    }
}

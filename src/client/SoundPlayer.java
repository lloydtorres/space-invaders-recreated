package client;

public interface SoundPlayer {
    void play(String filePath);
    void stop();
    void setVolume(float volume);
}
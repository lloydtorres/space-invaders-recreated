package client;

import java.util.AbstractMap;
import java.util.Map;

public class ClientConfig {
    public static String soundsPath = "sounds/";
    public static Map<String, String> soundsMap = Map.ofEntries(
            new AbstractMap.SimpleEntry<String, String>("PLAYER_SHOOT", "playerShoot.wav"),
            new AbstractMap.SimpleEntry<String, String>("PLAYER_HIT", "playerDown.wav"),
            new AbstractMap.SimpleEntry<String, String>("ALIEN_SHOOT", "playerShoot.wav"),
            new AbstractMap.SimpleEntry<String, String>("ALIEN_HIT", "alienShot.wav"),
            new AbstractMap.SimpleEntry<String, String>("PLAYER_MOVE_HIGH", "playerMoveHigh.wav"),
            new AbstractMap.SimpleEntry<String, String>("PLAYER_MOVE_LOW", "playerMoveLow.wav"),
            new AbstractMap.SimpleEntry<String, String>("ENEMY_MOVE_HIGH", "enemyMoveHigh.wav"),
            new AbstractMap.SimpleEntry<String, String>("ENEMY_MOVE_LOW", "enemyMoveLow.wav")
    );

    public static String getFullSoundPath(String soundType){
        return soundsPath + soundsMap.get(soundType);
    }
}

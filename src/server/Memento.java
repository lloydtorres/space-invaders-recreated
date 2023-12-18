package server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import server.entities.*;

public class Memento {
    private final GameState gameState;
    private final Map<Integer, PlayerServerEntity> playerEntities;
    private final Map<Integer, Entity> enemyEntities;
    private final Map<Integer, BulletServerEntity> bulletEntities;
    private final Map<Integer, ShieldFragmentServerEntity> shieldFragmentEntities;
    private final int livesLeft;
    private final int score;
    
    

    public Memento(GameState gameState){
        this.gameState = gameState;
        playerEntities = new ConcurrentHashMap<Integer, PlayerServerEntity>(gameState.getPlayerEntities());
        enemyEntities = new ConcurrentHashMap<Integer, Entity>(gameState.getEnemyEntities());
        bulletEntities = new ConcurrentHashMap<Integer, BulletServerEntity>(gameState.getBulletEntities());
        shieldFragmentEntities = new ConcurrentHashMap<Integer, ShieldFragmentServerEntity>(gameState.getShieldFragmentEntities());
        livesLeft = gameState.getLivesLeft();
        score = gameState.getScore();
    }

    public void Restore(){
        gameState.setPlayerEntities(playerEntities);
        gameState.setEnemyEntities(enemyEntities);
        gameState.setBulletEntities(bulletEntities);
        gameState.setShieldFragmentEntities(shieldFragmentEntities);
        gameState.setLivesLeft(livesLeft);
        gameState.setScore(score);
    }
}

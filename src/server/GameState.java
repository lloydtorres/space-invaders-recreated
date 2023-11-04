package server;

import common.MoveDirection;
import server.entities.*;
import server.entities.EnemyServerEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameState {
    // IdCounter for simple entity id generation (except for players)
    // highly unlikely to reach Integer.MAX_VALUE in a single game session
    private int IdCounter = 0;
    private Map<Integer, PlayerServerEntity> playerEntities;
    private Map<Integer, EnemyServerEntity> enemyEntities;
    private Map<Integer, BulletServerEntity> bulletEntities;
    private Map<Integer, ShieldFragmentServerEntity> shieldFragmentEntities;
    private final int RIGHT_MOVEMENT_BOUND = 740;
    private final int LEFT_MOVEMENT_BOUND = 12;
    private final int BOUNDS_CENTER = (RIGHT_MOVEMENT_BOUND - LEFT_MOVEMENT_BOUND) / 2;
    private final int PLAYERS_LINE_HEIGHT = 570;
    private final int GAME_WIDTH = 770;
    private final int GAME_HEIGHT = 652;
    private int livesLeft = 3;
    private int score;
    public GameState(){
        playerEntities = new ConcurrentHashMap<>();
        enemyEntities = new ConcurrentHashMap<>();
        bulletEntities = new ConcurrentHashMap<>();
        shieldFragmentEntities = new ConcurrentHashMap<>();
    }
    public void initializeGame(){
        generateEnemies();
        generateShields();
        score = 0;
    }
    public void updateBullets(){
        removeBulletsOutOfBounds();
        for(BulletServerEntity entity : bulletEntities.values()){
            switch (entity.getBulletSender()){
                case ENEMY:
                    entity.move(MoveDirection.DOWN);
                    break;
                case PLAYER:
                    entity.move(MoveDirection.UP);
                    break;
            }
        }
    }
    public void updateEnemies(MoveDirection moveDirection){
        for(EnemyServerEntity enemy : enemyEntities.values()) {
            enemy.move(moveDirection);
        }

    }
    //Placeholders for now
    public void moveEnemies(){
        return;
    }

    public void generateEnemies(){
        return;
    }
    public void generateShields(){
        return;
    }
    private void removeBulletsOutOfBounds(){
        for(int key : bulletEntities.keySet()){
            BulletServerEntity entity = bulletEntities.get(key);
            if(entity.getY() <= 0 || entity.getY() >= GAME_HEIGHT){
                removeBulletEntity(key);
            }
        }
    }


    // Straightforward, although a slow, solution to collision checking
    // Next steps would be to implement something like a Grid map or a Quadtree for storing entities.
    public void checkBulletCollisions(){
        for(int bulletId : bulletEntities.keySet()){
            BulletServerEntity bullet = bulletEntities.get(bulletId);
            for(int shieldId : shieldFragmentEntities.keySet()){
                if(bullet.intersects(shieldFragmentEntities.get(shieldId))){
                    removeBulletEntity(bulletId);
                    removeShieldFragmentEntity(shieldId);
                    break;
                }
            }
            if(bulletEntities.get(bulletId) == null){
                break;
            }
            switch (bullet.getBulletSender()){
                case PLAYER:
                    for(int enemyId : enemyEntities.keySet()){
                        EnemyServerEntity enemy = enemyEntities.get(enemyId);
                        if(bullet.intersects(enemy)){
                            addPoints(enemy.getPointWorth());
                            removeBulletEntity(bulletId);
                            removeEnemyEntity(enemyId);
                            break;
                        }
                    }
                    break;
                case ENEMY:
                    for(int playerId : playerEntities.keySet()){
                        if(bullet.intersects(playerEntities.get(playerId))){
                            removeLife();
                            removeBulletEntity(bulletId);
                            break;
                        }
                    }
                    break;
            }
        }

    }
    public void addPlayerEntity(int id){
        PlayerServerEntity playerEntity = new PlayerServerEntity(id, BOUNDS_CENTER, PLAYERS_LINE_HEIGHT);
        playerEntities.put(id, playerEntity);
    }

    public void removePlayerEntity(int id){
        playerEntities.remove(id);
    }
    private void addEnemyEntity(float x, float y, int pointWorth){
        EnemyServerEntity enemyEntity = new EnemyServerEntity(IdCounter, x, y, pointWorth);
        enemyEntities.put(IdCounter, enemyEntity);
        IdCounter++;
    }
    private void removeEnemyEntity(int id){
        enemyEntities.remove(id);
    }
    private void addBulletEntity(float x, float y, BulletSender bulletSender){
        BulletServerEntity bulletEntity = new BulletServerEntity(IdCounter, x, y, bulletSender);
        bulletEntities.put(IdCounter, bulletEntity);
        IdCounter++;
    }
    public void removeBulletEntity(int id){
        bulletEntities.remove(id);
    }

    private void addShieldFragmentEntity(float x, float y){
        ShieldFragmentServerEntity shieldFragmentEntity = new ShieldFragmentServerEntity(IdCounter, x, y);
        shieldFragmentEntities.put(IdCounter, shieldFragmentEntity);
        IdCounter++;
    }
    private void removeShieldFragmentEntity(int id){
        shieldFragmentEntities.remove(id);
    }

    private void addPoints(int points){
        score += points;
    }
    private void removeLife(){
        livesLeft--;
        if(livesLeft <= 0){
            // handle game over (send game state packet, stop game loop, etc.)
        }
    }
    public void movePlayer(int id, MoveDirection moveDirection){
        playerEntities.get(id).move(moveDirection);
    }

    public void shootFromPlayer(int playerId){
        PlayerServerEntity entity = playerEntities.get(playerId);
        float bulletX = entity.getX() + entity.getWidth() / 2;
        float bulletY = entity.getY() - 8;
        addBulletEntity(bulletX, bulletY, BulletSender.PLAYER);
    }






}

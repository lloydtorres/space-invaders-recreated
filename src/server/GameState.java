package server;

import common.EntityType;
import common.MoveDirection;
import server.entities.*;
import server.entities.EnemyServerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameState implements StateSubject{
    private Map<Integer, PlayerServerEntity> playerEntities;
    private Map<Integer, EnemyServerEntity> enemyEntities;
    private Map<Integer, BulletServerEntity> bulletEntities;
    private Map<Integer, ShieldFragmentServerEntity> shieldFragmentEntities;
    private List<StateObserver> observers;
    private final int RIGHT_MOVEMENT_BOUND = 740;
    private final int LEFT_MOVEMENT_BOUND = 12;
    private final int BOUNDS_CENTER = (RIGHT_MOVEMENT_BOUND - LEFT_MOVEMENT_BOUND) / 2;
    private final int PLAYERS_LINE_HEIGHT = 570;
    private final int GAME_WIDTH = 770;
    private final int GAME_HEIGHT = 652;
    private int livesLeft = 3;
    private int score = 0;
    public GameState(){
        playerEntities = new ConcurrentHashMap<>();
        enemyEntities = new ConcurrentHashMap<>();
        bulletEntities = new ConcurrentHashMap<>();
        shieldFragmentEntities = new ConcurrentHashMap<>();
        observers = new ArrayList<>();
    }

    @Override
    public void addObserver(StateObserver observer) {
        if(!observers.contains(observer)){
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(StateObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(GameStateEvent event) {
        for(StateObserver observer : observers){
            observer.onEvent(event);
        }
    }
    public void initializeGame(){
        generateEnemies();
        generateShields();
        score = 0;
        livesLeft = 3;
    }
    private void generateEnemies(){
        return;
    }
    private void generateShields(){
        return;
    }
    public void updateBullets(){
        removeBulletsOutOfBounds();
        for(BulletServerEntity entity : bulletEntities.values()){
            switch (entity.getBulletSender()){
                case ENEMY:
                    moveEntity(entity, MoveDirection.DOWN);
                    break;
                case PLAYER:
                    moveEntity(entity, MoveDirection.UP);
                    break;
            }
        }
    }
    private void moveEntity(ServerEntity entity, MoveDirection moveDirection){
        entity.move(moveDirection);
        notifyObservers(new EntityUpdateEvent(entity, false));
    }

    public void moveEnemies(){
        return;
    }


    public void shootFromEnemy(int enemyId){
        return;
    }
    private void removeBulletsOutOfBounds(){
        for(int key : bulletEntities.keySet()){
            BulletServerEntity entity = bulletEntities.get(key);
            if(entity.getY() <= 0 || entity.getY() >= GAME_HEIGHT){
                removeEntity(key, EntityType.BULLET);
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
                    removeEntity(bulletId, EntityType.BULLET);
                    removeEntity(shieldId, EntityType.SHIELD);
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
                            removeEntity(bulletId, EntityType.BULLET);
                            removeEntity(enemyId, EntityType.ENEMY);
                            break;
                        }
                    }
                    break;
                case ENEMY:
                    for(int playerId : playerEntities.keySet()){
                        if(bullet.intersects(playerEntities.get(playerId))){
                            removeLife();
                            removeEntity(bulletId, EntityType.BULLET);
                            break;
                        }
                    }
                    break;
            }
        }

    }
    private void removeEntity(int id, EntityType entityType){
        ServerEntity entity = null;
        switch(entityType){
            case ENEMY:
                entity = enemyEntities.remove(id);
                break;
            case PLAYER:
                entity = playerEntities.remove(id);
                break;
            case BULLET:
                entity = bulletEntities.remove(id);
                break;
            case SHIELD:
                entity = shieldFragmentEntities.remove(id);
                break;
        }
        notifyObservers(new EntityUpdateEvent(entity, true));
    }
    public int addPlayerEntity(){
        PlayerServerEntity playerEntity = new PlayerServerEntity(BOUNDS_CENTER, PLAYERS_LINE_HEIGHT);
        int id = playerEntity.getId();
        playerEntities.put(id, playerEntity);
        return id;
    }


    private void addEnemyEntity(float x, float y, int pointWorth){
        EnemyServerEntity enemyEntity = new EnemyServerEntity(x, y, pointWorth);
        int id = enemyEntity.getId();
        enemyEntities.put(id, enemyEntity);
        notifyObservers(new EntityUpdateEvent(enemyEntity, false));
    }
    private void addBulletEntity(float x, float y, BulletSender bulletSender){
        BulletServerEntity bulletEntity = new BulletServerEntity(x, y, bulletSender);
        int id = bulletEntity.getId();
        bulletEntities.put(id, bulletEntity);
        notifyObservers(new EntityUpdateEvent(bulletEntity, false));
    }

    private void addShieldFragmentEntity(float x, float y){
        ShieldFragmentServerEntity shieldFragmentEntity = new ShieldFragmentServerEntity(x, y);
        int id = shieldFragmentEntity.getId();
        shieldFragmentEntities.put(id, shieldFragmentEntity);
        notifyObservers(new EntityUpdateEvent(shieldFragmentEntity, false));
    }

    private void addPoints(int points){
        score += points;
        notifyObservers(new ScoreUpdateEvent(score));
    }
    private void removeLife(){
        livesLeft--;
        notifyObservers(new LivesLeftUpdateEvent(livesLeft));
        if(livesLeft <= 0){
            // handle game over (send game state packet, stop game loop, etc.)
        }
    }
    public void movePlayer(int id, MoveDirection moveDirection){
        PlayerServerEntity player = playerEntities.get(id);
        boolean canMove = false;
        switch (moveDirection){
            case LEFT:
                canMove = (player.getX() - player.getXSpeed()) >= LEFT_MOVEMENT_BOUND;
                break;
            case RIGHT:
                canMove = (player.getX() + player.getWidth() + player.getXSpeed()) <= RIGHT_MOVEMENT_BOUND;
                break;
        }
        if(canMove){
            moveEntity(player, moveDirection);
        }
    }
    // needs some sort of shooting timer. In this implementation players can just spam bullets
    public void shootFromPlayer(int playerId){
        PlayerServerEntity entity = playerEntities.get(playerId);
        float bulletX = entity.getX() + entity.getWidth() / 2;
        float bulletY = entity.getY() - 8;
        addBulletEntity(bulletX, bulletY, BulletSender.PLAYER);
    }

    // There is a better way to do this, too lazy to do this now
    public Map<Integer, ServerEntity> getAllEntities(){
        Map<Integer, ServerEntity> entities = new ConcurrentHashMap<>();
        entities.putAll(playerEntities);
        entities.putAll(bulletEntities);
        entities.putAll(enemyEntities);
        entities.putAll(shieldFragmentEntities);
        return entities;
    }

    public int getScore() {
        return score;
    }

    public int getLivesLeft() {
        return livesLeft;
    }

}

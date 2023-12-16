package server;

import common.EntityType;
import common.MoveDirection;
import server.entities.*;
import server.entities.enemy.*;
import server.visitors.DimensionSetterVisitor;
import server.visitors.PointSetterVisitor;
import server.visitors.SpeedSetterVisitor;
import server.visitors.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class GameState implements StateSubject {
    private Map<Integer, PlayerServerEntity> playerEntities;
    private Map<Integer, Entity> enemyEntities;
    private Map<Integer, BulletServerEntity> bulletEntities;
    private Map<Integer, ShieldFragmentServerEntity> shieldFragmentEntities;
    private List<StateObserver> observers;
    private List<Integer> shootCooldown;
    private ScheduledExecutorService executorService;
    private final int RIGHT_MOVEMENT_BOUND = 740;
    private final int LEFT_MOVEMENT_BOUND = 12;
    private final int BOUNDS_CENTER = (RIGHT_MOVEMENT_BOUND - LEFT_MOVEMENT_BOUND) / 2;
    private final int PLAYERS_LINE_HEIGHT = 570;
    private final int GAME_WIDTH = 770;
    private final int GAME_HEIGHT = 652;
    private int livesLeft = 3;
    private int score = 0;
    private final Visitor pointVisitor, dimensionSetterVisitor, speedSetterVisitor;

    public GameState() {
        playerEntities = new ConcurrentHashMap<>();
        enemyEntities = new ConcurrentHashMap<>();
        bulletEntities = new ConcurrentHashMap<>();
        shieldFragmentEntities = new ConcurrentHashMap<>();
        observers = new ArrayList<>();
        shootCooldown = new ArrayList<>();
        executorService = Executors.newScheduledThreadPool(1);
        pointVisitor = new PointSetterVisitor();
        dimensionSetterVisitor = new DimensionSetterVisitor();
        speedSetterVisitor = new SpeedSetterVisitor();
    }

    @Override
    public void addObserver(StateObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(StateObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(GameStateEvent event) {
        for (StateObserver observer : observers) {
            observer.onEvent(event);
        }
    }

    public void initializeGame() {
        generateEnemies();
        generateShields();
        score = 0;
        livesLeft = 3;
    }

    private void generateEnemies() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                int x = 160 + 45 * j;
                int y = 100 + 29 * i;

                Entity enemyEntity;

                if (i == 0) {
                    enemyEntity = new EliteEnemyDecorator(new EnemyServerEntity(x, y));
                } else if (i < 3) {
                    enemyEntity = new StandardEnemyDecorator(new EnemyServerEntity(x, y));
                } else {
                    enemyEntity = new BasicEnemyDecorator(new EnemyServerEntity(x, y));
                }

                enemyEntity.accept(dimensionSetterVisitor);
                enemyEntity.accept(pointVisitor);
                enemyEntity.accept(speedSetterVisitor);

                enemyEntities.put(enemyEntity.getId(), enemyEntity);
                notifyObservers(new EntityUpdateEvent(enemyEntity, false));
            }
        }
    }

    private void generateShields() {
        ShieldFragmentServerEntity shieldEntity = addShieldFragmentEntity(100, 500);
        for (int i = 100; i < 200; i += 10) {
            for (int j = (i == 100) ? 510 : 500; j < 550; j += 10) {
                ShieldFragmentServerEntity shieldCopy = shieldEntity.deepCopy();
                shieldCopy.setNewId();
                shieldCopy.setX(i);
                shieldCopy.setY(j);

                shieldEntity.accept(dimensionSetterVisitor);
                shieldEntity.accept(speedSetterVisitor);

                shieldFragmentEntities.put(shieldCopy.getId(), shieldCopy);
                notifyObservers(new EntityUpdateEvent(shieldCopy, false));
            }
        }
    }

    public void updateBullets() {
        removeBulletsOutOfBounds();
        for (BulletServerEntity entity : bulletEntities.values()) {
            switch (entity.getBulletSender()) {
                case ENEMY:
                    moveEntity(entity, MoveDirection.DOWN);
                    break;
                case PLAYER:
                    moveEntity(entity, MoveDirection.UP);
                    break;
            }
        }
    }

    private void moveEntity(ServerEntity entity, MoveDirection moveDirection) {
        entity.move(moveDirection);
        notifyObservers(new EntityUpdateEvent(entity, false));
    }

    public void moveEnemies() {
        return;
    }

    public void shootFromEnemy(int enemyId) {
        return;
    }

    private void removeBulletsOutOfBounds() {
        for (int key : bulletEntities.keySet()) {
            BulletServerEntity entity = bulletEntities.get(key);
            if (entity.getY() <= 0 || entity.getY() >= GAME_HEIGHT) {
                removeEntity(key, EntityType.BULLET);
            }
        }
    }

    // Straightforward, although a slow, solution to collision checking
    // Next steps would be to implement something like a Grid map or a Quadtree for storing entities.
    public void checkBulletCollisions() {
        for (int bulletId : bulletEntities.keySet()) {
            BulletServerEntity bullet = bulletEntities.get(bulletId);
            for (int shieldId : shieldFragmentEntities.keySet()) {
                if (bullet.intersects(shieldFragmentEntities.get(shieldId))) {
                    removeEntity(bulletId, EntityType.BULLET);
                    removeEntity(shieldId, EntityType.SHIELD);
                    break;
                }
            }
            if (bulletEntities.get(bulletId) == null) {
                break;
            }
            switch (bullet.getBulletSender()) {
                case PLAYER:
                    for (int enemyId : enemyEntities.keySet()) {
                        Entity enemy = enemyEntities.get(enemyId);
                        if (bullet.intersects(enemy)) {
                            addPoints(enemy.getPointWorth());
                            removeEntity(bulletId, EntityType.BULLET);
                            removeEntity(enemyId, EntityType.ENEMY);
                            break;
                        }
                    }
                    break;
                case ENEMY:
                    for (int playerId : playerEntities.keySet()) {
                        if (bullet.intersects(playerEntities.get(playerId))) {
                            removeLife();
                            removeEntity(bulletId, EntityType.BULLET);
                            break;
                        }
                    }
                    break;
            }
        }

    }

    public void removeEntity(int id, EntityType entityType) {
        Entity entity = null;
        switch (entityType) {
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

    public int addPlayerEntity() {
        PlayerServerEntity playerEntity = new PlayerServerEntity(BOUNDS_CENTER, PLAYERS_LINE_HEIGHT);
        int id = playerEntity.getId();

        playerEntity.accept(dimensionSetterVisitor);
        playerEntity.accept(speedSetterVisitor);

        playerEntities.put(id, playerEntity);
        notifyObservers(new EntityUpdateEvent(playerEntity, false));
        return id;
    }

    private void addBulletEntity(float x, float y, BulletSender bulletSender) {
        BulletServerEntity bulletEntity = new BulletServerEntity(x, y, bulletSender);
        int id = bulletEntity.getId();

        bulletEntity.accept(dimensionSetterVisitor);
        bulletEntity.accept(speedSetterVisitor);

        bulletEntities.put(id, bulletEntity);
        notifyObservers(new EntityUpdateEvent(bulletEntity, false));
    }

    private ShieldFragmentServerEntity addShieldFragmentEntity(float x, float y) {
        ShieldFragmentServerEntity shieldFragmentEntity = new ShieldFragmentServerEntity(x, y, new Placeholder("test", 123));
        int id = shieldFragmentEntity.getId();

        shieldFragmentEntity.accept(dimensionSetterVisitor);
        shieldFragmentEntity.accept(speedSetterVisitor);

        shieldFragmentEntities.put(id, shieldFragmentEntity);
        notifyObservers(new EntityUpdateEvent(shieldFragmentEntity, false));
        return shieldFragmentEntity;
    }

    private void addPoints(int points) {
        score += points;
        notifyObservers(new ScoreUpdateEvent(score));
    }

    private void removeLife() {
        livesLeft--;
        notifyObservers(new LivesLeftUpdateEvent(livesLeft));
        if (livesLeft <= 0) {
            // handle game over (send game state packet, stop game loop, etc.)
        }
    }

    public void movePlayer(int id, MoveDirection moveDirection) {
        PlayerServerEntity player = playerEntities.get(id);
        boolean canMove = false;
        switch (moveDirection) {
            case LEFT:
                canMove = (player.getX() - player.getXSpeed()) >= LEFT_MOVEMENT_BOUND;
                break;
            case RIGHT:
                canMove = (player.getX() + player.getXSpeed()) <= RIGHT_MOVEMENT_BOUND;
                break;
        }
        if (canMove) {
            moveEntity(player, moveDirection);
        }
    }

    public void shootFromPlayer(int playerId) {
        if (!shootCooldown.contains(playerId)) {
            PlayerServerEntity entity = playerEntities.get(playerId);
            float bulletX = entity.getX() + entity.getWidth() / 2;
            float bulletY = entity.getY() - 8;
            addBulletEntity(bulletX, bulletY, BulletSender.PLAYER);
            shootCooldown.add(playerId);

            // Removes the player's ID from the list after 1 second allowing the player to shoot again
            executorService.schedule(() -> {
                int indexToRemove = shootCooldown.indexOf(playerId);
                if (indexToRemove != -1) {
                    shootCooldown.remove(indexToRemove);
                }
            }, 1, TimeUnit.SECONDS);
        }
    }

    // There is a better way to do this, too lazy to do this now
    public Map<Integer, Entity> getAllEntities() {
        Map<Integer, Entity> entities = new ConcurrentHashMap<>();
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

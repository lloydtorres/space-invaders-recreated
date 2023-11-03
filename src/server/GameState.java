package server;

import client.PlayerCannon;
import common.packets.Packet;
import server.entities.BulletEntity;
import server.entities.EnemyEntity;
import server.entities.Entity;
import server.entities.PlayerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameState {

    private Map<Integer, Entity> allEntities;
    private Map<Integer, PlayerEntity> playerEntities;
    private Map<Integer, EnemyEntity> enemyEntities;
    private Map<Integer, BulletEntity> bulletEntities;
    private List<Packet> packetsToSend;
    public GameState(){
        packetsToSend = new ArrayList<>();
        allEntities = new ConcurrentHashMap<>();
        playerEntities = new ConcurrentHashMap<>();
        enemyEntities = new ConcurrentHashMap<>();
        bulletEntities = new ConcurrentHashMap<>();
    }
    public List<Packet> update(){
        packetsToSend.clear();

        return packetsToSend;
    }
    private void addEntity(int id, Entity entity){
        allEntities.put(id, entity);
    }
    public void addPlayerEntity(int id, float x, float y, String name){
        PlayerEntity playerEntity = new PlayerEntity(id, x, y, name);
        playerEntities.put(id, playerEntity);
        addEntity(id, playerEntity);
    }
    public void addEnemyEntity(int id, float x, float y, int pointWorth){
        EnemyEntity enemyEntity = new EnemyEntity(id, x, y, pointWorth);
        enemyEntities.put(id, enemyEntity);
        addEntity(id, enemyEntity);
    }
    public void addBulletEntity(int id, float x, float y, float speed){
        BulletEntity bulletEntity = new BulletEntity(id, x, y, speed);
        bulletEntities.put(id, bulletEntity);
        addEntity(id, bulletEntity);
    }

}

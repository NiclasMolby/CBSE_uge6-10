/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.EventType;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.ArrayList;

/**
 *
 * @author niclasmolby
 */
public class BulletControlSystem implements IEntityProcessingService {

    private ArrayList<EntityPlugin> bulletList = new ArrayList();
    @Override
    public void process(GameData gameData, World world) {
        for(Event event : gameData.getEvents()){
            if(event.getType() == EventType.ENEMY_SHOOT || event.getType() == EventType.PLAYER_SHOOT){
                EntityPlugin bullet = new EntityPlugin();
                bullet.start(gameData, world);
                bulletList.add(bullet);
            }
            
        }
        
        for(EntityPlugin bulletPlugin : bulletList){
            Entity bulletEntity = bulletPlugin.getBullet();
            
            float dx = (float) Math.cos(bulletEntity.getRadians()) * 300;
            float dy = (float) Math.sin(bulletEntity.getRadians()) * 300;
            
            bulletEntity.setX(bulletEntity.getX() + dx * gameData.getDelta());
            bulletEntity.setY(bulletEntity.getY() + dy * gameData.getDelta());
            
            if(bulletEntity.getX() < 0) bulletEntity.setX(gameData.getDisplayWidth());
            if(bulletEntity.getX() > gameData.getDisplayWidth()) bulletEntity.setX(0);
            if(bulletEntity.getY() < 0) bulletEntity.setY(gameData.getDisplayHeight());
            if(bulletEntity.getY() > gameData.getDisplayHeight()) bulletEntity.setY(0);
            
            bulletEntity.setExpiration(bulletEntity.getExpiration() - gameData.getDelta());
            
            bulletPlugin.setShape();
            
            if(bulletEntity.getExpiration() <= 0){
                bulletPlugin.stop(gameData, world);
            }
        }
    }
    
    private void setShape(Entity bullet) {
        float[] shapex = new float[2];
        float[] shapey = new float[2];
        shapex[0] = (float) (bullet.getX() + Math.cos(bullet.getRadians()) * 8);
        shapey[0] = (float) (bullet.getY() + Math.sin(bullet.getRadians()) * 8);
        
        shapex[1] = bullet.getX();
        shapey[1] = bullet.getY();
        
        bullet.setShapeX(shapex);
        bullet.setShapeY(shapey);
    }
    
}

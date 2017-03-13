/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.EventType;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

/**
 *
 * @author niclasmolby
 */

public class EntityPlugin implements IGamePluginService {

    private Entity bullet;
    
    @Override
    public void start(GameData gameData, World world) {
        
        for(Event event : gameData.getEvents()){
            if(event.getType() == EventType.PLAYER_SHOOT || event.getType() == EventType.ENEMY_SHOOT){
                bullet = createBullet(event.getEntityID(), world);
                
            }
            
            gameData.removeEvent(event);
            setShape();
            world.addEntity(bullet);
        }
        
        
    }
    
    private Entity createBullet(String id, World world){
        Entity bullet = new Entity();
        bullet.setType(EntityType.BULLET);
        for(Entity entity : world.getEntities(EntityType.PLAYER, EntityType.ENEMY)){
            if(entity.getID().equals(id)){
            bullet.setPosition(entity.getShapeX()[0], entity.getShapeY()[0]);
            bullet.setRadians(entity.getRadians());
            bullet.setExpiration(1);
            }
        }
        return bullet;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(bullet);
    }

    @Override
    public void setShape() {
        float[] shapex = new float[2];
        float[] shapey = new float[2];
        shapex[0] = (float) (bullet.getX() - Math.cos(bullet.getRadians()) * 8);
        shapey[0] = (float) (bullet.getY() - Math.sin(bullet.getRadians()) * 8);
        
        shapex[1] = bullet.getX();
        shapey[1] = bullet.getY();
        
        bullet.setShapeX(shapex);
        bullet.setShapeY(shapey);
    }
    
    public Entity getBullet(){
        return bullet;
    }
    
}

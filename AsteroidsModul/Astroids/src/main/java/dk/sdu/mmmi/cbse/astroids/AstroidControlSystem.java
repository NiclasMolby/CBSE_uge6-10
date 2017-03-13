/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.astroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.EventType;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

/**
 *
 * @author niclasmolby
 */
public class AstroidControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for(Event event : gameData.getEvents()){
                if(event.getType() == EventType.ASTEROID_SPLIT){
                    IGamePluginService asteroid1 = new dk.sdu.mmmi.cbse.astroids.EntityPlugin();
                    IGamePluginService asteroid2 = new dk.sdu.mmmi.cbse.astroids.EntityPlugin();
                    asteroid1.start(gameData, world);
                    asteroid2.start(gameData, world);
                    world.removeEntity(event.getEntityID());
                    gameData.removeEvent(event);
                }
            }
            
        
        for(Entity astroid : world.getEntities(EntityType.ASTEROIDS)){
            
            
            astroid.setRadians(astroid.getRadians()-astroid.getRotationSpeed() * gameData.getDelta());
            
            astroid.setDx((astroid.getDx() + ((float) Math.cos(astroid.getRadians())) * gameData.getDelta()));
            astroid.setDy((astroid.getDy() + ((float) Math.sin(astroid.getRadians())) * gameData.getDelta()));
        
            astroid.setX(astroid.getX() + astroid.getDx() * gameData.getDelta());
            astroid.setY(astroid.getY() + astroid.getDy() * gameData.getDelta());
            
            if(astroid.getX() < 0) astroid.setX(gameData.getDisplayWidth());
            if(astroid.getX() > gameData.getDisplayWidth()) astroid.setX(0);
            if(astroid.getY() < 0) astroid.setY(gameData.getDisplayHeight());
            if(astroid.getY() > gameData.getDisplayHeight()) astroid.setY(0);
            
            
        }
        setShape(world);
    }
    
    
    private void setShape(World world) {
        for(Entity asteroid : world.getEntities(EntityType.ASTEROIDS)){
        float[] shapex = new float[5];
        float[] shapey = new float[5];
        
        float x = asteroid.getX();
        float y = asteroid.getY();
        float radians = asteroid.getRadians();
        
        shapex[0] = (float) (x + Math.cos(radians) * asteroid.getSize());
        shapey[0] = (float) (y + Math.sin(radians) * asteroid.getSize());
        
        shapex[1] = (float) (x + Math.cos(radians - 1) * asteroid.getSize());
        shapey[1] = (float) (y + Math.sin(radians - 1) * asteroid.getSize());
        
        shapex[2] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * asteroid.getSize());
	shapey[2] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * asteroid.getSize());
        
        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * asteroid.getSize());
	shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * asteroid.getSize());
        
        shapex[4] = (float) (x + Math.cos(radians + 1) * asteroid.getSize());
        shapey[4] = (float) (y + Math.sin(radians + 1) * asteroid.getSize());
        
        asteroid.setShapeX(shapex);
        asteroid.setShapeY(shapey);
        }
    }
    
}

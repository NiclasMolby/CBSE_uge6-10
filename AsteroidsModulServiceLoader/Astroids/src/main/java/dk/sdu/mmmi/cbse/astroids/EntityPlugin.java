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
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;

/**
 *
 * @author niclasmolby
 */
public class EntityPlugin implements IGamePluginService {

    private Entity astroid;
    private World world;
    
    @Override
    public void start(GameData gameData, World world) {
        Random rand = new Random();
        this.world = world;
        if(!gameData.getEvents().isEmpty()){
            for(Event event: gameData.getEvents()){
                if(event.getType() == EventType.ASTEROID_SPLIT){
                    astroid = createSmallAstroid(gameData, world);
                    setShape();
                    world.addEntity(astroid);
                }
            }
        }
        else {
            for(int i = 0; i < rand.nextInt(5)+1; i++){
                astroid = createAstroid(gameData, world);
                setShape();
                world.addEntity(astroid);
            }
        }
  
    }
    
    private Entity createSmallAstroid(GameData gameData, World world){
        Entity astroid = new Entity();
        Random rand = new Random();
        astroid.setType(EntityType.ASTEROIDS);
        
        
        for(Event event: gameData.getEvents()){
            if(event.getType() == EventType.ASTEROID_SPLIT){
                for(Entity e : world.getEntities(EntityType.ASTEROIDS)){
                    if(e.getID().equals(event.getEntityID())){
                        
                        astroid.setRadians(rand.nextInt(5) + rand.nextFloat());
                        astroid.setRotationSpeed(1);
                        astroid.setDirection(astroid.getRadians());
                        astroid.setMaxSpeed(50);
                        astroid.setDx((float) Math.cos(astroid.getRadians()) * astroid.getMaxSpeed());
                        astroid.setDy((float) Math.sin(astroid.getRadians()) * astroid.getMaxSpeed());  
                        astroid.setPosition(e.getX(), e.getY());
                        astroid.setLife(e.getLife()); 
                        astroid.setRadius(8);
                        astroid.setSize(8);
                        
                    }
                }
            }
        }
        
        
        return astroid;
    }
    
    private Entity createAstroid(GameData gameData, World world){
        Entity astroid = new Entity();
        Random rand = new Random();
        astroid.setType(EntityType.ASTEROIDS);

        
        astroid.setPosition(rand.nextInt(gameData.getDisplayWidth()), rand.nextInt(gameData.getDisplayHeight()));
        astroid.setRadians(rand.nextInt(5) + rand.nextFloat());
        astroid.setRotationSpeed(1);
        astroid.setRadius(16);
        astroid.setDirection(astroid.getRadians());
        astroid.setLife(2);
        astroid.setMaxSpeed(50);
        astroid.setSize(16);
        astroid.setDx((float) Math.cos(astroid.getRadians()) * astroid.getMaxSpeed());
        astroid.setDy((float) Math.sin(astroid.getRadians()) * astroid.getMaxSpeed());      
                
        
        
        return astroid;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(astroid);
    }

    @Override
    public void setShape() {
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

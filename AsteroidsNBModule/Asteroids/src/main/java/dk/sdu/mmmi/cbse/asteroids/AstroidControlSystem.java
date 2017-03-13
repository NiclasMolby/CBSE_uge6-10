package dk.sdu.mmmi.cbse.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.EventType;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author niclasmolby
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)
})
public class AstroidControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for(Event event : gameData.getEvents()){
                if(event.getType() == EventType.ASTEROID_SPLIT){
                    IGamePluginService asteroid1 = new dk.sdu.mmmi.cbse.asteroids.EntityPlugin();
                    IGamePluginService asteroid2 = new dk.sdu.mmmi.cbse.asteroids.EntityPlugin();
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
    }
    
}

package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.common.data.Entity;
import static dk.sdu.mmmi.cbse.common.data.EntityType.PLAYER;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class)
})
public class EntityPlugin implements IGamePluginService {

    private Entity player;

    public EntityPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        player = createPlayerShip(gameData);
        setShape();
        world.addEntity(player);
    }

    private Entity createPlayerShip(GameData gameData) {

        Entity playerShip = new Entity();
        playerShip.setType(PLAYER);

        playerShip.setPosition(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);

        playerShip.setMaxSpeed(300);
        playerShip.setAcceleration(200);
        playerShip.setDeacceleration(10);
        playerShip.setLife(3);
        playerShip.setRadius(8);

        playerShip.setRadians(3.1415f / 2);
        playerShip.setRotationSpeed(3);
        
        return playerShip;
    }
    
    public void setShape(){
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        float x = player.getX();
        float y = player.getY();
        float radians = player.getRadians();
        
        shapex[0] = (float) (x + Math.cos(radians) * 8);
	shapey[0] = (float) (y + Math.sin(radians) * 8);
		
	shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
	shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8);
		
	shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
	shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);
		
	shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
	shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);
        
        player.setShapeX(shapex);
        player.setShapeY(shapey);
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }

}

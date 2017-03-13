package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import static dk.sdu.mmmi.cbse.common.data.EntityType.ENEMY;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EntityPlugin implements IGamePluginService {

    private Entity enemy;

    public EntityPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        enemy = createEnemyShip(gameData);
        setShape();
        world.addEntity(enemy);
    }

    private Entity createEnemyShip(GameData gameData) {

        Entity enemyShip = new Entity();
        enemyShip.setType(ENEMY);

        enemyShip.setPosition(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);

        enemyShip.setMaxSpeed(300);
        enemyShip.setAcceleration(200);
        enemyShip.setDeacceleration(10);

        enemyShip.setRadians(3.1415f / 2);
        enemyShip.setRotationSpeed(3);
        
        enemyShip.setLife(3);
        enemyShip.setRadius(8);

        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemy);
    }
    
    @Override
    public void setShape(){
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        float x = enemy.getX();
        float y = enemy.getY();
        float radians = enemy.getRadians();
        
        shapex[0] = (float) (x + Math.cos(radians) * 8);
	shapey[0] = (float) (y + Math.sin(radians) * 8);
		
	shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
	shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8);
		
	shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
	shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);
		
	shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
	shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);
        
        enemy.setShapeX(shapex);
        enemy.setShapeY(shapey);
    }

}

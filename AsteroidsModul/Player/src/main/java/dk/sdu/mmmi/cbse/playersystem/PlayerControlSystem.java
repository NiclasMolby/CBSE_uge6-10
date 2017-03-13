package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.EventType;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

/**
 *
 * @author jcs
 */
public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        // TODO: Implement entity processor
        for(Entity player : world.getEntities(EntityType.PLAYER)){
           
            if(gameData.getKeys().isPressed(GameKeys.SPACE)){
                gameData.addEvent(new Event(EventType.PLAYER_SHOOT, player.getID()));
            }

            if(gameData.getKeys().isDown(GameKeys.RIGHT)) {
                player.setRadians(player.getRadians()-player.getRotationSpeed() * gameData.getDelta());
            }

            if(gameData.getKeys().isDown(GameKeys.LEFT)) {
                player.setRadians(player.getRadians()+player.getRotationSpeed() * gameData.getDelta());
            }

            if(gameData.getKeys().isDown(GameKeys.UP)) {
                player.setDx(player.getDx() + ((float) Math.cos(player.getRadians())) * player.getAcceleration() * gameData.getDelta());
                player.setDy(player.getDy() + ((float) Math.sin(player.getRadians())) * player.getAcceleration() * gameData.getDelta());
            }

            float vec = (float) Math.sqrt(player.getDx() * player.getDx() + player.getDy() * player.getDy());

            if(vec > 0){
                player.setDx(player.getDx() - (player.getDx() / vec) * player.getDeacceleration() * gameData.getDelta());
                player.setDy(player.getDy() - (player.getDy() / vec) * player.getDeacceleration() * gameData.getDelta());
            }

            if(vec > player.getMaxSpeed()) {
                player.setDx((player.getDx() / vec) * player.getMaxSpeed());
                player.setDy((player.getDy() / vec) * player.getMaxSpeed());
            }

            player.setX(player.getX() + player.getDx() * gameData.getDelta());
            player.setY(player.getY() + player.getDy() * gameData.getDelta());


            if(player.getX() < 0) player.setX(gameData.getDisplayWidth());
            if(player.getX() > gameData.getDisplayWidth()) player.setX(0);
            if(player.getY() < 0) player.setY(gameData.getDisplayHeight());
            if(player.getY() > gameData.getDisplayHeight()) player.setY(0);
            setShape(player);
        }
        
    }
    
    private void setShape(Entity player){
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
}

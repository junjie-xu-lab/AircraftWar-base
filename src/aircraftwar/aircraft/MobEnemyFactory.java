package aircraftwar.aircraft;

import aircraftwar.application.ImageManager;
import aircraftwar.application.Main;

/**
 * Creates basic mob enemies.
 */
public class MobEnemyFactory implements IEnemyFactory {

    @Override
    public AbstractAircraft createEnemy() {
        return new MobEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0,
                10,
                30
        );
    }
}


package aircraftwar.aircraft;

import aircraftwar.application.ImageManager;
import aircraftwar.application.Main;

/**
 * Creates advanced elite enemies.
 */
public class ElitePlusEnemyFactory implements IEnemyFactory {

    @Override
    public AbstractAircraft createEnemy() {
        return new ElitePlusEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_PLUS_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                2,
                8,
                70
        );
    }
}


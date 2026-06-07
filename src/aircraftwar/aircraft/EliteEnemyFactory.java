package aircraftwar.aircraft;

import aircraftwar.application.ImageManager;
import aircraftwar.application.Main;

/**
 * Creates standard elite enemies.
 */
public class EliteEnemyFactory implements IEnemyFactory {

    @Override
    public AbstractAircraft createEnemy() {
        return new EliteEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0,
                10,
                50
        );
    }
}


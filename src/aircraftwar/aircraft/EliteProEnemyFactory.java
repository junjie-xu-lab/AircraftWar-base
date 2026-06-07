package aircraftwar.aircraft;

import aircraftwar.application.ImageManager;
import aircraftwar.application.Main;

/**
 * Creates ace enemies.
 */
public class EliteProEnemyFactory implements IEnemyFactory {

    @Override
    public AbstractAircraft createEnemy() {
        return new EliteProEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_PRO_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                3,
                6,
                100
        );
    }
}


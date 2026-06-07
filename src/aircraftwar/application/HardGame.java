package aircraftwar.application;

import aircraftwar.aircraft.AbstractAircraft;
import aircraftwar.aircraft.BossEnemy;
import aircraftwar.dao.ScoreDAOImpl;

/**
 * Hard mode increases enemy pressure faster and scales boss health.
 */
public class HardGame extends GamePanel {

    private final int bossBaseHp = 300;
    private final int bossScoreStep = 300;
    private int nextBossScore = 300;

    private double bossHpMultiplier = 1.0;
    private double gameProgression = 1.0;
    private int progressionCounter = 0;

    private final double baseEnemySpawnCycle = 15;
    private final double baseHeroShootCycle = 15;
    private final double baseEnemyShootCycle = 15;

    public HardGame() {
        super();
    }

    @Override
    protected void initializeSettings() {
        this.difficulty = 3;
        this.enemyMaxNumber = 8;

        this.enemySpawnCycle = baseEnemySpawnCycle;
        this.heroShootCycle = baseHeroShootCycle;
        this.enemyShootCycle = baseEnemyShootCycle;

        this.bossSpawned = false;
        this.nextBossScore = bossScoreStep;

        this.gameProgression = 1.0;
        this.bossHpMultiplier = 1.0;
        this.progressionCounter = 0;

        this.scoreDAO = new ScoreDAOImpl(3);

        System.out.println("Hard mode initialized.");
    }

    @Override
    protected void spawnEnemy() {
        int factoryIndex = (int) (Math.random() * enemyFactories.length);
        AbstractAircraft enemy = enemyFactories[factoryIndex].createEnemy();

        enemy.applyDifficultyBonus(gameProgression, gameProgression);

        enemyAircrafts.add(enemy);
    }

    @Override
    protected void checkBossSpawn() {
        if (score >= nextBossScore && !hasActiveBoss()) {
            int currentBossHp = (int) (bossBaseHp * bossHpMultiplier);

            AbstractAircraft boss = new BossEnemy(
                    Main.WINDOW_WIDTH / 2,
                    (int) (Main.WINDOW_HEIGHT * 0.05),
                    2,
                    3,
                    currentBossHp
            );
            enemyAircrafts.add(boss);

            System.out.println("Hard mode boss spawned.");
            System.out.printf("Boss HP: %d, boss HP multiplier: %.2f%n", currentBossHp, bossHpMultiplier);
            AudioManager.getInstance().playBossMusic();

            nextBossScore += bossScoreStep;

            bossHpMultiplier += 0.2;
            if (bossHpMultiplier > 2.0) {
                bossHpMultiplier = 2.0;
            }

            bossSpawned = true;
        }
    }

    @Override
    protected void applyDifficultyProgression() {
        progressionCounter++;

        if (progressionCounter >= 250) {
            progressionCounter = 0;

            gameProgression += 0.08;
            if (gameProgression > 2.0) {
                gameProgression = 2.0;
            }

            enemySpawnCycle = Math.max(6, baseEnemySpawnCycle / gameProgression);

            heroShootCycle = Math.min(35, baseHeroShootCycle * gameProgression);
            enemyShootCycle = Math.max(6, baseEnemyShootCycle / gameProgression);

            System.out.printf(
                    "Hard mode increased. Enemy spawn cycle: %.2f, enemy multiplier: %.2f, hero shoot cycle: %.2f, enemy shoot cycle: %.2f, boss HP multiplier: %.2f%n",
                    enemySpawnCycle,
                    gameProgression,
                    heroShootCycle,
                    enemyShootCycle,
                    bossHpMultiplier
            );
        }
    }
}



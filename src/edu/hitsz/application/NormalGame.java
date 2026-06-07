package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.dao.ScoreDAOImpl;

/**
 * Normal mode adds timed difficulty growth and recurring bosses.
 */
public class NormalGame extends GamePanel {

    private final int bossHp = 300;
    private final int bossScoreStep = 500;
    private int nextBossScore = 500;

    private final double baseEnemySpawnCycle = 20;
    private double gameProgression = 1.0;
    private int progressionCounter = 0;

    public NormalGame() {
        super();
    }

    @Override
    protected void initializeSettings() {
        this.difficulty = 2;
        this.enemyMaxNumber = 5;
        this.enemySpawnCycle = baseEnemySpawnCycle;
        this.heroShootCycle = 20;
        this.enemyShootCycle = 20;
        this.bossSpawned = false;

        this.nextBossScore = bossScoreStep;
        this.gameProgression = 1.0;
        this.progressionCounter = 0;

        this.scoreDAO = new ScoreDAOImpl(2);

        System.out.println("Normal mode initialized.");
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
            AbstractAircraft boss = new BossEnemy(
                    Main.WINDOW_WIDTH / 2,
                    (int) (Main.WINDOW_HEIGHT * 0.05),
                    2,
                    3,
                    bossHp
            );
            enemyAircrafts.add(boss);

            System.out.println("Normal mode boss spawned.");
            AudioManager.getInstance().playBossMusic();

            nextBossScore += bossScoreStep;
            bossSpawned = true;
        }
    }

    @Override
    protected void applyDifficultyProgression() {
        progressionCounter++;

        if (progressionCounter >= 250) {
            progressionCounter = 0;

            gameProgression += 0.05;
            if (gameProgression > 1.5) {
                gameProgression = 1.5;
            }

            enemySpawnCycle = Math.max(8, baseEnemySpawnCycle / gameProgression);

            System.out.printf(
                    "Normal mode increased. Enemy spawn cycle: %.2f, enemy multiplier: %.2f%n",
                    enemySpawnCycle,
                    gameProgression
            );
        }
    }
}



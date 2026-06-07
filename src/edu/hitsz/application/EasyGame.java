package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.EliteEnemyFactory;
import edu.hitsz.aircraft.MobEnemyFactory;
import edu.hitsz.dao.ScoreDAOImpl;

/**
 * Easy mode keeps the enemy count low and does not spawn bosses.
 */
public class EasyGame extends GamePanel {

    public EasyGame() {
        super();
    }

    @Override
    protected void initializeSettings() {
        this.difficulty = 1;
        this.enemyMaxNumber = 3;
        this.enemySpawnCycle = 25;
        this.heroShootCycle = 25;
        this.enemyShootCycle = 25;
        this.bossSpawned = true;
        this.scoreDAO = new ScoreDAOImpl(1);
        System.out.println("Easy mode initialized.");
    }

    @Override
    protected void spawnEnemy() {
        int rand = (int) (Math.random() * 2);
        AbstractAircraft enemy;
        if (rand == 0) {
            enemy = new MobEnemyFactory().createEnemy();
        } else {
            enemy = new EliteEnemyFactory().createEnemy();
        }
        enemyAircrafts.add(enemy);
    }

    @Override
    protected void checkBossSpawn() {
    }

    @Override
    protected void applyDifficultyProgression() {
    }
}


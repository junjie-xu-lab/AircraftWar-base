package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.SpreadShootStrategy;

public class BulletProp extends BaseProp {

    private static final int DURATION = 5000;

    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public void activate(HeroAircraft hero) {
        System.out.println("Spread fire active for " + DURATION + " ms.");
        int version = hero.activateTemporaryShootStrategy(new SpreadShootStrategy());

        Runnable restoreTask = () -> {
            try {
                Thread.sleep(DURATION);
                hero.restoreShootStrategyIfCurrent(version);
                System.out.println("Spread fire ended.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread restoreThread = new Thread(restoreTask);
        restoreThread.setDaemon(true);
        restoreThread.start();
    }
}

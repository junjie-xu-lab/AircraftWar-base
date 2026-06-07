package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * Basic enemy that moves downward and does not shoot.
 */
public class MobEnemy extends AbstractAircraft {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        if (isFrozen) {
            updateFreezeTimer();
            return;
        }

        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }

    @Override
    public void onBombActivated() {
        System.out.println("Mob enemy destroyed by bomb.");
        vanish();
    }

    @Override
    public void onFreezeActivated() {
        System.out.println("Mob enemy frozen.");
        activateFreezeEffect(Integer.MAX_VALUE);
    }

}

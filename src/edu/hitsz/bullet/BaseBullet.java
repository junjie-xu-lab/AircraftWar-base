package edu.hitsz.bullet;

import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;

/**
 * Base type for all bullets.
 */
public abstract class BaseBullet extends AbstractFlyingObject {

    private int power = 0;

    public BaseBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY);
        this.power = power;
    }

    @Override
    public void forward() {
        super.forward();

        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            vanish();
        }

        if (speedY > 0 && locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }else if (locationY <= 0){
            vanish();
        }
    }

    public int getPower() {
        return power;
    }
}

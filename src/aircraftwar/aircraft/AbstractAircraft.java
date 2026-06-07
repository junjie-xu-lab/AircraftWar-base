package aircraftwar.aircraft;

import aircraftwar.bullet.BaseBullet;
import aircraftwar.basic.AbstractFlyingObject;
import aircraftwar.prop.IPropObserver;

import java.util.List;

/**
 * Base class for player and enemy aircraft.
 */
public abstract class AbstractAircraft extends AbstractFlyingObject implements IPropObserver {

    protected int maxHp;
    protected int hp;

    protected boolean isFrozen = false;
    protected int freezeTimer = 0;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    protected void activateFreezeEffect(int duration) {
        this.isFrozen = true;
        this.freezeTimer = duration;
    }

    protected void updateFreezeTimer() {
        if (isFrozen && freezeTimer > 0) {
            freezeTimer--;
            if (freezeTimer <= 0) {
                isFrozen = false;
                System.out.println(getClass().getSimpleName() + " freeze effect ended.");
            }
        }
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public void increaseHp(int amount) {
        hp = Math.min(maxHp, hp + Math.max(0, amount));
    }

    public int getHp() {
        return hp;
    }

    public void applyDifficultyBonus(double hpRate, double speedRate) {
        this.maxHp = Math.max(1, (int) Math.round(this.maxHp * hpRate));
        this.hp = Math.max(1, (int) Math.round(this.hp * hpRate));

        if (speedX != 0) {
            int signX = speedX > 0 ? 1 : -1;
            speedX = signX * Math.max(1, (int) Math.round(Math.abs(speedX) * speedRate));
        }

        if (speedY != 0) {
            int signY = speedY > 0 ? 1 : -1;
            speedY = signY * Math.max(1, (int) Math.round(Math.abs(speedY) * speedRate));
        }
    }


    public abstract List<BaseBullet> shoot();

}




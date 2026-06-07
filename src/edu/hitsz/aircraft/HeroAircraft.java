package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.strategy.IShootStrategy;
import edu.hitsz.strategy.ShootContext;
import edu.hitsz.strategy.StraightShootStrategy;

import java.util.List;

/**
 * Player-controlled aircraft.
 */
public class HeroAircraft extends AbstractAircraft {

    private static volatile HeroAircraft instance = null;

    private int shootNum = 1;
    private int power = 30;
    private int direction = -1;

    private ShootContext shootContext;
    private int shootBuffVersion = 0;

    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootContext = new ShootContext(new StraightShootStrategy());
    }

    public static HeroAircraft getInstance(int locationX, int locationY, int speedX, int speedY, int hp) {
        if (instance == null) {
            synchronized (HeroAircraft.class) {
                if (instance == null) {
                    instance = new HeroAircraft(locationX, locationY, speedX, speedY, hp);
                }
            }
        }
        return instance;
    }

    public static HeroAircraft getInstance() {
        if (instance == null) {
            throw new IllegalStateException("HeroAircraft has not been initialized!");
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    public void setShootStrategy(IShootStrategy strategy) {
        this.shootContext.setStrategy(strategy);
    }

    public synchronized int activateTemporaryShootStrategy(IShootStrategy strategy) {
        shootBuffVersion++;
        setShootStrategy(strategy);
        return shootBuffVersion;
    }

    public synchronized void restoreShootStrategyIfCurrent(int version) {
        if (version == shootBuffVersion) {
            setShootStrategy(new StraightShootStrategy());
        }
    }

    @Override
    public void forward() {
    }

    @Override
    public List<BaseBullet> shoot() {
        return shootContext.execute(this, power);
    }
    @Override
    public void onBombActivated() {
    }

    @Override
    public void onFreezeActivated() {
    }
}









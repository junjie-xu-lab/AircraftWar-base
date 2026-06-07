package edu.hitsz.prop;

/**
 * Simple factory for collectible props.
 */
public class PropFactory {

    public static IProp createProp(String type, int x, int y) {
        int speedX = 0;
        int speedY = 5;

        switch (type) {
            case "blood":
                return new BloodProp(x, y, speedX, speedY);
            case "bullet":
                return new BulletProp(x, y, speedX, speedY);
            case "bulletPlus":
                return new BulletPlusProp(x, y, speedX, speedY);
            case "freeze":
                return new FreezeProp(x, y, speedX, speedY);
            case "bomb":
                return new BombProp(x, y, speedX, speedY);
            default:
                throw new IllegalArgumentException("Unknown prop type: " + type);
        }
    }
}


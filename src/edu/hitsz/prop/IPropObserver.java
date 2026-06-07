package edu.hitsz.prop;

/**
 * Observer interface for props that affect enemies and enemy bullets.
 */
public interface IPropObserver {

    void onBombActivated();

    void onFreezeActivated();
}

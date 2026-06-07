package edu.hitsz.aircraft;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    private HeroAircraft hero;

    @BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class ---**");
        HeroAircraft.resetInstance();
        hero = HeroAircraft.getInstance(100, 200, 0, 0, 100);
    }

    @AfterEach
    void tearDown() {
        System.out.println("**--- Executed after each test method in this class ---**");
        hero = null;
    }

    @Test
    void notValid() {
        System.out.println("**--- Test notValid method executed ---**");
        assertFalse(hero.notValid(), "A new aircraft should be valid.");
        hero.vanish();
        assertTrue(hero.notValid(), "vanish should mark the aircraft invalid.");
    }

    @Test
    void decreaseHp() {
        System.out.println("**--- Test decreaseHp method executed ---**");
        int initialHp = hero.getHp();
        hero.decreaseHp(30);
        assertEquals(initialHp - 30, hero.getHp(), "HP should decrease correctly.");

        hero.decreaseHp(200);
        assertEquals(0, hero.getHp(), "HP should not be lower than 0.");
        assertTrue(hero.notValid(), "The aircraft should be invalid when HP reaches 0.");
    }

    @Test
    void getHp() {
        System.out.println("**--- Test getHp method executed ---**");
        assertEquals(100, hero.getHp(), "Initial HP should be 100.");

        hero.decreaseHp(50);
        assertEquals(50, hero.getHp(), "HP should be 50 after taking 50 damage.");
    }
}

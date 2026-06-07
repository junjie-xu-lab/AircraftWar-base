package aircraftwar.application;

import aircraftwar.aircraft.HeroAircraft;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Handles mouse dragging for the hero aircraft.
 */
public class HeroController {
    private JPanel gamePanel;
    private HeroAircraft heroAircraft;
    private MouseAdapter mouseAdapter;

    public HeroController(JPanel gamePanel, HeroAircraft heroAircraft){
        this.gamePanel = gamePanel;
        this.heroAircraft = heroAircraft;

        mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                int x = e.getX();
                int y = e.getY();
                if ( x<0 || x>Main.WINDOW_WIDTH || y<0 || y>Main.WINDOW_HEIGHT){
                    return;
                }
                heroAircraft.setLocation(x, y);
            }
        };

        gamePanel.addMouseListener(mouseAdapter);
        gamePanel.addMouseMotionListener(mouseAdapter);
    }


}


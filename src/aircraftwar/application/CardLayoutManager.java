package aircraftwar.application;

import javax.swing.*;
import java.awt.*;

public class CardLayoutManager {

    private static CardLayoutManager instance = null;
    private static final String CURRENT_CARD = "current";

    public final CardLayout cardLayout = new CardLayout(0, 0);
    public final JPanel cardPanel = new JPanel(cardLayout);

    private CardLayoutManager() {
    }

    public static CardLayoutManager getInstance() {
        if (instance == null) {
            synchronized (CardLayoutManager.class) {
                if (instance == null) {
                    instance = new CardLayoutManager();
                }
            }
        }
        return instance;
    }

    public void showPanel(JPanel panel) {
        cardPanel.removeAll();
        cardPanel.add(panel, CURRENT_CARD);
        cardLayout.show(cardPanel, CURRENT_CARD);
        cardPanel.revalidate();
        cardPanel.repaint();
        panel.requestFocusInWindow();
    }
}




package aircraftwar.application;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowUI);
    }

    private static void createAndShowUI() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CardLayoutManager manager = CardLayoutManager.getInstance();
        frame.add(manager.cardPanel);

        DifficultyMenu difficultyMenu = new DifficultyMenu();
        manager.showPanel(difficultyMenu.getMainPanel());

        frame.setVisible(true);
    }
}


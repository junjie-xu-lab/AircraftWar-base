package aircraftwar.application;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class DifficultyMenu {

    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JLabel titleLabel = new JLabel("", JLabel.CENTER);
    private final JLabel chooseModeLabel = new JLabel("", JLabel.CENTER);
    private final JRadioButton chineseButton = new JRadioButton("中文版本");
    private final JRadioButton englishButton = new JRadioButton("English Version");
    private final JButton easyButton = new JButton();
    private final JButton normalButton = new JButton();
    private final JButton hardButton = new JButton();

    public DifficultyMenu() {
        buildUI();
        bindActions();
        updateLanguageText();
    }

    private void buildUI() {
        mainPanel.setBackground(new Color(18, 30, 45));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(90, 70, 90, 70));

        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        ButtonGroup languageGroup = new ButtonGroup();
        languageGroup.add(chineseButton);
        languageGroup.add(englishButton);
        chineseButton.setSelected(I18n.getLanguage() == Language.ZH);
        englishButton.setSelected(I18n.getLanguage() == Language.EN);

        JPanel languagePanel = new JPanel();
        languagePanel.setOpaque(false);
        chineseButton.setOpaque(false);
        englishButton.setOpaque(false);
        chineseButton.setForeground(Color.WHITE);
        englishButton.setForeground(Color.WHITE);
        languagePanel.add(chineseButton);
        languagePanel.add(englishButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12, 0, 12, 0);
        gbc.weightx = 1;

        gbc.gridy = 0;
        centerPanel.add(languagePanel, gbc);

        chooseModeLabel.setForeground(Color.WHITE);
        chooseModeLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        gbc.gridy = 1;
        centerPanel.add(chooseModeLabel, gbc);

        styleDifficultyButton(easyButton);
        styleDifficultyButton(normalButton);
        styleDifficultyButton(hardButton);

        gbc.gridy = 2;
        centerPanel.add(easyButton, gbc);
        gbc.gridy = 3;
        centerPanel.add(normalButton, gbc);
        gbc.gridy = 4;
        centerPanel.add(hardButton, gbc);
    }

    private void bindActions() {
        chineseButton.addActionListener(e -> {
            I18n.setLanguage(Language.ZH);
            updateLanguageText();
        });
        englishButton.addActionListener(e -> {
            I18n.setLanguage(Language.EN);
            updateLanguageText();
        });

        easyButton.addActionListener(e -> startGame(1));
        normalButton.addActionListener(e -> startGame(2));
        hardButton.addActionListener(e -> startGame(3));
    }

    private void updateLanguageText() {
        titleLabel.setText(I18n.text("title"));
        chooseModeLabel.setText(I18n.text("chooseMode"));
        easyButton.setText(I18n.text("easy"));
        normalButton.setText(I18n.text("normal"));
        hardButton.setText(I18n.text("hard"));
    }

    private void styleDifficultyButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 20));
        button.setFocusPainted(false);
        button.setBackground(new Color(236, 244, 255));
        button.setForeground(new Color(20, 39, 62));
    }

    private void startGame(int difficulty) {
        GamePanel game = createGame(difficulty);
        game.setBackgroundImage(backgroundForDifficulty(difficulty));
        CardLayoutManager.getInstance().showPanel(game);
        game.startGame();
    }

    private GamePanel createGame(int difficulty) {
        switch (difficulty) {
            case 1:
                return new EasyGame();
            case 2:
                return new NormalGame();
            case 3:
                return new HardGame();
            default:
                throw new IllegalArgumentException("Unsupported difficulty: " + difficulty);
        }
    }

    private String backgroundForDifficulty(int difficulty) {
        switch (difficulty) {
            case 1:
                return "images/bg.jpg";
            case 2:
                return "images/bg3.jpg";
            case 3:
                return "images/bg5.jpg";
            default:
                return "images/bg.jpg";
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}


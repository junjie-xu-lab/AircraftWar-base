package aircraftwar.application;

import aircraftwar.dao.ScoreDAOImpl;
import aircraftwar.dao.ScoreRecord;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

public class LeaderboardUI {

    private final JPanel mainPanel = new JPanel(new BorderLayout(0, 18));
    private final JLabel headerLabel = new JLabel("", JLabel.CENTER);
    private final JTable scoreTable = new JTable();
    private final JButton deleteButton = new JButton();
    private final JButton playAgainButton = new JButton();
    private final JButton backButton = new JButton();
    private final JButton exitButton = new JButton();

    private final ScoreDAOImpl scoreDAO;
    private DefaultTableModel tableModel;

    public LeaderboardUI(int difficulty, Runnable playAgainAction, Runnable backToMenuAction) {
        this.scoreDAO = new ScoreDAOImpl(difficulty);
        buildUI(playAgainAction, backToMenuAction);
        refreshText();
        refreshTable();
    }

    private void buildUI(Runnable playAgainAction, Runnable backToMenuAction) {
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 35, 40, 35));
        mainPanel.setBackground(new Color(18, 30, 45));

        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        scoreTable.setRowHeight(28);
        scoreTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        scoreTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        mainPanel.add(new JScrollPane(scoreTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(deleteButton);
        buttonPanel.add(playAgainButton);
        buttonPanel.add(backButton);
        buttonPanel.add(exitButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        deleteButton.addActionListener(e -> deleteSelectedRow());
        playAgainButton.addActionListener(e -> playAgainAction.run());
        backButton.addActionListener(e -> backToMenuAction.run());
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void refreshText() {
        headerLabel.setText(I18n.text("leaderboard"));
        deleteButton.setText(I18n.text("delete"));
        playAgainButton.setText(I18n.text("playAgain"));
        backButton.setText(I18n.text("backMenu"));
        exitButton.setText(I18n.text("exit"));
    }

    private void initTableModel() {
        String[] columnNames = {
                I18n.text("rank"),
                I18n.text("player"),
                I18n.text("score"),
                I18n.text("time")
        };

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        scoreTable.setModel(tableModel);
    }

    private void deleteSelectedRow() {
        int selectedRow = scoreTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showOptionDialog(
                    mainPanel,
                    I18n.text("selectRecord"),
                    I18n.text("notice"),
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    new Object[]{I18n.text("ok")},
                    I18n.text("ok")
            );
            return;
        }

        int result = JOptionPane.showOptionDialog(
                mainPanel,
                I18n.text("confirmDelete"),
                I18n.text("confirmDeleteTitle"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{I18n.text("yes"), I18n.text("no")},
                I18n.text("no")
        );

        if (result == 0) {
            String playerName = (String) tableModel.getValueAt(selectedRow, 1);
            int score = Integer.parseInt(String.valueOf(tableModel.getValueAt(selectedRow, 2)));
            String recordTime = (String) tableModel.getValueAt(selectedRow, 3);

            ScoreRecord record = new ScoreRecord();
            record.setPlayerName(playerName);
            record.setScore(score);
            record.setRecordTime(recordTime);

            scoreDAO.delete(record);
            JOptionPane.showOptionDialog(
                    mainPanel,
                    I18n.text("deleted"),
                    I18n.text("notice"),
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{I18n.text("ok")},
                    I18n.text("ok")
            );
            refreshTable();
        }
    }

    public void refreshTable() {
        initTableModel();
        List<ScoreRecord> records = scoreDAO.getLeaderboard();

        for (int i = 0; i < records.size(); i++) {
            ScoreRecord record = records.get(i);
            Object[] row = {
                    i + 1,
                    record.getPlayerName(),
                    record.getScore(),
                    record.getRecordTime()
            };
            tableModel.addRow(row);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}


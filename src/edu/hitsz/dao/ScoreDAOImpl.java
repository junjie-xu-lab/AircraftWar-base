package edu.hitsz.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreDAOImpl implements ScoreDAO {

    private final List<ScoreRecord> scoreList;
    private final String filePath;
    private final int difficulty;

    public ScoreDAOImpl(int difficulty) {
        this.difficulty = difficulty;
        this.filePath = "scores_difficulty_" + difficulty + ".tsv";
        this.scoreList = loadFromFile();
    }

    @Override
    public List<ScoreRecord> getAll() {
        return new ArrayList<>(scoreList);
    }

    @Override
    public ScoreRecord getOne(int rank) {
        if (rank < 1 || rank > scoreList.size()) {
            return null;
        }
        return getLeaderboard().get(rank - 1);
    }

    @Override
    public void add(ScoreRecord record) {
        scoreList.add(record);
        saveToFile();
    }

    @Override
    public void delete(ScoreRecord record) {
        scoreList.remove(record);
        saveToFile();
    }

    @Override
    public List<ScoreRecord> getLeaderboard() {
        List<ScoreRecord> sorted = new ArrayList<>(scoreList);
        Collections.sort(sorted);
        return sorted;
    }

    private List<ScoreRecord> loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        List<ScoreRecord> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ScoreRecord record = parseRecord(line);
                if (record != null) {
                    records.add(record);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load score records: " + e.getMessage());
        }
        return records;
    }

    private ScoreRecord parseRecord(String line) {
        String[] parts = line.split("\t", 4);
        if (parts.length < 4) {
            return null;
        }

        try {
            ScoreRecord record = new ScoreRecord();
            record.setPlayerName(unescape(parts[0]));
            record.setScore(Integer.parseInt(parts[1]));
            record.setDifficulty(Integer.parseInt(parts[2]));
            record.setRecordTime(unescape(parts[3]));
            return record;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (ScoreRecord record : scoreList) {
                writer.write(escape(record.getPlayerName()));
                writer.write("\t");
                writer.write(String.valueOf(record.getScore()));
                writer.write("\t");
                writer.write(String.valueOf(difficulty));
                writer.write("\t");
                writer.write(escape(record.getRecordTime()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to save score records: " + e.getMessage());
        }
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\").replace("\t", "\\t");
    }

    private String unescape(String value) {
        StringBuilder result = new StringBuilder();
        boolean escaping = false;
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (escaping) {
                result.append(ch);
                escaping = false;
            } else if (ch == '\\') {
                escaping = true;
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public void printLeaderboard() {
        List<ScoreRecord> leaderboard = getLeaderboard();

        System.out.println("\n****************************************");
        System.out.println("              Leaderboard");
        System.out.println("****************************************");

        if (leaderboard.isEmpty()) {
            System.out.println("No records.");
        } else {
            for (int i = 0; i < leaderboard.size(); i++) {
                ScoreRecord record = leaderboard.get(i);
                System.out.printf("#%d: %s%n", i + 1, record.toString());
            }
        }

        System.out.println("****************************************\n");
    }
}

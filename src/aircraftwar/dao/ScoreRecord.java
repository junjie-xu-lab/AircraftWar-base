package aircraftwar.dao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ScoreRecord implements Serializable, Comparable<ScoreRecord> {

    private static final long serialVersionUID = 1L;

    private String playerName;
    private int score;
    private String recordTime;
    private int difficulty;

    public ScoreRecord() {
    }

    public ScoreRecord(String playerName, int score, int difficulty) {
        this.playerName = playerName;
        this.score = score;
        this.difficulty = difficulty;
        this.recordTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd HH:mm"));
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public int compareTo(ScoreRecord other) {
        return Integer.compare(other.score, this.score);
    }

    @Override
    public String toString() {
        return playerName + "," + score + "," + recordTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScoreRecord)) {
            return false;
        }
        ScoreRecord that = (ScoreRecord) o;
        return score == that.score
                && Objects.equals(playerName, that.playerName)
                && Objects.equals(recordTime, that.recordTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, score, recordTime);
    }
}


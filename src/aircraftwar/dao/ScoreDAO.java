package aircraftwar.dao;

import java.util.List;

public interface ScoreDAO {

    List<ScoreRecord> getAll();

    ScoreRecord getOne(int rank);

    void add(ScoreRecord record);

    void delete(ScoreRecord record);

    List<ScoreRecord> getLeaderboard();
}


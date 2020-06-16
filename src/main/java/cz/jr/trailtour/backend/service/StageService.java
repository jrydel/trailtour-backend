package cz.jr.trailtour.backend.service;

import cz.jr.trailtour.backend.repository.StageRepository;
import cz.jr.trailtour.backend.repository.entities.stage.Stage;
import cz.jr.trailtour.backend.repository.entities.stage.StageData;
import cz.jr.trailtour.backend.repository.entities.stage.StageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Jiří Rýdel on 4/19/20, 1:26 PM
 */
@Service
public class StageService {

    private final StageRepository stageRepository;

    @Autowired
    public StageService(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    public StageData get(String database, int number) throws SQLException {
        return stageRepository.get(database, number);
    }

    public String getStravaData(String database, int number) throws SQLException {
        return stageRepository.getStravaData(database, number);
    }

    public List<Stage> getAll(String database) throws SQLException {
        return stageRepository.getAll(database);
    }

    public List<StageData> getAllData(String database) throws SQLException {
        return stageRepository.getAllData(database);
    }

    public List<Stage> getAllFulltext(String database, String match) throws SQLException {
        return stageRepository.getStagesFulltext(database, match);
    }

    public int save(String database, Stage stage) throws SQLException {
        return stageRepository.save(database, stage);
    }

    public List<StageInfo> getInfo(String database, int number) throws SQLException {
        return stageRepository.getInfo(database, number);
    }

    public int saveInfo(String database, StageInfo stageInfo) throws SQLException {
        return stageRepository.saveInfo(database, stageInfo);
    }
}

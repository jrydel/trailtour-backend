package cz.jr.trailtour.backend.service;

import cz.jr.trailtour.backend.repository.StageRepository;
import cz.jr.trailtour.backend.repository.entities.stage.Stage;
import cz.jr.trailtour.backend.repository.entities.stage.StageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

    public Stage get(String database, int number) throws SQLException {
        return stageRepository.get(database, number);
    }

    public Map<String, Object> getCounts(String database, int number) throws SQLException {
        return stageRepository.getCounts(database, number);
    }

    public String getGPSData(String database, int number) throws SQLException {
        return stageRepository.getGPSData(database, number);
    }

    public Map<String, Object> getGPSStart(String database, int number) throws SQLException {
        return stageRepository.getGPSStart(database, number);
    }

    public List<Stage> getAll(String database) throws SQLException {
        return stageRepository.getAll(database);
    }

    public Map<Integer, Map<String, Object>> getAllCounts(String database) throws SQLException {
        return stageRepository.getAllCounts(database);
    }

    public Map<Integer, String> getAllGPSData(String database) throws SQLException {
        return stageRepository.getAllGPSData(database);
    }

    public Map<Integer, Map<String, Object>> getAllGPSStart(String database) throws SQLException {
        return stageRepository.getAllGPSStart(database);
    }

    public List<Stage> getFulltext(String database, String match) throws SQLException {
        return stageRepository.getFulltext(database, match);
    }

    public List<Map<String, Object>> getResults(String database, int number) throws SQLException {
        return stageRepository.getResults(database, number);
    }

    public List<StageInfo> getInfo(String database, int number) throws SQLException {
        return stageRepository.getInfos(database, number);
    }

    public int saveInfo(String database, StageInfo stageInfo) throws SQLException {
        return stageRepository.saveInfo(database, stageInfo);
    }
}

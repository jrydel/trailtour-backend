package cz.jr.trailtour.backend.service;

import cz.jr.trailtour.backend.repository.StageRepository;
import cz.jr.trailtour.backend.repository.entity.Stage;
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

    public Stage getById(String database, long stageId) throws SQLException {
        return stageRepository.get(database, stageId);
    }

    public Stage getByNumber(String database, int number) throws SQLException {
        Long stageId = stageRepository.getStageId(database, number);
        if (stageId == null) {
            return null;
        }
        return getById(database, stageId);
    }

    public List<Stage> getAll(String database) throws SQLException {
        return stageRepository.getAll(database);
    }

    public int save(String database, Stage stage) throws SQLException {
        return stageRepository.save(database, stage);
    }
}

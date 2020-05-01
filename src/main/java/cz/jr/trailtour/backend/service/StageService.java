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

    public List<Stage> getAll() throws SQLException {
        return stageRepository.getAll();
    }

    public int save(Stage stage) throws SQLException {
        return stageRepository.save(stage);
    }
}

package cz.jr.trailtour.backend.service;

import cz.jr.trailtour.backend.repository.AthleteRepository;
import cz.jr.trailtour.backend.repository.ResultRepository;
import cz.jr.trailtour.backend.repository.StageRepository;
import cz.jr.trailtour.backend.repository.entities.Result;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiří Rýdel on 5/1/20, 5:31 PM
 */
@Service
public class ResultService {

    private final ResultRepository resultRepository;
    private final StageRepository stageRepository;
    private final AthleteRepository athleteRepository;

    public ResultService(ResultRepository resultRepository, StageRepository stageRepository, AthleteRepository athleteRepository) {
        this.resultRepository = resultRepository;
        this.stageRepository = stageRepository;
        this.athleteRepository = athleteRepository;
    }

    public Map<String, Object> getFeed(String database, int limit, int offset) throws SQLException {
        return resultRepository.getFeed(database, limit, offset);
    }

    public Map<Integer, Map<String, Object>> getKomResults(String database) throws SQLException {
        return resultRepository.getKomResults(database);
    }

    public List<Result> get(String database, int stageNumber) throws SQLException {
        return resultRepository.getResults(database, stageNumber);
    }

    public Map<String, Integer> getCounts(String database, int number) throws SQLException {
        return resultRepository.getResultsCounts(database, number);
    }
}

package cz.jr.trailtour.backend.service;

import cz.jr.trailtour.backend.repository.ResultRepository;
import cz.jr.trailtour.backend.repository.StageRepository;
import cz.jr.trailtour.backend.repository.entity.Feed;
import cz.jr.trailtour.backend.repository.entity.Result;
import cz.jr.trailtour.backend.repository.entity.ResultCount;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiří Rýdel on 5/1/20, 5:31 PM
 */
@Service
public class ResultService {

    private final ResultRepository resultRepository;
    private final StageRepository stageRepository;

    public ResultService(ResultRepository resultRepository, StageRepository stageRepository) {
        this.resultRepository = resultRepository;
        this.stageRepository = stageRepository;
    }

    public List<Feed> getFeed(String database, int limit) throws SQLException {
        return resultRepository.getFeed(database, limit);
    }

    public List<Result> getResultsById(String database, long stageId) throws SQLException {
        return resultRepository.getResults(database, stageId);
    }

    public List<Result> getResultsByNumber(String database, int number) throws SQLException {
        Long stageId = stageRepository.getStageId(database, number);
        if (stageId == null) {
            return new ArrayList<>();
        }
        return getResultsById(database, stageId);
    }

    public ResultCount getResultsCount(String database, int number) throws SQLException {
        Long stageId = stageRepository.getStageId(database, number);
        if (stageId == null) {
            return null;
        }

        int maleCount = resultRepository.getResultsCount(database, "M", stageId);
        int femaleCount = resultRepository.getResultsCount(database, "F", stageId);
        ResultCount resultCount = new ResultCount();
        resultCount.setMale(maleCount);
        resultCount.setFemale(femaleCount);
        resultCount.setClub(0);
        return resultCount;
    }
}

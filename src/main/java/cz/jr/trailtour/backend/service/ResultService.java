package cz.jr.trailtour.backend.service;

import cz.jr.trailtour.backend.repository.ResultRepository;
import cz.jr.trailtour.backend.repository.StageRepository;
import cz.jr.trailtour.backend.repository.entity.Feed;
import cz.jr.trailtour.backend.repository.entity.Result;
import cz.jr.trailtour.backend.repository.entity.ResultCount;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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

    public List<Feed> getFeed(int limit) throws SQLException {
        return resultRepository.getFeed(limit);
    }

    public List<Result> getResults(long stageId) throws SQLException {
        return resultRepository.getResults(stageId);
    }

    public List<Result> getResults(String country, int number) throws SQLException {
        Long stageId = stageRepository.getStageId(country, number);
        return getResults(stageId);
    }

    public ResultCount getResultsCount(String country, int number) throws SQLException {
        Long stageId = stageRepository.getStageId(country, number);
        int maleCount = resultRepository.getResultsCount("M", stageId);
        int femaleCount = resultRepository.getResultsCount("F", stageId);
        ResultCount resultCount = new ResultCount();
        resultCount.setMale(maleCount);
        resultCount.setFemale(femaleCount);
        return resultCount;
    }
}

package cz.jr.trailtour.backend.service;

import cz.jr.trailtour.backend.repository.AthleteRepository;
import cz.jr.trailtour.backend.repository.ResultRepository;
import cz.jr.trailtour.backend.repository.StageRepository;
import cz.jr.trailtour.backend.repository.entities.Result;
import cz.jr.trailtour.backend.repository.entities.ResultCount;
import cz.jr.trailtour.backend.repository.entities.athlete.AthleteResult;
import cz.jr.trailtour.backend.repository.entities.feed.FeedResult;
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
    private final AthleteRepository athleteRepository;

    public ResultService(ResultRepository resultRepository, StageRepository stageRepository, AthleteRepository athleteRepository) {
        this.resultRepository = resultRepository;
        this.stageRepository = stageRepository;
        this.athleteRepository = athleteRepository;
    }

    public List<FeedResult> getFeed(String database, int limit, int offset) throws SQLException {
        return resultRepository.getFeed(database, limit, offset);
    }

    public List<AthleteResult> getAthleteResults(String database, long athleteId) throws SQLException {
        return resultRepository.getAthleteResults(database, athleteId);
    }

    public List<Result> get(String database, int stageNumber) throws SQLException {
        return resultRepository.getResults(database, stageNumber);
    }

    public ResultCount getCounts(String database, int number) throws SQLException {
        int maleCount = resultRepository.getResultsCount(database, "M", number);
        int femaleCount = resultRepository.getResultsCount(database, "F", number);
        ResultCount resultCount = new ResultCount();
        resultCount.setMale(maleCount);
        resultCount.setFemale(femaleCount);
        resultCount.setClub(0);
        return resultCount;
    }
}

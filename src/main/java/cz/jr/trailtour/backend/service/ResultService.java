package cz.jr.trailtour.backend.service;

import cz.jr.trailtour.backend.repository.ResultRepository;
import cz.jr.trailtour.backend.repository.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Jiří Rýdel on 5/1/20, 5:31 PM
 */
@Service
public class ResultService {

    private final ResultRepository resultRepository;

    @Autowired
    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public List<Result> getResults(long stageId) throws SQLException {
        return resultRepository.getResults(stageId);
    }
}

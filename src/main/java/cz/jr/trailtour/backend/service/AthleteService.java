package cz.jr.trailtour.backend.service;

import cz.jr.trailtour.backend.repository.AthleteRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiří Rýdel on 4/29/20, 2:44 PM
 */
@Service
public class AthleteService {

    private final AthleteRepository athleteRepository;

    public AthleteService(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    public Map<String, Object> get(String database, long id) throws SQLException {
        return athleteRepository.get(database, id);
    }

    public List<Map<String, Object>> getAll(String database, String gender) throws SQLException {
        return athleteRepository.getAll(database, gender);
    }

    public List<Map<String, Object>> getAllFulltext(String database, String match) throws SQLException {
        return athleteRepository.getFulltext(database, match);
    }

    public List<Map<String, Object>> getResults(String database, long id) throws SQLException {
        return athleteRepository.getResults(database, id);
    }

    public Map<Integer, Map<String, Object>> getKomResults(String database) throws SQLException {
        return athleteRepository.getKomResults(database);
    }

    public List<Map<String, Object>> getLadder(String database, String gender) throws SQLException {
        return athleteRepository.getLadder(database, gender);
    }

    public List<Map<String, Object>> getHistory(String database, List<Long> ids) throws SQLException {
        return athleteRepository.getHistory(database, ids);
    }
}

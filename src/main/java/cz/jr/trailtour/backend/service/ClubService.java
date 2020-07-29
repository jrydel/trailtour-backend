package cz.jr.trailtour.backend.service;

import cz.jr.trailtour.backend.repository.ClubRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiří Rýdel on 4/21/20, 1:33 PM
 */
@Service
public class ClubService {

    private final ClubRepository clubRepository;

    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public Map<String, Object> get(String database, long id) throws SQLException {
        return clubRepository.get(database, id);
    }

    public List<Map<String, Object>> getAll(String database) throws SQLException {
        return clubRepository.getAll(database);
    }

    public List<Map<String, Object>> getAllAthletes(String database, long id) throws SQLException {
        return clubRepository.getAllAthletes(database, id);
    }

    public List<Map<String, Object>> getLadder(String database) throws SQLException {
        return clubRepository.getLadder(database);
    }

    public List<Map<String, Object>> getAllFulltext(String database, String match) throws SQLException {
        return clubRepository.getFulltext(database, match);
    }
}

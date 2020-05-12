package cz.jr.trailtour.backend.service;

import cz.jr.trailtour.backend.repository.AthleteRepository;
import cz.jr.trailtour.backend.repository.entities.Athlete;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Jiří Rýdel on 4/29/20, 2:44 PM
 */
@Service
public class AthleteService {

    private final AthleteRepository athleteRepository;

    public AthleteService(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    public List<Athlete> getAll(String database) throws SQLException {
        return athleteRepository.getAll(database);
    }

    public Athlete get(String database, long id) throws SQLException {
        return athleteRepository.get(database, id);
    }

}

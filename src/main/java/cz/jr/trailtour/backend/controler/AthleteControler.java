package cz.jr.trailtour.backend.controler;

import cz.jr.trailtour.backend.repository.entity.Athlete;
import cz.jr.trailtour.backend.service.AthleteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Jiří Rýdel on 4/29/20, 2:43 PM
 */
@RestController
public class AthleteControler {

    private final AthleteService athleteService;

    public AthleteControler(AthleteService athleteService) {
        this.athleteService = athleteService;
    }

    @CrossOrigin
    @GetMapping(value = "/getAthletes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Athlete>> scrap() throws SQLException {
        return new ResponseEntity<>(athleteService.getAll(), HttpStatus.OK);
    }
}
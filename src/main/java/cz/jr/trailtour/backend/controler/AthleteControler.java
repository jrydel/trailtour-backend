package cz.jr.trailtour.backend.controler;

import cz.jr.trailtour.backend.service.AthleteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
    @GetMapping(value = "/getAthlete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getAthlete(@RequestParam(value = "database") String database, @RequestParam(value = "id") Long id) throws SQLException {
        return new ResponseEntity<>(athleteService.get(database, id), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getAthletes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, Object>>> getAthletes(@RequestParam(value = "database") String database, @RequestParam(value = "gender") String gender) throws SQLException {
        return new ResponseEntity<>(athleteService.getAll(database, gender), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getAthleteResults", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, Object>>> getAthleteResults(@RequestParam(value = "database") String database, @RequestParam(value = "id") Long id) throws SQLException {
        return new ResponseEntity<>(athleteService.getResults(database, id), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getKomResults", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Integer, Map<String, Object>>> getKomResults(@RequestParam(value = "database") String database) throws SQLException {
        return new ResponseEntity<>(athleteService.getKomResults(database), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getAthleteLadder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, Object>>> getAthleteLadder(@RequestParam(value = "database") String database, @RequestParam(value = "gender") String gender) throws SQLException {
        return new ResponseEntity<>(athleteService.getLadder(database, gender), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getAthleteHistory", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, Object>>> getAthleteLadder(@RequestParam(value = "database") String database, @RequestParam(name = "id") List<Long> ids) throws SQLException {
        return new ResponseEntity<>(athleteService.getHistory(database, ids), HttpStatus.OK);
    }
}

package cz.jr.trailtour.backend.controler;

import cz.jr.trailtour.backend.repository.entities.Result;
import cz.jr.trailtour.backend.repository.entities.ResultCount;
import cz.jr.trailtour.backend.repository.entities.athlete.AthleteResult;
import cz.jr.trailtour.backend.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created by Jiří Rýdel on 5/1/20, 5:32 PM
 */
@RestController
public class ResultControler {

    private final ResultService resultService;

    @Autowired
    public ResultControler(ResultService resultService) {
        this.resultService = resultService;
    }

    @CrossOrigin
    @GetMapping(value = "/getFeed", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getFeed(@RequestParam(value = "database") String database, @RequestParam("limit") int limit, @RequestParam(value = "offset", required = false) Integer offset) throws SQLException {
        if (offset == null) {
            offset = 0;
        }
        return new ResponseEntity<>(resultService.getFeed(database, limit, offset), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getResults", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Result>> getResults(@RequestParam(value = "database") String database, @RequestParam("number") Integer number) throws SQLException {
        return new ResponseEntity<>(resultService.get(database, number), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getAthleteResults", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AthleteResult>> getAthleteResults(@RequestParam(value = "database") String database, @RequestParam("athleteId") Long athleteId) throws SQLException {
        return new ResponseEntity<>(resultService.getAthleteResults(database, athleteId), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getKomResults", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Integer, Map<String, Object>>> getKomResults(@RequestParam(value = "database") String database) throws SQLException {
        return new ResponseEntity<>(resultService.getKomResults(database), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getResultsCount", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultCount> getResultsCount(@RequestParam(value = "database") String database, @RequestParam(value = "number") int number) throws SQLException {
        return new ResponseEntity<>(resultService.getCounts(database, number), HttpStatus.OK);
    }
}

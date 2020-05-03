package cz.jr.trailtour.backend.controler;

import cz.jr.trailtour.backend.repository.entity.Feed;
import cz.jr.trailtour.backend.repository.entity.Result;
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
    public ResponseEntity<List<Feed>> getFeed(@RequestParam("limit") int limit) throws SQLException {
        return new ResponseEntity<>(resultService.getFeed(limit), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getResults", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Result>> getResults(@RequestParam("stageId") long stageId) throws SQLException {
        return new ResponseEntity<>(resultService.getResults(stageId), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getResults", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Result>> getResults(@RequestParam("country") String country, @RequestParam(value = "number") int number) throws SQLException {
        return new ResponseEntity<>(resultService.getResults(country, number), HttpStatus.OK);
    }
}

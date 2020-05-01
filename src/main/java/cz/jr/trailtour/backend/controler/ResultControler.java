package cz.jr.trailtour.backend.controler;

import cz.jr.trailtour.backend.repository.entity.ResultWeb;
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
    @GetMapping(value = "/getResultsWeb", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResultWeb>> getResultsWeb(@RequestParam("stageId") long stageId) throws SQLException {
        return new ResponseEntity<>(resultService.getResultsWeb(stageId), HttpStatus.OK);
    }
}

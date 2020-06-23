package cz.jr.trailtour.backend.controler;

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
}

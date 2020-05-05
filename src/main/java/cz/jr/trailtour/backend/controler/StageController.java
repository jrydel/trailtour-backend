package cz.jr.trailtour.backend.controler;

import cz.jr.trailtour.backend.repository.entities.Stage;
import cz.jr.trailtour.backend.repository.entities.StageData;
import cz.jr.trailtour.backend.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiří Rýdel on 4/19/20, 1:10 PM
 */
@RestController
public class StageController {

    private final StageService stageService;

    @Autowired
    public StageController(StageService stageService) {
        this.stageService = stageService;
    }

    @CrossOrigin
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> defaultResponse() {
        return new ResponseEntity<>(Map.of("timestamp", System.currentTimeMillis()), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getStage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StageData> getStage(@RequestParam(value = "database") String database, @RequestParam(value = "number") Integer number) throws SQLException {
        return new ResponseEntity<>(stageService.get(database, number), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getStages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StageData>> getStages(@RequestParam(value = "database") String database) throws SQLException {
        return new ResponseEntity<>(stageService.getAll(database), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(path = "/saveStage", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> saveStage(@RequestParam(value = "database") String database, @RequestBody Stage stage) throws SQLException {
        return new ResponseEntity<>(stageService.save(database, stage), HttpStatus.OK);
    }
}

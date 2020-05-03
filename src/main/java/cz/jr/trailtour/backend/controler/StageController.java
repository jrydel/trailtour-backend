package cz.jr.trailtour.backend.controler;

import cz.jr.trailtour.backend.repository.entity.Stage;
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
    @GetMapping(value = "/getStageById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Stage> getStageById(@RequestParam(value = "id") Long id) throws SQLException {
        return new ResponseEntity<>(stageService.get(id), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getStageByCountryNumber", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Stage> getStageByCountryNumber(@RequestParam(value = "country") String country, @RequestParam(value = "number") int number) throws SQLException {
        return new ResponseEntity<>(stageService.get(country, number), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getStages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Stage>> getStages() throws SQLException {
        return new ResponseEntity<>(stageService.getAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(path = "/saveStage", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> saveStage(@RequestBody Stage stage) throws SQLException {
        return new ResponseEntity<>(stageService.save(stage), HttpStatus.OK);
    }
}

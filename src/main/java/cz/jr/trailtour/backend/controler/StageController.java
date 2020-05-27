package cz.jr.trailtour.backend.controler;

import cz.jr.trailtour.backend.repository.entities.stage.Stage;
import cz.jr.trailtour.backend.repository.entities.stage.StageData;
import cz.jr.trailtour.backend.repository.entities.stage.StageInfo;
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
    @GetMapping(value = "/getStageInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StageInfo>> getStageInfo(@RequestParam(value = "database") String database, @RequestParam(value = "number") Integer number) throws SQLException {
        return new ResponseEntity<>(stageService.getInfo(database, number), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getStages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Stage>> getStages(@RequestParam(value = "database") String database) throws SQLException {
        return new ResponseEntity<>(stageService.getAll(database), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getStagesData", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StageData>> getStagesData(@RequestParam(value = "database") String database) throws SQLException {
        return new ResponseEntity<>(stageService.getAllData(database), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(path = "/saveStage", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> saveStage(@RequestParam(value = "database") String database, @RequestBody Stage stage) throws SQLException {
        return new ResponseEntity<>(stageService.save(database, stage), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(path = "/saveStageInfo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> saveStageInfo(@RequestParam(value = "database") String database, @RequestBody StageInfo stageInfo) throws SQLException {
        return new ResponseEntity<>(stageService.saveInfo(database, stageInfo), HttpStatus.OK);
    }
}

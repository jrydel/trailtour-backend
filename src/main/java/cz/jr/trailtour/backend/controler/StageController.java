package cz.jr.trailtour.backend.controler;

import cz.jr.trailtour.backend.repository.entities.stage.Stage;
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
    public ResponseEntity<Stage> getStage(@RequestParam(value = "database") String database, @RequestParam(value = "number") Integer number) throws SQLException {
        return new ResponseEntity<>(stageService.get(database, number), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getStageCounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStageCounts(@RequestParam(value = "database") String database, @RequestParam(value = "number") Integer number) throws SQLException {
        return new ResponseEntity<>(stageService.getCounts(database, number), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getStageGPSData", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getStageGPSData(@RequestParam(value = "database") String database, @RequestParam(value = "number") Integer number) throws SQLException {
        return new ResponseEntity<>(stageService.getGPSData(database, number), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getStageGPSStart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getStageGPSStart(@RequestParam(value = "database") String database, @RequestParam(value = "number") Integer number) throws SQLException {
        return new ResponseEntity<>(stageService.getGPSStart(database, number), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getAllStages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Stage>> getAllStages(@RequestParam(value = "database") String database) throws SQLException {
        return new ResponseEntity<>(stageService.getAll(database), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getAllStagesCounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Integer, Map<String, Object>>> getAllStagesCounts(@RequestParam(value = "database") String database) throws SQLException {
        return new ResponseEntity<>(stageService.getAllCounts(database), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getAllStagesGPSData", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Integer, String>> getAllStagesGPSData(@RequestParam(value = "database") String database) throws SQLException {
        return new ResponseEntity<>(stageService.getAllGPSData(database), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getAllStagesGPSStart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, Object>>> getAllStagesGPSStart(@RequestParam(value = "database") String database) throws SQLException {
        return new ResponseEntity<>(stageService.getAllGPSStart(database), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getStageResults", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, Object>>> getStageResults(@RequestParam(value = "database") String database, @RequestParam(value = "number") Integer number) throws SQLException {
        return new ResponseEntity<>(stageService.getResults(database, number), HttpStatus.OK);
    }


    @CrossOrigin
    @GetMapping(value = "/getStageInfos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StageInfo>> getStageInfos(@RequestParam(value = "database") String database, @RequestParam(value = "number") Integer number) throws SQLException {
        return new ResponseEntity<>(stageService.getInfo(database, number), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(path = "/saveStageInfo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> saveStageInfo(@RequestParam(value = "database") String database, @RequestBody StageInfo stageInfo) throws SQLException {
        return new ResponseEntity<>(stageService.saveInfo(database, stageInfo), HttpStatus.OK);
    }
}

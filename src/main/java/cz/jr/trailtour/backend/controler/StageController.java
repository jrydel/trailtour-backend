package cz.jr.trailtour.backend.controler;

import cz.jr.trailtour.backend.repository.entity.Stage;
import cz.jr.trailtour.backend.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping(value = "/getStages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Stage>> getStages() {
        return new ResponseEntity<>(stageService.getAll(), HttpStatus.OK);
    }

//    @CrossOrigin
//    @PostMapping(path = "/saveSegment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> saveSegment(@RequestBody Stage stage) {
//        stageService.save(stage);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @CrossOrigin
//    @PostMapping(path = "/deleteSegment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> deleteSegment(@RequestBody Stage stage) {
//        stageService.delete(stage);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}

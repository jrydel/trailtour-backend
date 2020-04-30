package cz.jr.trailtour.backend.controler;

import cz.jr.trailtour.backend.repository.entity.Stage;
import cz.jr.trailtour.backend.service.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Jiří Rýdel on 4/19/20, 1:10 PM
 */
@RestController
public class SegmentController {

    private final SegmentService segmentService;

    @Autowired
    public SegmentController(SegmentService segmentService) {
        this.segmentService = segmentService;
    }

    @CrossOrigin
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> defaultResponse() {
        return new ResponseEntity<>(Map.of("timestamp", System.currentTimeMillis()), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getSegments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Stage>> getSegments() {
        return new ResponseEntity<>(segmentService.getAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(path = "/saveSegment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveSegment(@RequestBody Stage stage) {
        segmentService.save(stage);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(path = "/deleteSegment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteSegment(@RequestBody Stage stage) {
        segmentService.delete(stage);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

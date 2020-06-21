package cz.jr.trailtour.backend.controler;

import cz.jr.trailtour.backend.service.AthleteService;
import cz.jr.trailtour.backend.service.ClubService;
import cz.jr.trailtour.backend.service.StageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jiří Rýdel on 6/9/20, 4:39 PM
 */
@RestController
public class FulltextController {

    private final StageService stageService;
    private final AthleteService athleteService;
    private final ClubService clubService;

    public FulltextController(StageService stageService, AthleteService athleteService, ClubService clubService) {
        this.stageService = stageService;
        this.athleteService = athleteService;
        this.clubService = clubService;
    }

    @CrossOrigin
    @GetMapping(value = "/fulltext", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> fulltext(@RequestParam(value = "database") String database, @RequestParam(value = "match") String match) throws SQLException {
        Map<String, Object> result = new HashMap<>();
        result.putIfAbsent("stages", stageService.getFulltext(database, match));
        result.putIfAbsent("athletes", athleteService.getAllFulltext(database, match));
        result.putIfAbsent("clubs", clubService.getAllFulltext(database, match));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

package cz.jr.trailtour.backend.controler;

import cz.jr.trailtour.backend.service.ClubService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiří Rýdel on 6/21/20, 4:35 PM
 */
@RestController
public class ClubController {

    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @CrossOrigin
    @GetMapping(value = "/getClubs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getClubs(@RequestParam(value = "database") String database) throws SQLException {
        return new ResponseEntity<>(clubService.getAll(database), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getClub", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getClub(@RequestParam(value = "database") String database, @RequestParam(value = "id") Long id) throws SQLException {
        return new ResponseEntity<>(clubService.get(database, id), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getClubLadder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, Object>>> getClubLadder(@RequestParam(value = "database") String database) throws SQLException {
        return new ResponseEntity<>(clubService.getLadder(database), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/getClubAthletes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, Object>>> getClubLadder(@RequestParam(value = "database") String database, @RequestParam(value = "id") Long id) throws SQLException {
        return new ResponseEntity<>(clubService.getAllAthletes(database, id), HttpStatus.OK);
    }
}

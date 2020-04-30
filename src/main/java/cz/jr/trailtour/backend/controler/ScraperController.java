package cz.jr.trailtour.backend.controler;

import cz.jr.trailtour.backend.service.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Jiří Rýdel on 4/29/20, 2:46 PM
 */
@RestController
public class ScraperController {

    private final ScraperService scraperService;

    @Autowired
    public ScraperController(ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @CrossOrigin
    @GetMapping(value = "/scrap", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> scrap() throws IOException, InterruptedException, SQLException {
        return new ResponseEntity<>(scraperService.scrap(), HttpStatus.OK);
    }
}

package cz.jr.trailtour.backend.controler;

import cz.jr.trailtour.backend.service.GpxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiří Rýdel on 7/6/20, 10:21 AM
 */
@RestController
public class GpxController {

    private final GpxService gpxService;

    @Autowired
    public GpxController(GpxService gpxService) {
        this.gpxService = gpxService;
    }

    @CrossOrigin
    @PostMapping(path = "/saveGpx", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> saveGpx(@RequestParam(value = "database") String database, @RequestParam("file") MultipartFile file) throws IOException, SQLException {
        return new ResponseEntity<>(Map.of("id", gpxService.storeFile(database, file)), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(path = "/getGpx", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> saveGpx(@RequestParam(value = "database") String database, @RequestParam("id") Long id) throws IOException, SQLException, JAXBException, XMLStreamException {
        return new ResponseEntity<>(gpxService.getFile(database, id), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(path = "/getAllGpx", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, Object>>> getAllGpx(@RequestParam(value = "database") String database) throws IOException, SQLException, JAXBException, XMLStreamException {
        return new ResponseEntity<>(gpxService.getAllFiles(database), HttpStatus.OK);
    }
}

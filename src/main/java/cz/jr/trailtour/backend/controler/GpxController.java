package cz.jr.trailtour.backend.controler;

import cz.jr.trailtour.backend.service.GpxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.nio.file.Path;
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
    public ResponseEntity<String> saveGpx(@RequestParam(value = "database") String database, @RequestParam("files") MultipartFile[] files) throws IOException, SQLException {
        for (MultipartFile file : files) {
            gpxService.storeFile(database, file);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
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

    @CrossOrigin
    @GetMapping(path = "/downloadGpx", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource> downloadGpx(@RequestParam(value = "path") Path path) {
        FileSystemResource resource = new FileSystemResource(path);
        MediaType mediaType = MediaTypeFactory
                .getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        ContentDisposition disposition = ContentDisposition
                .builder("inline")
                .filename(String.valueOf(path.getFileName()))
                .build();
        headers.setContentDisposition(disposition);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(path = "/mergeGpx", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> mergeGpx(
            @RequestParam(value = "scriptPath") Path scriptPath,
            @RequestParam(value = "goodPath") Path goodPath,
            @RequestParam(value = "goodStart") String goodStart,
            @RequestParam(value = "goodEnd") String goodEnd,
            @RequestParam(value = "badPath") Path badPath,
            @RequestParam(value = "badStart") String badStart,
            @RequestParam(value = "badEnd") String badEnd,
            @RequestParam(value = "outputPath") Path outputPath) throws IOException, InterruptedException {

        ProcessBuilder pb = new ProcessBuilder("python3.6", scriptPath.toString(), goodPath.toString(), badPath.toString(), goodStart, goodEnd, badStart, badEnd, outputPath.toString());
        Process p = pb.start();
        p.waitFor();

        return new ResponseEntity<>(Map.of("status", "ok"), HttpStatus.OK);
    }
}

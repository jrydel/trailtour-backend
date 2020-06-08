package cz.jr.trailtour.backend.controler;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * Created by Jiří Rýdel on 6/8/20, 2:18 PM
 */
@RestController
public class AuthController {

    @CrossOrigin
    @PostMapping(path = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticate(@RequestParam(value = "password") String password) throws SQLException {
        return null;
    }
}

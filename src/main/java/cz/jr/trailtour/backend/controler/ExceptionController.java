package cz.jr.trailtour.backend.controler;

import cz.jr.trailtour.backend.Utils;
import cz.jr.trailtour.backend.exceptions.NotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Jiří Rýdel on 3/8/20, 10:15 AM
 */
@RestControllerAdvice
public class ExceptionController {

    private static final Logger LOG = LogManager.getLogger(ExceptionController.class);

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(Throwable t) {
        return handleException(t, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Throwable t) {
        return handleException(t, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Map<String, String>> handleException(Throwable t, HttpStatus httpStatus) {
        LOG.error("Error.", t);
        return new ResponseEntity<>(convertException(t), httpStatus);
    }

    private Map<String, String> convertException(Throwable t) {
        LOG.error("Error.", t);
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        result.put("message", Utils.convertMessages(t));
        result.put("stackTrace", ExceptionUtils.getStackTrace(t));
        return result;
    }
}

package vn.elca.training.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import vn.elca.training.model.exception.*;

import java.util.LinkedHashMap;
import java.util.Map;

@EnableWebMvc
@ControllerAdvice
public class ControllerAdvisor {

    @ResponseBody
    @ExceptionHandler(value = NumberExistException.class)
    public ResponseEntity<String> handleNumberExistException(NumberExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(VisaNotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> handleVisaNotFoundException(VisaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(value = StartDateGreaterThanEndDateException.class)
    @ResponseBody
    public ResponseEntity<Object> handleStartDateGreaterThanEndDateException(StartDateGreaterThanEndDateException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());
        body.put("errorName", "DateException");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = ConcurrentUpdateException.class)
    @ResponseBody
    public ResponseEntity<Object> handleConcurrentUpdateException(ConcurrentUpdateException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());
        body.put("errorName", "ConcurrentException");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = DeteleProjectNotNewStatusException.class)
    @ResponseBody
    public ResponseEntity<Object> handleDeleteProjectNotNewStatusException(DeteleProjectNotNewStatusException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());
        body.put("errorName", "DeleteProjectException");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}

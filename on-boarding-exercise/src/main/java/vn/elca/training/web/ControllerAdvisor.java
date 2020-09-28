package vn.elca.training.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import vn.elca.training.model.exception.NumberExistException;
import vn.elca.training.model.exception.StartDateGreaterThanEndDateException;
import vn.elca.training.model.exception.VisaNotFoundException;

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
    public ResponseEntity<String> handleStartDateGreaterThanEndDateException(StartDateGreaterThanEndDateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}

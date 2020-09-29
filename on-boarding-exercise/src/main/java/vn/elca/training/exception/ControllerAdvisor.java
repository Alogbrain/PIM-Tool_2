package vn.elca.training.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(value = NumberExistException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleNumberExistException(NumberExistException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>( ex.getMessage(),status);
//    public @ResponseBody ExceptionResponse  handleNumberExistException(NumberExistException ex) {
//        ExceptionResponse error = new ExceptionResponse();
//        error.setErrorMessage(ex.getMessage());
//        return error;

//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(ex.getMessage());

//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("timestamp", LocalDateTime.now());
//        body.put("message", "The project number is existed. Please select a different project number");
//        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = VisaNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleVisaNotFoundException(VisaNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(ex.getMessage(), status);
    }

}

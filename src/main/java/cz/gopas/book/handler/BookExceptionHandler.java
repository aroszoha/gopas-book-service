package cz.gopas.book.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class BookExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleInvalidArgs() {
        log.info("Bad argument supplied to service method !");
        return ResponseEntity.badRequest().body("Invalid argument supplied");
    }

}
